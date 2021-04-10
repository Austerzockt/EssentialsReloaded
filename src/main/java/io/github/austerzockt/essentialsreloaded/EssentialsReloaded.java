package io.github.austerzockt.essentialsreloaded;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.austerzockt.essentialsreloaded.reflection.IAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class EssentialsReloaded extends JavaPlugin {
    private static EssentialsReloaded essentialsReloaded;
    private IAdapter adapter;
    private ICommandHandler commandHandler;

    public static EssentialsReloaded instance() {
        return essentialsReloaded;
    }

    @Override
    public void onEnable() {
        essentialsReloaded = this;
        if (!setupAdapter())
            disablePlugin("Reflective Access between Versions (\"Adapter\") couldn't be setup correctly \n You are probably running an unsupported Minecraft Version");
        if (!setupCommandHandler())
            disablePlugin("CommandHandler couldn't be set up correctly. \n Reflective Access is probably not working");
        getLogger().severe(String.valueOf(commandHandler.getCommands().size()));
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    }

    private void disablePlugin(String reason) {
        getLogger().severe("Plugin was disabled!");
        getLogger().severe("Reason: " + reason);
        this.getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupCommandHandler() {
        commandHandler = new SimpleCommandHandler(this, "io.github.austerzockt.essentialsreloaded.commands");
        return true;
    }

    private boolean setupAdapter() {

        String version;

        try {

            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            return false;
        }

        try {
            Class<? extends IAdapter> clazz = (Class<? extends IAdapter>) Class.forName(IAdapter.class.getPackage().getName() + ".Adapter_" + version);
            try {
                adapter = clazz.getConstructor(EssentialsReloaded.class).newInstance(this);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return adapter != null;
    }

    public IAdapter getAdapter() {
        return adapter;
    }
}
