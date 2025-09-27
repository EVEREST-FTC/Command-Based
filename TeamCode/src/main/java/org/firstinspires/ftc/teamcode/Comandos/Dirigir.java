package org.firstinspires.ftc.teamcode.Comandos;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class Dirigir extends Command {

    protected final MecanumDrive mecanumDrive;

    private final DoubleSupplier z;
    private final DoubleSupplier y;
    private final DoubleSupplier x;

    public Dirigir(MecanumDrive mecanumDrive, DoubleSupplier z,DoubleSupplier x,DoubleSupplier y) {
        this.mecanumDrive = mecanumDrive;
        this.z = z;
        this.y = y;
        this.x = x;

        addRequirements(mecanumDrive);
    }


    @Override
    public void execute() {
        double z = this.z.getAsDouble();
        double y = this.y.getAsDouble();
        double x = this.x.getAsDouble();
        mecanumDrive.brake();
        mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(x, y), z));
    }

    @Override
    public void end(boolean interrupted) {
        mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
    }
}
