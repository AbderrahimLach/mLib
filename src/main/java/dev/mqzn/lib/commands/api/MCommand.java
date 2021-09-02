package dev.mqzn.lib.commands.api;

import dev.mqzn.lib.MLib;
import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Arrays;
import java.util.Map;

public abstract class MCommand extends Command {

    static String NO_PERMISSION, ONLY_PLAYER;


    static {
        NO_PERMISSION = FormatUtils.color("&cAccess denied to the command");
        ONLY_PLAYER = FormatUtils.color("&cOnly players can do this !");
    }

    private final String permission;
    public MCommand(String name, String permission, String desc, String usage, String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.permission = permission;
    }

    public MCommand(String name, String desc, String usage, String[] aliases) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.permission = null;
    }


    @Override
    public String getPermission() {
        return this.permission == null ? "command."
                + this.getName().toLowerCase() : this.permission;
    }

    public abstract Map<Integer, Class<?>> getArgParses();

    public boolean execute(CommandSender sender, String label, String[] args) {

        if(!allowConsole() && !(sender instanceof Player)) {
            sender.sendMessage(ONLY_PLAYER);
            return true;
        }

        if(!sender.hasPermission(this.getPermission())) {
            sender.sendMessage(NO_PERMISSION);
            return true;
        }


        CommandArg[] commandArgs = new CommandArg[args.length];

        for (int i = 0; i < args.length; i++) {

            Class<?> clazz = getArgParses().get(i);
            if(clazz == null) {
                clazz = String.class;
            }

            String tempArg = args[i];
            commandArgs[i] = new CommandArg(i,
                    MLib.getInstance().getCommandManager().getArgumentParser(clazz).parse(tempArg), tempArg);

        }


        execute(sender, commandArgs);

        return true;
    }

    public abstract void execute(CommandSender sender, CommandArg... args);

    public abstract boolean allowConsole();

}
