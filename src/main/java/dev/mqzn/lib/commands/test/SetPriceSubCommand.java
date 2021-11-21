package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.SubCommand;
import dev.mqzn.lib.commands.api.UsageArg;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SetPriceSubCommand extends SubCommand {

    public SetPriceSubCommand() {
        super("setprice", 1);
    }

    @Override
    public String getPermission() {
        return "test.command.setprice";
    }

    @Override
    public String getDescription() {
        return "sets price of people";
    }

    @Override
    public void setRequirements() {
        // /test <user> setprice <price>

        addRequirement((args -> args.size() == 3), ((sender, args) ->  {

            OfflinePlayer offlinePlayer =
                    Bukkit.getOfflinePlayer(args.get(1).getArgument());
            if(!offlinePlayer.isOnline()) {
                sender.sendMessage(Translator.color("Target player is offline !"));
                return;
            }

            int price = (int) args.get(2).getParsedArg();
            TestCommand.price = price;
            System.out.println("PRICE IS NOW " + TestCommand.price);
            sender.sendMessage(Translator.color("&7You have set the price of &e" + offlinePlayer.getName() + " &7to &a" + price));

        }), new UsageArg("user", 0, String.class, UsageArg.ArgumentType.REQUIRED),
                new UsageArg("price", 2, int.class, UsageArg.ArgumentType.REQUIRED));

    }


}
