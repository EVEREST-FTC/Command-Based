package org.firstinspires.ftc.teamcode.RobotConteiner;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Comandos.ChassisVelocity;
import org.firstinspires.ftc.teamcode.Comandos.ComandoServo;
import org.firstinspires.ftc.teamcode.Comandos.Dirigir;
import org.firstinspires.ftc.teamcode.Comandos.Girar;
import org.firstinspires.ftc.teamcode.Constantes;
import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.SubSistemas.Intake;
import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.InstantCommand;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.ParallelRaceGroup;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Trigger;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.WaitCommand;

public class VelocityTest {

    private final Chassi chassis;


    //private final PID Pid;


    public VelocityTest(Gamepad gamepad, HardwareMap hardwareMap, Telemetry telemetry){
        this.chassis = new Chassi(hardwareMap, telemetry);
        //this.Pid = new PID(0.01);
        new Trigger(()-> gamepad.b).toggleOnTrue(
                new ChassisVelocity(chassis, 0.70)
        );
        new Trigger(()-> gamepad.a).toggleOnTrue(
                new ChassisVelocity(chassis, 0.50)
        );
        new Trigger(()-> gamepad.y).toggleOnTrue(
                new ChassisVelocity(chassis, 0.85)
        );
        new Trigger(()-> gamepad.x).toggleOnTrue(
                new ChassisVelocity(chassis, 1)
        );
    }

}

