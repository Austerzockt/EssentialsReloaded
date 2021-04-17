package io.github.austerzockt.essentialsreloaded.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Utils {
    private static final CommandUtils commandUtils = new CommandUtils();
    private static final PlayerUtils playerUtils = new PlayerUtils();


    public static CommandUtils commandUtils() {
        return commandUtils;
    }

    public static PlayerUtils playerUtils() {
        return playerUtils;
    }

    public static class CommandUtils {
        public List<String> listOfMaterials(String input, boolean lowercaseOutput) {

            List<String> values = Arrays.stream(Material.values()).map(Enum::name).map(s -> {
                if (lowercaseOutput) return s.toLowerCase();
                else return s;
            }).filter(s -> !s.startsWith(Material.LEGACY_PREFIX)).collect(Collectors.toList());
            //Some weird BS with Concurrent Access and the Iterator in StringUtil.copyPartialMatches ... This fixes it, might not be a nice solution, but i am tired and i frankly dont fking care
            List<String> x = new ArrayList<>();
            StringUtil.copyPartialMatches(input, values, x);
            return x;
        }
    }

    public static class PlayerUtils {
        public boolean isOnline(String name) {
            return Bukkit.getOnlinePlayers().stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().orElse(null) != null;
        }

        public Player getPlayer(String name) {
            if (isOnline(name)) {
                return Bukkit.getPlayer(name);
            }
            return null;

        }
    }


}

