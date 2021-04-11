package io.github.austerzockt.essentialsreloaded.config;

import java.util.Map;

public interface IConfigHandler {
    void registerConfig(String configName);

    Map<String, EssentialsConfig> getConfigs();

    EssentialsConfig getConfig(String configName);
}
