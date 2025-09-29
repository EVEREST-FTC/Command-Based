package org.firstinspires.ftc.teamcode.rrres.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.rrres.util.TankDrive;

public final class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose, telemetry);

            waitForStart();

            Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        /*.splineTo(new Vector2d(27, 42), Math.PI/4)*/
                        .strafeToLinearHeading(new Vector2d(35, 30),Math.PI/4)
                        /*.splineToLinearHeading(new Pose2d(new Vector2d(33, 40), Math.PI/4), Math.PI/4)*/
                        /*.splineTo(new Vector2d(0, 60), Math.PI)*/
                        .build());
        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(30, 30), Math.PI / 2)
                            .splineTo(new Vector2d(0, 60), Math.PI)
                            .build());
        } else {
            throw new RuntimeException();
        }
    }
}
