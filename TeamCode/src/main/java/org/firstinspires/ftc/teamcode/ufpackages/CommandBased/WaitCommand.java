package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;

import com.qualcomm.robotcore.util.ElapsedTime;

public class WaitCommand extends Command{
    protected ElapsedTime timer  = new ElapsedTime();
    private final double duration;

    public WaitCommand(double duration){
        this.duration = duration;
    }

    @Override
    protected void initialize() {
        this.timer.reset();
    }

    @Override
    protected boolean isFinished() {
        return this.timer.seconds()>=duration;
    }
}
