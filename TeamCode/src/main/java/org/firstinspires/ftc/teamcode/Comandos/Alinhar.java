package org.firstinspires.ftc.teamcode.Comandos;

import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

public class Alinhar extends Command {

    protected final Chassi chassis;
    protected final PID Pid;
    private final double alvo;



    public Alinhar(Chassi chassis, PID pid, double alvo) {
        this.chassis = chassis;
        this.Pid = pid;
        this.alvo = alvo;


        addRequirements(chassis);
    }

    @Override
    public void execute() {
        Pid.calculate(chassis.get_yaw(),alvo);
    }

    @Override
    public void end(boolean interrupted) {
        chassis.drive(0, 0, 0);
    }
}
