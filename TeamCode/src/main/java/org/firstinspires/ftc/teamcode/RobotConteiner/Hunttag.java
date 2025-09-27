package org.firstinspires.ftc.teamcode.RobotConteiner;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Comandos.ComandoServo;
import org.firstinspires.ftc.teamcode.Comandos.Dirigir;
import org.firstinspires.ftc.teamcode.Comandos.Girar;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.SubSistemas.Intake;
import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.RepeatCommand;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Trigger;

public class Hunttag {

    private final MecanumDrive chassis;
    //private final PID Pid;
    private final Gamepad gamepad;
    private final Intake intake;
    public Hunttag(Gamepad gamepad, HardwareMap hardwareMap, Telemetry telemetry){
        this.chassis = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0), telemetry);
        this.intake = new Intake(hardwareMap);
        //this.Pid = new PID(0.01);
        this.gamepad = gamepad;
        //mover();
    }
    private void hunt(){
        new Trigger(()->gamepad.b).toggleOnTrue(
                new Girar(chassis, 0.3)
        );
        /*new Trigger(()-> gamepad.a).toggleOnTrue(
                new RepeatCommand(
                        new Girar(chassis,0.3)
                ).ateQUe(chassis::isvalid)
        );*/

    }
    private  void mover(){
        chassis.setDefaultCommand(
                new Dirigir(chassis,()->-gamepad.right_stick_x*0.7,()->gamepad.left_stick_y*0.7,()->-gamepad.left_stick_x*0.7)
        );
        new Trigger(()->gamepad.a).toggleOnTrue(
                new ComandoServo(intake,1)
        );
        new Trigger(()->gamepad.b).toggleOnTrue(
                new ComandoServo(intake,-1)
        );
    }

}