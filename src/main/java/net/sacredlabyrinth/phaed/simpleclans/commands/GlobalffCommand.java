package net.sacredlabyrinth.phaed.simpleclans.commands;

import net.sacredlabyrinth.phaed.simpleclans.ChatBlock;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

/**
 * @author phaed
 */
public class GlobalffCommand {
    public GlobalffCommand() {
    }

    /**
     * Execute the command
     *
     * @param sender
     * @param arg
     */
    public void execute(CommandSender sender, String[] arg) {
        SimpleClans plugin = SimpleClans.getInstance();

        if (arg.length != 1) {
            ChatBlock.sendMessage(sender, ChatColor.RED + MessageFormat.format(plugin.getLang("usage.0.ff.allow.auto"), plugin.getSettingsManager().getCommandClan()));
            return;
        }

        String action = arg[0];

        if (action.equalsIgnoreCase(plugin.getLang("allow"))) {
            if (plugin.getSettingsManager().isGlobalff()) {
                ChatBlock.sendMessage(sender, ChatColor.AQUA + plugin.getLang("global.friendly.fire.is.already.being.allowed"));
            } else {
                plugin.getSettingsManager().setGlobalff(true);
                ChatBlock.sendMessage(sender, ChatColor.AQUA + plugin.getLang("global.friendly.fire.is.set.to.allowed"));
            }
            return;
        }

        if (action.equalsIgnoreCase(plugin.getLang("auto"))) {
            if (!plugin.getSettingsManager().isGlobalff()) {
                ChatBlock.sendMessage(sender, ChatColor.AQUA + plugin.getLang("global.friendy.fire.is.already.being.managed.by.each.clan"));
            } else {
                plugin.getSettingsManager().setGlobalff(false);
                ChatBlock.sendMessage(sender, ChatColor.AQUA + plugin.getLang("global.friendy.fire.is.now.managed.by.each.clan"));
            }
            return;
        }
        ChatBlock.sendMessage(sender, ChatColor.RED + MessageFormat.format(plugin.getLang("usage.0.globalff.allow.auto"), plugin.getSettingsManager().getCommandClan()));
    }
}
