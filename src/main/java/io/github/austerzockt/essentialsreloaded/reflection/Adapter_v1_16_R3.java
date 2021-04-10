package io.github.austerzockt.essentialsreloaded.reflection;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

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

    @Override
    public void hidePlayer(Player player, Player hideFrom) {
        PacketPlayOutPlayerInfo pack = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) player).getHandle());
        ((CraftPlayer) hideFrom).getHandle().playerConnection.sendPacket(pack);
        Bukkit.broadcastMessage("Sending to " + player.getName() + " for " + hideFrom.getName());
    }

    public void hidePlayer(Player p, List<Player> hideFrom) {
        hideFrom.forEach(s -> hidePlayer(p, s));

    }
}
