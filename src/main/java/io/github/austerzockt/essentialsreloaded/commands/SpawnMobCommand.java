package io.github.austerzockt.essentialsreloaded.commands;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SpawnMobCommand extends AbstractCommand {
	public SpawnMobCommand(EssentialsReloaded essentialsReloaded) {
		super(essentialsReloaded, "spawnmob", "essentials.spawnmob");
	}

	@Override
	public void execute(CommandSender sender, Command command, String[] args, PlayerData playerData) {

	}

	@Override
	public List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData) {
		return null;
	}
}
