package com.everest.CommandBased.compositions;

import com.everest.CommandBased.definition.Command;

public class ParallelRaceGroup extends ParallelCommandGroup {
    public ParallelRaceGroup(Command... commands){
        super(commands);
    }

    @Override
    public boolean isFinished() {
        boolean[] k = {false};
        super.commands.forEach(
                (command, runningCommand)->{
                    k[0]|=command.isFinished();
                }
        );
        return k[0];
    }
}