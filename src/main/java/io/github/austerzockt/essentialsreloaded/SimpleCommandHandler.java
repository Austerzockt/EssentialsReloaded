package io.github.austerzockt.essentialsreloaded;

import com.google.common.collect.Maps;
import io.github.austerzockt.essentialsreloaded.commands.AbstractCommand;
import io.github.austerzockt.essentialsreloaded.exceptions.CommandNotFoundException;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleCommandHandler implements ICommandHandler {
    private final HashMap<Class<? extends AbstractCommand>, AbstractCommand> commands;
    private final EssentialsReloaded essentialsReloaded;

    public SimpleCommandHandler(String string) {
        this(EssentialsReloaded.instance(), string);
    }

    public SimpleCommandHandler(EssentialsReloaded essentialsReloaded, String string) {
        this.essentialsReloaded = essentialsReloaded;
        commands = Maps.newHashMap();
        try (ScanResult scanResult = new ClassGraph().acceptPackages("io.github.austerzockt.essentialsreloaded.commands")
                .enableClassInfo().scan()) {
            List<Class<AbstractCommand>> result = scanResult
                    .getSubclasses(AbstractCommand.class.getName())
                    .loadClasses(AbstractCommand.class);

            result.forEach(s -> {
                try {
                    var cmd = s.getConstructor(EssentialsReloaded.class).newInstance(this.essentialsReloaded);

                    commands.put(s, cmd);
                    EssentialsReloaded.instance().getLogger().severe(s.getName());
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    EssentialsReloaded.instance().getLogger().severe("Command " + s.getName() + " could not be instantiated. Possible reason: Missing or wrong constructor");
                    e.printStackTrace();
                }

            });
        }


    }

    @Override
    public List<AbstractCommand> getCommands() {
        return new ArrayList<>(commands.values());
    }

    /*
     *
     * @returns Returns the Instance of the AbstractCommand-class supplied
     * Note: this returns null if it is supplied null as an argument
     *
     * */
    @Override
    public AbstractCommand getCommand(Class<? extends AbstractCommand> commandClass) {
        AbstractCommand r;
        if (commandClass == null) return null;
        else r = commands.getOrDefault(commandClass, null);
        if (r == null)
            throw new CommandNotFoundException("This Command was not found. (This should in theory be impossible, what did you do??) " + commandClass.getName());
        return r;
    }
}
