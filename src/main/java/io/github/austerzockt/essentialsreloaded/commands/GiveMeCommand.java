package io.github.austerzockt.essentialsreloaded.commands;

import com.google.common.collect.Lists;
import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import io.github.austerzockt.essentialsreloaded.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiveMeCommand extends AbstractCommand {
	public GiveMeCommand(EssentialsReloaded essentialsReloaded) {
		super(essentialsReloaded, "giveme", "essentials.giveme");

	}

	@Override
	public void execute(CommandSender sender, Command command, String[] args, PlayerData playerData) {
		if (playerData.isPlayer()) {
			if (args.length >= 1) {
				Material mat = Material.getMaterial(args[0].toUpperCase());
				if (mat == null)
					return;
				int amount = args.length == 2 ? Integer.parseInt(args[1]) : 64;
				playerData.player().getInventory().addItem(new ItemStack(mat, amount));
			}
		}
	}

	@Override
	public List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData) {
		List<String> list = Lists.newArrayList();
		if (args.length == 1)
			list = Utils.commandUtils().listOfMaterials(args[0], true);
		if (args.length == 2)
			list = Lists.newArrayList("1", "8", "16", "32", "64");
		return list;
	}
}
