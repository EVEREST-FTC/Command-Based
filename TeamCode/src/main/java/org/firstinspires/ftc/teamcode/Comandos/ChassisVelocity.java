package org.firstinspires.ftc.teamcode.Comandos;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

public class ChassisVelocity extends Command {
    private Chassi chassi;
    private double velocidade;

    ElapsedTime time = new ElapsedTime();
    public ChassisVelocity(Chassi chassi,double velocidade) {
        this.chassi = chassi;
        this.velocidade = velocidade;
        addRequirements(chassi);
    }

    @Override
    protected void initialize() {
        time.reset();
    }

    @Override
    public void execute() {
        chassi.setVelocity(velocidade);
    }

    @Override
    public void end(boolean interrupted) {
        chassi.setVelocity(0);
        chassi.setUtimate(time.time());

    }

}
