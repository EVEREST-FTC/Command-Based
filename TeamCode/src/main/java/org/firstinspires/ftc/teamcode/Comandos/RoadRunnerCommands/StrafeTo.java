package org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

public class StrafeTo extends Command {
    private final MecanumDrive mecanumDrive;
    private final Vector2d vector2d;

    private boolean inTrajectory;

    TrajectoryActionBuilder trajectoryActionBuilder;
    Action action;
    public StrafeTo(MecanumDrive mecanumDrive, Vector2d vector2d) {
        this.mecanumDrive = mecanumDrive;
        this.vector2d = vector2d;
        addRequirements(mecanumDrive);
    }

    @Override
    protected void initialize() {
        inTrajectory = true;
        trajectoryActionBuilder = mecanumDrive.actionBuilder(
                mecanumDrive.localizer.getPose());
        action = trajectoryActionBuilder.strafeTo(vector2d).build();
    }

    @Override
    public void execute() {
        inTrajectory = action.run(new TelemetryPacket());
    }


    @Override
    protected boolean isFinished() {
        return !inTrajectory;
    }
}
