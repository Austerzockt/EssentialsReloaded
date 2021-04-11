package io.github.austerzockt.essentialsreloaded.commands;

import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GamemodeCommand extends AbstractCommand {
    private final List<String> GAMEMODES;
    private final Pattern pattern_s;
    private final Pattern pattern_c;
    private final Pattern pattern_a;
    private final Pattern pattern_sp;

    public GamemodeCommand(EssentialsReloaded essentialsReloaded) {
        super(essentialsReloaded);
        this.GAMEMODES = List.of("2", "3", "0", "1", "spectator", "creative", "survival", "adventure");
        this.name = "gamemode";
        this.permission = "essentials.gamemode";
        init();
        //And yes, I just wanted to show off my freshly gained and immediately forgotten Regex skills
        pattern_s = Pattern.compile("^(s(|urv)(ival|)|0)$");
        pattern_c = Pattern.compile("^(c(|reative)|1)$");
        pattern_a = Pattern.compile("^(a(|dventure)|2)$");
        pattern_sp = Pattern.compile("^(s(|p(ec(|tator)|))|3)$");
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args, PlayerData playerData) {
        if (playerData.isPlayer()) {
            if (args.length == 1) {
                var x = gamemode(playerData.player(), args[0]);
                if (x.changed) {
                    playerData.player().sendMessage(ChatColor.GOLD + "Your Gamemode was set to " + StringUtils.capitalize(x.gamemode.name().toLowerCase()));
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

    private GamemodeData gamemode(Player player, String gamemode) {
        GamemodeData data = new GamemodeData(false, null);
        if (pattern_s.matcher(gamemode).find()) {
            player.setGameMode(GameMode.SURVIVAL);
            data.gamemode = GameMode.SURVIVAL;
            data.changed = true;
        } else if (pattern_c.matcher(gamemode).find()) {
            player.setGameMode(GameMode.CREATIVE);
            data.gamemode = GameMode.CREATIVE;
            data.changed = true;
        } else if (pattern_a.matcher(gamemode).find()) {
            player.setGameMode(GameMode.ADVENTURE);
            data.gamemode = GameMode.ADVENTURE;
            data.changed = true;
        } else if (pattern_sp.matcher(gamemode).find()) {
            player.setGameMode(GameMode.SPECTATOR);
            data.gamemode = GameMode.SPECTATOR;
            data.changed = true;
        }
        return data;
    }

    private class GamemodeData {
        public boolean changed;
        public GameMode gamemode;

        public GamemodeData(boolean changed, GameMode gamemode) {
            this.changed = changed;
            this.gamemode = gamemode;
        }
    }
}
