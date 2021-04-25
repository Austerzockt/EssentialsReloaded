package io.github.austerzockt.essentialsreloaded.commands;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {
	protected String name;
	protected String permission;
	protected EssentialsReloaded essentialsReloaded;

	public AbstractCommand(EssentialsReloaded essentialsReloaded, String name, String permission) {
		this.name = name;
		this.permission = permission;
		this.essentialsReloaded = essentialsReloaded;
		init();

	}

	protected void init() {
		essentialsReloaded.getCommand(name).setExecutor(this);
		essentialsReloaded.getCommand(name).setTabCompleter(this);

	}

	public abstract void execute(CommandSender sender, Command command, String[] args, PlayerData playerData);

	public abstract List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData);

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (command.getName().equalsIgnoreCase(this.name)) {
			if (commandSender.hasPermission(this.permission)) {

				execute(commandSender, command, strings, PlayerData.fromCommandSender(commandSender));
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

		return tab(commandSender, command, strings, PlayerData.fromCommandSender(commandSender));
	}

	public static class PlayerData {

		private final boolean isPlayer;
		private final Player player;
		private final UUID uuid;

		public PlayerData(Player player) {
			this.player = player;
			isPlayer = this.player != null;
			uuid = this.player != null ? this.player.getUniqueId() : null;
		}

		public static PlayerData fromCommandSender(CommandSender sender) {
			return new PlayerData(sender instanceof Player ? (Player) sender : null);
		}

		public Player player() {
			return player;
		}

		public UUID uuid() {
			return uuid;
		}

		public boolean isPlayer() {
			return isPlayer;
		}
	}
}
