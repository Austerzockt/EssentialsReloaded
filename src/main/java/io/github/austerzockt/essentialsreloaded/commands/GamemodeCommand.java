package io.github.austerzockt.essentialsreloaded.commands;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GamemodeCommand extends AbstractCommand {
    private final Pattern pattern;
    private final List<String> GAMEMODES;

    public GamemodeCommand(EssentialsReloaded essentialsReloaded) {
        super(essentialsReloaded);
        this.GAMEMODES = List.of("2", "3", "0", "1", "spectator", "creative", "survival", "adventure");
        this.name = "gamemode";
        this.permission = "essentials.gamemode";
        init();
        //And yes, I just wanted to show off my freshly gained and immediately forgotten Regex skills
        this.pattern = Pattern.compile("^((s(p|)|c|a)|(^surv(ival|))|(creative)|(adventure)|(spec(|tator))|0|1|2|3)$");
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args, PlayerData playerData) {
        if (playerData.isPlayer()) {
            if (args.length == 1) {
                if (pattern.matcher(args[0]).find()) {

                    switch (args[0]) {
                        case "0":
                        case "s":
                        case "surv":
                        case "survival":
                            playerData.player().setGameMode(GameMode.SURVIVAL);
                            break;
                        case "1":
                        case "c":
                        case "creative":
                            playerData.player().setGameMode(GameMode.CREATIVE);
                            break;
                        case "2":
                        case "adventure":
                        case "a":
                            playerData.player().setGameMode(GameMode.ADVENTURE);
                            break;
                        case "3":
                        case "sp":
                        case "spectator":
                        case "spec":
                            playerData.player().setGameMode(GameMode.SPECTATOR);
                            break;

                    }

                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Gamemode, please enter input according to this regex: " + pattern.pattern());

                }
            }
        }
    }

    @Override
    public List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData) {
        List<String> x = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], GAMEMODES, x);
        return x.stream().sorted().collect(Collectors.toList());
    }
}
