package io.github.austerzockt.essentialsreloaded;

import io.github.austerzockt.essentialsreloaded.commands.AbstractCommand;

import java.util.List;

public interface ICommandHandler {

    List<AbstractCommand> getCommands();

     <T extends AbstractCommand> T getCommand(Class<T> commandClass);

}
