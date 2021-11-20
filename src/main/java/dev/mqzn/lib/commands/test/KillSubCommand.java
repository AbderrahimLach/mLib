package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.SubCommand;
import dev.mqzn.lib.commands.api.UsageArg;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class KillSubCommand extends SubCommand {

    public KillSubCommand() {
        super("kill", 0);
    }

    @Override
    public String getPermission() {
        return "test.command.kill";
    }

    @Override
    public String getDescription() {
        return "Kills a player";
    }

    @Override
    public void setRequirements() {

        addRequirement((args -> args.size() == 2), ((sender, args) ->  {

            OfflinePlayer offlinePlayer =
                    Bukkit.getOfflinePlayer(args.get(1).getArgument());
                // test kill <>
            if(!offlinePlayer.isOnline()) {
                sender.sendMessage(Translator.color("Target player is offline !"));
                return;
            }

            ((Player)offlinePlayer).setHealth(0D); //killing the player
            sender.sendMessage(Translator.color("&7You have &cKilled &7the player &a"
                    + offlinePlayer.getName()));

        }), new UsageArg("target", 1, String.class, UsageArg.ArgumentType.REQUIRED));

    }


}
