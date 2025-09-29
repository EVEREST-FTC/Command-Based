package org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;

import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

public class StrafeToLInear extends Command {

private final MecanumDrive mecanumDrive;
private final Vector2d vector2d;

private boolean inTrajectory;

private final double alvo;

TrajectoryActionBuilder trajectoryActionBuilder;
Action action;
public StrafeToLInear(MecanumDrive mecanumDrive, Vector2d vector2d, double alvo) {
    this.mecanumDrive = mecanumDrive;
    this.vector2d = vector2d;
    this.alvo = alvo;
    addRequirements(mecanumDrive);
}

@Override
protected void initialize() {
    inTrajectory = true;
    trajectoryActionBuilder = mecanumDrive.actionBuilder(
            mecanumDrive.localizer.getPose());
    action = trajectoryActionBuilder.strafeToLinearHeading(vector2d,alvo).build();
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
