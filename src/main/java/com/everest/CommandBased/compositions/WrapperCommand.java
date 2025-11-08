package com.everest.CommandBased.compositions;

import com.everest.CommandBased.definition.Command;

public class WrapperCommand extends Command {
   final Command command;


    public WrapperCommand(Command command) {
        this.command = command;
    }

    @Override
    public void initialize() {
        command.initialize();
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public void end(boolean interrupted) {
        command.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return command.isFinished();

    }
}
