package org.firstinspires.ftc.teamcode.Comandos;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.SubSistemas.Chassi;
import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;
import org.firstinspires.ftc.teamcode.rrres.util.Localizer;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

public class Alinhar extends Command {

    protected final MecanumDrive mecanumDrive;
    protected final PID Pid;
    private final double alvo;



    public Alinhar(MecanumDrive mecanumDrive, PID pid, double alvo) {
        this.mecanumDrive = mecanumDrive;
        this.Pid = pid;
        this.alvo = alvo;


        addRequirements(mecanumDrive);
    }

    @Override
    public void execute() {
        Localizer localizer = mecanumDrive.localizer;
        Pose2d pose2d = localizer.getPose();
        double heading = pose2d.heading.real;
        double power = Pid.calculate(heading, alvo);
        mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), power));
    }

    @Override
    public void end(boolean interrupted) {
        mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
    }
}
