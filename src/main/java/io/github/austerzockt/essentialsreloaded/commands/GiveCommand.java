package io.github.austerzockt.essentialsreloaded.commands;

import com.google.common.collect.Lists;
import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import io.github.austerzockt.essentialsreloaded.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GiveCommand extends AbstractCommand {
	public GiveCommand(EssentialsReloaded essentialsReloaded) {
		super(essentialsReloaded, "give", "essentials.give");
	}

	@Override
	public void execute(CommandSender sender, Command command, String[] args, PlayerData playerData) {
		if (args.length >= 2) {
			if (Utils.playerUtils().isOnline(args[0])) {

			}
			int amount = args.length == 3 ? Integer.parseInt(args[2]) : 64;
			Material mat = Material.getMaterial(args[1].toUpperCase());
			if (mat != null) {
				playerData.player().getInventory().addItem(new ItemStack(mat, amount));

			}
		}
	}

	// Returns a List of all available items in the game (by materials). If you
	// enter anything, it filters it and only returns things that start with that
	// input
	@Override
	public List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData) {
		// COPLETION FOR ARGS 1
		sender.sendMessage(args.length + "");
		List<String> returns = new ArrayList<>();
		if (args.length == 3)
			returns = Lists.newArrayList("1", "8", "16", "32", "64");
		if (args.length == 2)
			returns = Utils.commandUtils().listOfMaterials(args[1], true);
		if (args.length == 1)
			returns = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
		Collections.sort(returns);
		return returns;
	}
}
