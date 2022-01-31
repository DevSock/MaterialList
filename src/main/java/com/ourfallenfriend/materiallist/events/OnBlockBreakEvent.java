package com.ourfallenfriend.materiallist.events;

import com.ourfallenfriend.materiallist.Contractor;
import com.ourfallenfriend.materiallist.MaterialList;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class OnBlockBreakEvent implements Listener {
    Contractor contractor = Contractor.getInstance();
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(contractor.hasContract(player.getUniqueId())) {
            UUID agentID = player.getUniqueId();
            Block block = event.getBlock();
            if(!(block.hasMetadata("PlacedUnderContract"))) return;

            MaterialList materials = contractor.getSpecificContract(agentID).getValue();
            materials.removeBlock(block);
        }
    }
}
