package org.firstinspires.ftc.teamcode.RobotConteiner;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Comandos.Girar;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.RepeatCommand;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Trigger;

public class Hunttag {

    private final Chassi chassis;
    private final PID Pid;
    private final Gamepad gamepad;
    public Hunttag(Gamepad gamepad, HardwareMap hardwareMap, Telemetry telemetry){
        this.chassis = new Chassi(hardwareMap, telemetry);
        this.Pid = new PID(0.01);
        this.gamepad = gamepad;
        hunt();
    }
    private void hunt(){
        new Trigger(()->gamepad.b).toggleOnTrue(
                new Girar(chassis, 0.3)
        );
        new Trigger(()-> gamepad.a).toggleOnTrue(
                new RepeatCommand(
                        new Girar(chassis,0.3)
                ).ateQUe(chassis::isvalid)
        );

    }
}