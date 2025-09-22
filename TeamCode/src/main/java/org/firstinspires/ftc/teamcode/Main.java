package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.CommandScheduler;
@TeleOp(name = "main")
public class Main extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        new RobotContainer(telemetry,
                hardwareMap,
                gamepad1);
        waitForStart();
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run(this);
            telemetry.update();
        }
        CommandScheduler.getInstance().m_scheduledCommands.clear();
    }
}
