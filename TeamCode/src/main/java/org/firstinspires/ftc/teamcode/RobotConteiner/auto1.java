package org.firstinspires.ftc.teamcode.RobotConteiner;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands.RoadRunnerAdministrator;
import org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands.StrafeTo;
import org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands.StrafeToLInear;
import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Trigger;

public class auto1 {

    private final MecanumDrive chassis;
    //private final PID Pid;
    private final Gamepad gamepad;

    public auto1(Gamepad gamepad, HardwareMap hardwareMap, Telemetry telemetry) {
        this.chassis =  new MecanumDrive(hardwareMap,new Pose2d(new Vector2d(0,0),Math.toRadians(-135)),telemetry);
        this.gamepad = gamepad;
        auto1();
    }
    public  void auto1(){
       /* new Trigger(()-> gamepad.b).toggleOnTrue(
                new SequentialCommandGroup(
                        new StrafeToLInear(chassis, new Vector2d(-30,-20),Math.toRadians(135)),//posição para obelisco
                        //todo o processo de ordenação do artefato
                        new StrafeToLInear(chassis, new Vector2d(-30,-27),Math.toRadians(45))
                )-
        );*/
        new SequentialCommandGroup(
                new StrafeToLInear(chassis, new Vector2d(-30,-20),Math.toRadians(135)),//posição para obelisco
                //todo o processo de ordenação do artefato
                new StrafeToLInear(chassis, new Vector2d(-30,-27),Math.toRadians(45))
        ).schedule();
    }
}
