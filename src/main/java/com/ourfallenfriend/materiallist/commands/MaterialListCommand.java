package com.ourfallenfriend.materiallist.commands;

import com.ourfallenfriend.materiallist.Contractor;
import com.ourfallenfriend.materiallist.messenger.BakedMessage;
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
        Messenger messenger = Messenger.getInstance();

        if(args.length < 1) {
            messenger.sendMessage(player, BakedMessage.WRONG_ARGS, BakedMessage.COMMAND_USAGE);
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
                        messenger.sendMessage(player, BakedMessage.NOT_CONTRACTED, BakedMessage.COMMAND_USAGE);
                        break;
                    }
                    contractor.submitContract(agentID);
                }
                default -> messenger.sendMessage(player, BakedMessage.WRONG_ARGS, BakedMessage.COMMAND_USAGE);
            }
        }
        return true;
    }
}
