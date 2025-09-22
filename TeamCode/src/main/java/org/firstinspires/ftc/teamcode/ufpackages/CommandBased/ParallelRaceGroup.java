package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;
public class ParallelRaceGroup extends ParallelCommandGroup{
    public ParallelRaceGroup(Command... commands){
        super(commands);
    }

    @Override
    protected boolean isFinished() {
        boolean[] k = {false};
        super.commands.forEach(
                (command, runningCommand)->{
                    k[0]|=command.isFinished();
                }
        );
        return k[0];
    }
}