package com.ourfallenfriend.materiallist.commands;

import com.ourfallenfriend.materiallist.Contractor;
import com.ourfallenfriend.materiallist.messenger.MessageType;
import com.ourfallenfriend.materiallist.messenger.Messenger;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MaterialListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage(Component.text("This command is only available to players."));
            return true;
        }

        if(args.length < 1) {
            Messenger.dispatch(player, MessageType.FAILURE, "Please make sure you specify whether you'd like to start or stop your material list.");
            Messenger.dispatch(player, MessageType.INFO,"/MaterialList *<Start / Stop>*" );
        }else {
            UUID agentID = player.getUniqueId();
            Contractor contractor = Contractor.getInstance();

            switch (args[0].toLowerCase()) {
                case "start" -> {
                    if (args.length > 1) {
                        boolean force = Boolean.parseBoolean(args[1]);
                        contractor.draftContract(agentID, force);
                        break;
                    }
                    contractor.draftContract(agentID, false);
                }
                case "stop" -> {
                    if (!(contractor.hasContract(agentID))) {
                        Messenger.dispatch(player, MessageType.FAILURE, "You're not currently making a material list.");
                        Messenger.dispatch(player, MessageType.INFO,"/MaterialList *<Start / Stop>*" );
                        break;
                    }
                    contractor.submitContract(agentID);
                }
                default -> {
                    Messenger.dispatch(player, MessageType.FAILURE, "Unrecognized command option, please ensure you're using the command correctly.");
                    Messenger.dispatch(player, MessageType.INFO,"/MaterialList *<Start / Stop>*" );
                }
            }
        }
        return true;
    }
}
