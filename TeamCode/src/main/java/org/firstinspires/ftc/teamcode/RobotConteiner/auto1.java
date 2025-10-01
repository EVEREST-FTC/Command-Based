package org.firstinspires.ftc.teamcode.RobotConteiner;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Comandos.Dirigir;
import org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands.RoadRunnerAdministrator;
import org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands.StrafeTo;
import org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands.StrafeToLInear;
import org.firstinspires.ftc.teamcode.SIteFactory;
import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.CommandScheduler;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.InstantCommand;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Trigger;

import java.util.ArrayList;
import java.util.List;

public class auto1 {

    private final MecanumDrive chassis;
    //private final PID Pid;
    private final Gamepad gamepad;
    public final StringBuilder poses;

    private final Command leftRed;
    private final Command rightRed;

    public auto1(Gamepad gamepad, HardwareMap hardwareMap, Telemetry telemetry) {
        /*this.chassis =  new MecanumDrive(hardwareMap,new Pose2d(new Vector2d(0,0),Math.toRadians(-135)),telemetry);*/
        this.chassis =  new MecanumDrive(hardwareMap,new Pose2d(new Vector2d(0,0),Math.toRadians(0)),telemetry);
        this.gamepad = gamepad;
        this.poses = new StringBuilder();
        leftRed = new SequentialCommandGroup(
                new StrafeToLInear(chassis,
                        new Vector2d(-30,-20),Math.toRadians(135)),//posição para obelisco
                //todo o processo de ordenação do artefato
                new StrafeToLInear(chassis, new Vector2d(-37,-34),Math.toRadians(45)),
                //Rota 1
                new StrafeToLInear(chassis, new Vector2d(-13.140634,-45.042238),0),
                //Rota 2
                new StrafeToLInear(chassis, new Vector2d(-13.754075,-67.138244),0),
                //Rota 3
                new StrafeToLInear(chassis, new Vector2d(-10.350324,-93.901646),0),
                // Posição de lançamento
                new StrafeToLInear(chassis, new Vector2d(-37,-34),Math.toRadians(45)
                ));
        rightRed = new SequentialCommandGroup(
                //atirar
                new StrafeToLInear(chassis, new Vector2d(88.110475,12.240065),-0.818125),
                //Rota 1
                new StrafeToLInear(chassis, new Vector2d(70.005267,-6.738443),-1.567105),
                //Rota 2
                new StrafeToLInear(chassis, new Vector2d(51.438563,-3.616233),-1.567105),
                //Rota 3
                new StrafeToLInear(chassis, new Vector2d(25.167604,-9.768014),-1.567105)
        );
        auto1();
    }
    public  void auto1(){

        rightRed.schedule();

        new Trigger(()-> gamepad.a).toggleOnTrue(new InstantCommand(()->poses.append(
               String.format("new StrafeToLInear(chassis, new Vector2d(%f,%f),%f)," +
                               "",
                       chassis.localizer.getPose().position.x,
                       chassis.localizer.getPose().position.y,
                       chassis.localizer.getPose().heading.toDouble()
               )
        )));
        new Trigger(()-> gamepad.b).toggleOnTrue(
                new Dirigir(chassis,
                        ()-> -gamepad.right_stick_x*0.5,
                        ()-> -gamepad.left_stick_y*0.5,
                        ()-> -gamepad.left_stick_x*0.5)

        );



    }

    public void publish(){
        SIteFactory.publish(poses.toString());
    }
}
