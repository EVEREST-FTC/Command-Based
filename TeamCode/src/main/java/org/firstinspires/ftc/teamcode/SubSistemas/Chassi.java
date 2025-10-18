package org.firstinspires.ftc.teamcode.SubSistemas;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constantes;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.CommandScheduler;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.SubsystemBase;

@Deprecated
public class Chassi extends SubsystemBase {
    DcMotorEx MFR,MFL,MBR,MBL;
    IMU imu;

    private double Utimate = 0;


    Telemetry telemetry;

    public Chassi(HardwareMap hardwareMap, Telemetry telemetry){
        MFL = hardwareMap.get(DcMotorEx.class,"MFL");
        MFR = hardwareMap.get(DcMotorEx.class,"MFR");
        MBL = hardwareMap.get(DcMotorEx.class,"MBL");
        MBR = hardwareMap.get(DcMotorEx.class,"MBR");
        MFL.setDirection(DcMotorSimple.Direction.REVERSE);
        MBL.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = hardwareMap.get(IMU.class, "imu");
        MFR.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MBL.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        this.telemetry = telemetry;
        telemetry.setMsTransmissionInterval(11);

        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void drive(double x, double y, double z){
        double frontLeftPower = x+y+z;
        double frontRightPower = x-y-z;
        double backLeftPower = x-y+z;
        double backRightPower = x+y-z;
        MFL.setPower(frontLeftPower);
        MFR.setPower(frontRightPower);
        /*MBL.setPower(backLeftPower);*/
        /*MBR.setPower(backRightPower);*/
    }
    public void setVelocity(double velocity){
        /*velocity = (velocity*Constantes.taxadeconverçãoouttake)/60;*/
        MFR.setPower(velocity);
        MFL.setPower(velocity);

    }
    public void ResetEncoder(){
        MFR.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MFL.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void brake(){
        MFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
    @Override
    public void periodic() {
        telemetry.addData("velocidadeL", (MFL.getVelocity()/Constantes.taxadeconverçãoouttake)*60);
        telemetry.addData("velocidadeF", (MFR.getVelocity()/Constantes.taxadeconverçãoouttake)*60);
        telemetry.addData("positionL",MFL.getCurrentPosition()/ Constantes.taxadeconvercao1);
        telemetry.addData("positionR",MFR.getCurrentPosition()/Constantes.taxadeconvercao1);
        telemetry.addData("tempo",Utimate);


    }

    public void setUtimate(double utimate) {
        Utimate = utimate;
    }

    public void stop(){
        drive(0.0, 0.0, 0.0);
    }
}
