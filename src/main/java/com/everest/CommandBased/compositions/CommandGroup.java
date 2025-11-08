package com.everest.CommandBased.compositions;

import com.everest.CommandBased.definition.Command;

import java.util.ArrayList;

public abstract class CommandGroup extends Command {
    protected ArrayList<Command> commands = new ArrayList<>();
    protected InterruptBehavior interruptBehavior = InterruptBehavior.cancelIncoming;
    protected CommandGroup(Command...commands){
        addCommands(commands);
    }
    protected void addCommands(Command... commands){
        for(Command command : commands){
            this.commands.add(command);
            addRequirements(command.getRequirements());
            if(command.getInterruptionBehavior()==InterruptBehavior.cancelSelf){
                this.interruptBehavior = InterruptBehavior.cancelSelf;
            }
        }
    }
}