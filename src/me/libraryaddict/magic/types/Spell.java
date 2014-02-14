package me.libraryaddict.magic.types;

import java.io.File;
import java.io.IOException;

import me.libraryaddict.magic.MagicApi;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public abstract class Spell {

    public String getName() {
        return getClass().getSimpleName();
    }

    public abstract void invokeSpell(Player player, String[] args);

    public void saveConfig(YamlConfiguration config) {
        File configFile = new File(MagicApi.getMainPlugin().getDataFolder(), getName() + ".yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        File configFile = new File(MagicApi.getMainPlugin().getDataFolder(), getName() + ".yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }
}
