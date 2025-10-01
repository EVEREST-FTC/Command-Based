package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.RobotConteiner.Hunttag;
import org.firstinspires.ftc.teamcode.RobotConteiner.auto1;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.CommandScheduler;
@TeleOp(name = "main")
public class Main extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        auto1 rcAuto = new auto1(
                gamepad1,hardwareMap, telemetry);
        waitForStart();
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run(this);
            if (gamepad1.a)
                CommandScheduler.getInstance().m_scheduledCommands.clear();
            telemetry.update();
        }
        CommandScheduler.getInstance().m_scheduledCommands.clear();
        rcAuto.publish();
    }
}
