package org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

public class TurnTo extends Command {
    private final double angulo;
    private final MecanumDrive mecanumDrive;
    private boolean inTrajectory;

    TrajectoryActionBuilder trajectoryActionBuilder;
    Action action;
    public TurnTo(MecanumDrive mecanumDrive, double angulo) {
        this.mecanumDrive = mecanumDrive;
        this.angulo = angulo;
        addRequirements(mecanumDrive);
    }

    @Override
    protected void initialize() {
        inTrajectory = true;
        trajectoryActionBuilder = mecanumDrive.actionBuilder(
                mecanumDrive.localizer.getPose());
        action = trajectoryActionBuilder.turn(angulo).build();
    }

    @Override
    public void execute() {
        inTrajectory = action.run(new TelemetryPacket());
    }

    @Override
    public void end(boolean interrupted) {
        mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
    }

    @Override
    protected boolean isFinished() {
        return !inTrajectory;
    }
}
