package io.github.austerzockt.essentialsreloaded;

import io.github.austerzockt.essentialsreloaded.config.IConfigHandler;
import io.github.austerzockt.essentialsreloaded.config.SimpleConfigHandler;
import io.github.austerzockt.essentialsreloaded.localization.IMessageHandler;
import io.github.austerzockt.essentialsreloaded.localization.SimpleMessageHandler;
import io.github.austerzockt.essentialsreloaded.reflection.IAdapter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class EssentialsReloaded extends JavaPlugin implements Listener {
    private static EssentialsReloaded essentialsReloaded;
    private IAdapter adapter;
    private ICommandHandler commandHandler;
    private IConfigHandler configHandler;
    private IMessageHandler messageHandler;

    public static EssentialsReloaded instance() {
        return essentialsReloaded;
    }

    public static IAdapter adapter() {
        return instance().adapter;
    }


    public IConfigHandler configHandler() {
        return configHandler;
    }

    public ICommandHandler commandHandler() {
        return commandHandler;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        setupConfigHandler();
        setupConfigs();
        essentialsReloaded = this;
        if (!setupAdapter())
            disablePlugin("Reflective Access between Versions (\"Adapter\") couldn't be setup correctly \n You are probably running an unsupported Minecraft Version");
        if (!setupCommandHandler())
            disablePlugin("CommandHandler couldn't be set up correctly. \n Reflective Access is probably not working");
        getLogger().severe(String.valueOf(commandHandler.getCommands().size()));

    }

    @EventHandler
    public void event(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(messageHandler.getMessage("test").placeholder("pl", event.getPlayer().getDisplayName()).build());
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

    private boolean setupConfigs() {
        configHandler.registerConfig("config");
        String languageCode = configHandler.getConfig("config").getString("language");
        messageHandler = new SimpleMessageHandler(this, languageCode);
        return true;
    }

    private boolean setupConfigHandler() {
        configHandler = new SimpleConfigHandler(this);
        return true;
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
}
