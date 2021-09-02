package dev.mqzn.lib.commands.api.identifiers;

import dev.mqzn.lib.commands.api.ArgumentParser;
import org.bukkit.Material;

public class MaterialParser implements ArgumentParser<Material> {


    @Override
    public Material parse(String arg) {
        try{
            Material.valueOf(arg.toUpperCase());
        }catch (Exception ex) {
            return null;
        }

        return Material.valueOf(arg.toUpperCase());
    }

}
