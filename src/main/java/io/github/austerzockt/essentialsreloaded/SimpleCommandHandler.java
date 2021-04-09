package io.github.austerzockt.essentialsreloaded;

import io.github.austerzockt.essentialsreloaded.commands.AbstractCommand;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SimpleCommandHandler implements ICommandHandler {
    private EssentialsReloaded essentialsReloaded;
    private List<AbstractCommand> commands;
    public SimpleCommandHandler(EssentialsReloaded essentialsReloaded) {
        this.essentialsReloaded = essentialsReloaded;

        commands = new ArrayList<>();

        try (ScanResult scanResult = new ClassGraph().acceptPackages("io.github.austerzockt.essentialsreloaded.commands")
                .enableClassInfo().scan()) {
          List<Class<AbstractCommand>> result = scanResult
                    .getSubclasses(AbstractCommand.class.getName())
                    .loadClasses(AbstractCommand.class);

            result.forEach(s -> {
              try {
                  var cmd = s.getConstructor(EssentialsReloaded.class).newInstance(essentialsReloaded);

                 commands.add(cmd);
              } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                  e.printStackTrace();
              }

          });
        }


    }

    public List<AbstractCommand> getCommands() {
        return commands;
    }
}
