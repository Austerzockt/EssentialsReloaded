package io.github.austerzockt.essentialsreloaded.commands;

import com.google.common.collect.Lists;
import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import io.github.austerzockt.essentialsreloaded.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.UUID;

public class EnderChestCommand extends AbstractCommand implements Listener {
    private final List<UUID> openUnmodifiableECs;
    public EnderChestCommand(EssentialsReloaded essentialsReloaded) {
        super(essentialsReloaded);
        openUnmodifiableECs = Lists.newArrayList();
        this.name = "enderchest";
        this.permission = "essentials.enderchest";
        init();
    }


    @Override
    public void execute(CommandSender sender, Command command, String[] args, PlayerData playerData) {
    if (playerData.isPlayer()) {
        Player p = (args.length > 0) ? Utils.playerUtils().getPlayer(args[0]) : playerData.player();
        if (p == null) {sender.sendMessage(essentialsReloaded.messageHandler().getMessage("general.unknown_player").build()); return; }
        if (sender.hasPermission(this.permission + ".modify") || args.length == 0) {
            playerData.player().openInventory(p.getEnderChest());

        } else {
            Inventory copyOfEc = Bukkit.createInventory( null, InventoryType.CHEST, "Ender Chest");
            copyOfEc.setContents(p.getEnderChest().getContents());
            openUnmodifiableECs.add(playerData.uuid());

            playerData.player().openInventory(copyOfEc);
        }
        playerData.player().openInventory(p.getEnderChest());
    }

    }


    @EventHandler
    public void onECOpen(InventoryClickEvent event) {
        if (openUnmodifiableECs.contains(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void onECClose(InventoryCloseEvent event) {
        openUnmodifiableECs.remove(event.getPlayer().getUniqueId());
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        openUnmodifiableECs.remove(event.getPlayer().getUniqueId());
    }
    public void onPlayerDeath(PlayerDeathEvent event) {
        openUnmodifiableECs.remove(event.getEntity().getUniqueId());
    }


    @Override
    public List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData) {
        return null;
    }

}

