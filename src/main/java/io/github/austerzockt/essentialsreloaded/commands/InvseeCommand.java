package io.github.austerzockt.essentialsreloaded.commands;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.comphenix.protocol.utility.Util;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import io.github.austerzockt.essentialsreloaded.utils.Utils;

public class InvseeCommand extends AbstractCommand implements Listener {
	private List<UUID> unmodifiableInvs;
	private Map<UUID, UUID> modifableInvs;

	public InvseeCommand(EssentialsReloaded essentialsReloaded) {
		super(essentialsReloaded, "invsee", "essentials.invsee");
		this.unmodifiableInvs = Lists.newArrayList();
		this.modifableInvs = Maps.newHashMap();
	}

	@Override
	public void execute(CommandSender sender, Command command, String[] args, PlayerData playerData) {
		if (playerData.isPlayer()) {
			if (args.length == 0) {
				playerData.player().openInventory(createInventory(playerData.player()));
				unmodifiableInvs.add(playerData.uuid());
			}
		}
	}

	@Override
	public List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData) {
		return Lists.newArrayList();
	}

	/*
	 * Layout: HOTBAR 0-9 INVENTORY 9-35 Mainhand, Offhand, Armor 36-41
	 */
	private Inventory createInventory(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 5 * 9);
		inventory.setContents(player.getInventory().getContents());

		/*
		 * for (int i = 0; i < player.getInventory().getContents().length; i++) {
		 * inventory.setItem(i + 8, player.getInventory().getContents()[i]);
		 * 
		 * }
		 */
		inventory.setItem(0 + 36, player.getInventory().getItemInHand());
		inventory.setItem(1 + 36, player.getInventory().getItemInOffHand());
		inventory.setItem(2 + 36, player.getInventory().getHelmet());
		inventory.setItem(3 + 36, player.getInventory().getChestplate());
		inventory.setItem(4 + 36, player.getInventory().getLeggings());
		inventory.setItem(5 + 36, player.getInventory().getBoots());
		return inventory;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (unmodifiableInvs.contains(event.getWhoClicked().getUniqueId())) {
			event.setCancelled(true);
		}
		if (modifableInvs.keySet().contains(event.getWhoClicked())) {
			if (Utils.playerUtils().isOnline(modifableInvs.get(event.getWhoClicked().getUniqueId()))) {
				Utils.playerUtils().getPlayer(modifableInvs.get(event.getWhoClicked().getUniqueId()));
			}
		}

	}
}
