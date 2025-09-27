package org.firstinspires.ftc.teamcode.Comandos;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

import java.util.function.DoubleSupplier;

public class Girar extends Command {

    protected final MecanumDrive mecanumDrive;

    private final double z;
    public Girar(MecanumDrive mecanumDrive, double z) {
        this.mecanumDrive = mecanumDrive;
        this.z = z;

        addRequirements(mecanumDrive);
    }


    @Override
    public void execute() {
        mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), z));
    }

    @Override
    public void end(boolean interrupted) {
        mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
    }
}
