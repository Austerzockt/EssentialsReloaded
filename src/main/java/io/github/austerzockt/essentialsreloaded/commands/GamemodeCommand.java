package io.github.austerzockt.essentialsreloaded.commands;

import com.google.common.collect.Lists;
import io.github.austerzockt.essentialsreloaded.EssentialsReloaded;
import io.github.austerzockt.essentialsreloaded.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
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
            if (args.length >= 1) {
                boolean self = !(args.length >= 2);

                Player player = self ? playerData.player() : Utils.playerUtils().getPlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(essentialsReloaded.messageHandler().getMessage("general.unknown_player").build());
                } else {
                    if (!self && playerData.player().getUniqueId().equals(player.getUniqueId())) self = true;

                    GamemodeData gamemodeData = gamemode(playerData.player(), player, args[0]);
                    if (gamemodeData.noperm)
                        sender.sendMessage(essentialsReloaded.messageHandler().getMessage("general.no_perms").build());
                    if (gamemodeData.target_exempt)
                        sender.sendMessage(essentialsReloaded.messageHandler().getMessage("gamemode.target_exempt").placeholder("player", player.getDisplayName()).build());
                    if (gamemodeData.changed) {
                        playerData.player().sendMessage(essentialsReloaded.messageHandler().getMessage("gamemode." + (self ? "set_own" : "set_other")).placeholder("gamemode", StringUtils.capitalize(gamemodeData.gamemode.name().toLowerCase())).placeholder("player", player.getDisplayName()).build());
                    }
                }
            }

        }
    }

    @Override
    public List<String> tab(CommandSender sender, Command command, String[] args, PlayerData playerData) {
        if (args.length == 1) {
            List<String> x = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], GAMEMODES, x);
            return x.stream().sorted().collect(Collectors.toList());
        } else if (args.length == 2) {
            List<String> x = new ArrayList<>();
            StringUtil.copyPartialMatches(args[1], Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()), x);
            return x;
        } else return Lists.newArrayList();


    }

    private GamemodeData gamemode(Player sender, Player target, String gamemode) {
        GamemodeData data = new GamemodeData(false, null);
        if (target.hasPermission(this.permission + ".exempt")) return data.exempt();
        if (sender.hasPermission(this.permission + "." + (sender.getUniqueId().equals(target.getUniqueId()) ? "own" : "other") + "." + gamemode.toLowerCase())) {

            if (pattern_s.matcher(gamemode).find()) {
                target.setGameMode(GameMode.SURVIVAL);
                data.gamemode = GameMode.SURVIVAL;
                data.changed = true;

            } else if (pattern_c.matcher(gamemode).find()) {
                target.setGameMode(GameMode.CREATIVE);
                data.gamemode = GameMode.CREATIVE;
                data.changed = true;
            } else if (pattern_a.matcher(gamemode).find()) {
                target.setGameMode(GameMode.ADVENTURE);
                data.gamemode = GameMode.ADVENTURE;
                data.changed = true;
            } else if (pattern_sp.matcher(gamemode).find()) {
                target.setGameMode(GameMode.SPECTATOR);
                data.gamemode = GameMode.SPECTATOR;
                data.changed = true;
            }
        } else return data.noperms();
        return data;
    }

    private class GamemodeData {
        public boolean changed;
        public GameMode gamemode;
        public boolean noperm;
        public boolean target_exempt;

        public GamemodeData(boolean changed, GameMode gamemode) {
            this.changed = changed;
            this.gamemode = gamemode;
            this.noperm = false;
            this.target_exempt = false;
        }

        public GamemodeData exempt() {
            this.target_exempt = true;
            return this;
        }

        public GamemodeData noperms() {
            this.noperm = true;
            return this;

        }
    }
}
