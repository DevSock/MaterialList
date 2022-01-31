package com.ourfallenfriend.materiallist.events;

import com.ourfallenfriend.materiallist.Contractor;
import com.ourfallenfriend.materiallist.MaterialList;
import com.ourfallenfriend.materiallist.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class OnBlockPlaceEvent implements Listener {
    Contractor contractor = Contractor.getInstance();
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(contractor.hasContract(player.getUniqueId())) {
            UUID agentID = player.getUniqueId();
            Block block = event.getBlock();
            appendMetadata(block);
            MaterialList materials = contractor.getSpecificContract(agentID).getValue();
            materials.addBlock(block);
        }
    }

    private void appendMetadata(Block block) {
        if(block.hasMetadata("PlacedUnderContract")) return;

        block.setMetadata("PlacedUnderContract", new FixedMetadataValue(Main.getInstance(), true));
    }
}
