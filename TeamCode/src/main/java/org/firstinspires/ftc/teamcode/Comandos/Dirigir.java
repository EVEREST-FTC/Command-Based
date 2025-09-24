package org.firstinspires.ftc.teamcode.Comandos;

import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class Dirigir extends Command {

    protected final Chassi chassis;

    private final DoubleSupplier z;
    private final DoubleSupplier y;
    private final DoubleSupplier x;

    public Dirigir(Chassi chassis, DoubleSupplier z,DoubleSupplier x,DoubleSupplier y) {
        this.chassis = chassis;
        this.z = z;
        this.y = y;
        this.x = x;

        addRequirements(chassis);
    }


    @Override
    public void execute() {
        double z = this.z.getAsDouble();
        double y = this.y.getAsDouble();
        double x = this.x.getAsDouble();
        chassis.brake();
        chassis.drive(x, y, z);
    }

    @Override
    public void end(boolean interrupted) {
        chassis.drive(0, 0, 0);
    }
}
