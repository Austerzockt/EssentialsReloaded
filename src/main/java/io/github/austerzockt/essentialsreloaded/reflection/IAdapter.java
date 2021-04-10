package io.github.austerzockt.essentialsreloaded.reflection;

import org.bukkit.entity.Player;

import java.util.List;

public interface IAdapter {

    void syncCommands();

    void hidePlayer(Player player, Player hideFrom);

    void hidePlayer(Player player, List<Player> hideFrom);

}
