package com.ourfallenfriend.materiallist;

import com.ourfallenfriend.materiallist.http.AsyncHttpClientExchange;
import com.ourfallenfriend.materiallist.messenger.BakedMessage;
import com.ourfallenfriend.materiallist.messenger.MessageType;
import com.ourfallenfriend.materiallist.messenger.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

public class Contractor {
    private static final Contractor instance;
    private final Messenger messenger;
    private final Hashtable<UUID, MaterialList> contracts = new Hashtable<>();

    private Contractor() {
        messenger = Messenger.getInstance();
        Main.getInstance().getLogger().log(Level.INFO, "Material List Manager Instantiated.");
    }

    static {
        instance = new Contractor();
    }

    public static Contractor getInstance() {
        return instance;
    }

    public Hashtable<UUID, MaterialList> getContracts() {
        return contracts;
    }

    public AbstractMap.SimpleEntry<UUID, MaterialList> getSpecificContract(UUID agentID) {
        if(!(hasContract(agentID))) return null;
        return new AbstractMap.SimpleEntry<>(agentID, getContracts().get(agentID));
    }

    private void addContract(UUID agentID, MaterialList materialList) {
        getContracts().put(agentID, materialList);

        Main.getInstance().getLogger().log(Level.INFO, String.valueOf(getContracts().containsKey(agentID)));
    }

    public void draftContract(UUID agentID, boolean force) {
        Player agent = Bukkit.getPlayer(agentID);
        assert agent != null;

        if(force) {
            messenger.sendMessage(agent, BakedMessage.CONTRACT_STARTED, BakedMessage.CONTRACT_HOWTO_END);
            addContract(agentID, new MaterialList());
        }else if(hasContract(agentID)) {
            messenger.sendMessage(agent, BakedMessage.ALREADY_CONTRACTED, BakedMessage.CONTRACT_HOWTO_END);
        }else {
            messenger.sendMessage(agent, BakedMessage.CONTRACT_STARTED, BakedMessage.CONTRACT_HOWTO_END);
            addContract(agentID, new MaterialList());
        }
    }

    public void submitContract(UUID agentID) {
        MaterialList materialList = getSpecificContract(agentID).getValue();
        Player agent = Bukkit.getPlayer(agentID);
        assert agent != null;

        messenger.sendMessage(agent, BakedMessage.CONTRACT_SUBMITTED);

        try {
            AsyncHttpClientExchange.doRequest(agentID, materialList);
        }catch(IOException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void completeContract(UUID agentID, String URL) {
        Player agent = Bukkit.getPlayer(agentID);

        messenger.sendMessage(agent, BakedMessage.CONTRACT_ENDED);
        messenger.sendMessage(agent, MessageType.INFO, URL);
        voidContract(agentID);
    }

    public void voidContract(UUID agentID) {
        getContracts().remove(agentID);
    }

    public boolean hasContract(UUID agentID) {
        return getContracts().containsKey(agentID);
    }
}
