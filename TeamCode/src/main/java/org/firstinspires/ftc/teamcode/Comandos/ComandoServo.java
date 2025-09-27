package org.firstinspires.ftc.teamcode.Comandos;

import org.firstinspires.ftc.teamcode.SubSistemas.Intake;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

public class ComandoServo extends Command {
    Intake intake;
    double power;
    public ComandoServo(Intake intake,double power){
        this.intake = intake;
        this.power = power;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.set_power(power);
    }

    @Override
    public void end(boolean interrupted) {
        intake.set_power(0);
    }
}
