package io.github.austerzockt.essentialsreloaded;

import io.github.austerzockt.essentialsreloaded.commands.AbstractCommand;
import io.github.austerzockt.essentialsreloaded.reflection.IAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class EssentialsReloaded extends JavaPlugin {
    private IAdapter adapter;
    private ICommandHandler commandHandler;
    @Override
    public void onEnable() {
        setupAdapter();
        setupCommandHandler();
        getLogger().severe(String.valueOf(commandHandler.getCommands().size()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private boolean setupCommandHandler() {
        commandHandler = new SimpleCommandHandler(this);
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
            Class<? extends IAdapter> clazz = (Class<? extends IAdapter>) Class.forName(IAdapter.class.getPackage().getName() + ".Adapter_"+ version);
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
