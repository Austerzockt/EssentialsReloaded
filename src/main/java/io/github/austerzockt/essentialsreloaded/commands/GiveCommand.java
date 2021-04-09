package io.github.austerzockt.essentialsreloaded.commands;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GiveCommand extends AbstractCommand{
    public GiveCommand(EssentialsReloaded essentialsReloaded) {
        super(essentialsReloaded);
        this.name = "give";
        this.permission = "essentials.give";
        init();
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args, PlayerData playerData) {
    if (playerData.isPlayer()) {
        if (args.length == 1) {

            playerData.getPlayer().getInventory().addItem(new ItemStack(Material.getMaterial(args[0])));
        }
    } else {
        sender.sendMessage(ChatColor.RED + "This Command is for Players only");
    }

    }
    //Returns a List of all available items in the game (by materials). If you enter anything, it filters it and only returns things that start with that input
    @Override
    public List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData) {

        List<String> values = Arrays.stream(Material.values()).map(Enum::name).filter(s -> !s.startsWith(Material.LEGACY_PREFIX)).collect(Collectors.toList());
        //Some weird BS with Conncurent Access and the Iterator in StringUtil.copyPartialMatches ... This fixes it, might not be a nice solution, but i am tired and i frankly dont fking care
        List<String> x = new ArrayList<>();
        x = StringUtil.copyPartialMatches(args[0], values, x);
        return x;
    }
}
