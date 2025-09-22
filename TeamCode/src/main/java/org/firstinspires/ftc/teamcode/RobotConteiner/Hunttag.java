package org.firstinspires.ftc.teamcode.RobotConteiner;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Comandos.Girar;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.RepeatCommand;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Trigger;

public class Hunttag {

    private final Chassi chassis;
    private final PID Pid;

    public Hunttag(PID pid,Chassi chassis){
        this.chassis = new Chassi(hardwareMap);
        this.Pid = new PID(0.01);
    }
    private void hunt(){
        new Trigger(()-> gamepad1.a).toggleOnTrue(
                new RepeatCommand(new Girar(chassis,0.5)).ateQUe(chassis::isvalid)



        );

    }
}