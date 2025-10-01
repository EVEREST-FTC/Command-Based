package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;

public class WrapperCommand extends Command{
   final Command command;

    public WrapperCommand(Command command) {
        this.command = command;
    }

    @Override
    protected void initialize() {
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
    protected boolean isFinished() {
        return command.isFinished();

    }
}
