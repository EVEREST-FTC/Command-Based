package org.firstinspires.ftc.teamcode.Comandos;

import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

import java.util.function.DoubleSupplier;

public class Girar extends Command {

    protected final Chassi chassis;

    private final double z;
    public Girar(Chassi chassis, double z) {
        this.chassis = chassis;
        this.z = z;

        addRequirements(chassis);
    }


    @Override
    public void execute() {
        double z = this.z;

        chassis.drive(0, 0, z);
    }
}
