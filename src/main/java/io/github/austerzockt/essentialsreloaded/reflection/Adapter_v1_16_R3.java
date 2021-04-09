package io.github.austerzockt.essentialsreloaded.reflection;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

public class Adapter_v1_16_R3 implements IAdapter {
    private EssentialsReloaded essentialsReloaded;
    public Adapter_v1_16_R3(EssentialsReloaded essentialsReloaded) {
        this.essentialsReloaded = essentialsReloaded;
    }
    @Override
    public void syncCommands() {
        CraftServer cs = (CraftServer) Bukkit.getServer();
        cs.syncCommands();
    }
}
