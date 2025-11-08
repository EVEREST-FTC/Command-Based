package com.everest.CommandBased.compositions;

import com.everest.CommandBased.definition.Command;

public class RepeatCommand extends Command {
    private final Command command;
    private boolean ended;
    public RepeatCommand(Command command){
        this.command = command;
        addRequirements(command.getRequirements());
    }

    @Override
    public void initialize() {
        ended = false;
        command.initialize();
    }

    @Override
    public void execute() {
        if(ended){
            ended = false;
            command.initialize();
        }
        command.execute();
        if(command.isFinished()){
            command.end(false);
            ended = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        if(!ended){
            command.end(interrupted);
            ended = true;
        }
    }
}
