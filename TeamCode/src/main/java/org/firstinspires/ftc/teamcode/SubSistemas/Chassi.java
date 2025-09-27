package org.firstinspires.ftc.teamcode.SubSistemas;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.CommandScheduler;
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.SubsystemBase;
@Deprecated
public class Chassi extends SubsystemBase {
    DcMotor MFR,MFL,MBR,MBL;
    IMU imu;

    /*Limelight3A limelight3A;*/
    Telemetry telemetry;

    public Chassi(HardwareMap hardwareMap, Telemetry telemetry){
        MFL = hardwareMap.get(DcMotor.class,"MFL");
        MFR = hardwareMap.get(DcMotor.class,"MFR");
        MBL = hardwareMap.get(DcMotor.class,"MBL");
        MBR = hardwareMap.get(DcMotor.class,"MBR");
        MFL.setDirection(DcMotorSimple.Direction.REVERSE);
        MBL.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = hardwareMap.get(IMU.class, "imu");
        MFR.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MBL.setMode(DcMotor.RunMode.RESET_ENCODERS);
        MFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        /*limelight3A = hardwareMap.get(Limelight3A.class,"limelight3A");*/

        this.telemetry = telemetry;
        telemetry.setMsTransmissionInterval(11);
        /*limelight3A.start();*/
        CommandScheduler.getInstance().registerSubsystem(this);
    }
    public void drive(double x, double y, double z){
        double frontLeftPower = x+y+z;
        double frontRightPower = x-y-z;
        double backLeftPower = x-y+z;
        double backRightPower = x+y-z;
        MFL.setPower(frontLeftPower);
        MFR.setPower(frontRightPower);
        MBL.setPower(backLeftPower);
        MBR.setPower(backRightPower);
    }

   /* @Override
    public void periodic() {
        telemetry.addData("isValid", isvalid());
    }
*/
    public void brake(){
        MFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
    public double get_yaw(){
        return imu.getRobotYawPitchRollAngles().getYaw();
    }
    /*public  double get_tx(){
        return limelight3A.getLatestResult().getTx();
    }
    public  double get_ty(){
        return limelight3A.getLatestResult().getTy();
    }
    public boolean isvalid(){
        return limelight3A.getLatestResult().isValid();
    }*/

    @Override
    public void periodic() {
        telemetry.addData("odometria 1",MFR.getCurrentPosition());
        telemetry.addData("odometria 2",MBL.getCurrentPosition());
    }

    public void stop(){
        drive(0.0, 0.0, 0.0);
    }
}
