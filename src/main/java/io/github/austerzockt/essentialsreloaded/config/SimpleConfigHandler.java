package io.github.austerzockt.essentialsreloaded.config;

import com.google.common.collect.Maps;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class SimpleConfigHandler implements IConfigHandler {
    private final Map<String, EssentialsConfig> configs;
    private final Plugin plugin;

    public SimpleConfigHandler(Plugin plugin) {
        configs = Maps.newHashMap();
        this.plugin = plugin;
    }

    @Override
    public void registerConfig(String name) {
        EssentialsConfig config = new EssentialsConfig(this.plugin, name);
        configs.put(config.getName(), config);
    }

    @Override
    public Map<String, EssentialsConfig> getConfigs() {
        return configs;
    }

    @Override
    public EssentialsConfig getConfig(String name) {

        return configs.getOrDefault(name, null);
    }
}
