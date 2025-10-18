package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotConteiner.VelocityTest;
import org.firstinspires.ftc.teamcode.RobotConteiner.auto1;
import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.CommandScheduler;
@TeleOp
public class MainTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        new VelocityTest(gamepad1,hardwareMap,telemetry);
        waitForStart();
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run(this);
            telemetry.update();
        }
        CommandScheduler.getInstance().m_scheduledCommands.clear();

    }
}
