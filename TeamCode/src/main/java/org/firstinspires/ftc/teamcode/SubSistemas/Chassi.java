package org.firstinspires.ftc.teamcode.SubSistemas;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.SubsystemBase;

public class Chassi extends SubsystemBase {
    DcMotor MFR,MFL,MBR,MBL;
    IMU imu;

    Limelight3A limelight3A;

    public Chassi(HardwareMap hardwareMap){
        MFL = hardwareMap.get(DcMotor.class,"MFL");
        MFR = hardwareMap.get(DcMotor.class,"MFR");
        MBL = hardwareMap.get(DcMotor.class,"MBL");
        MBR = hardwareMap.get(DcMotor.class,"MBR");
        MFL.setDirection(DcMotorSimple.Direction.REVERSE);
        MBL.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = hardwareMap.get(IMU.class, "imu");
        limelight3A = hardwareMap.get(Limelight3A.class,"limelight3A");
        limelight3A.start();

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
    public double get_yaw(){
        return imu.getRobotYawPitchRollAngles().getYaw();
    }
    public  double get_tx(){
        return limelight3A.getLatestResult().getTx();
    }
    public  double get_ty(){
        return limelight3A.getLatestResult().getTy();
    }
    public boolean isvalid(){
        return limelight3A.getLatestResult().isValid();
    }


    public void stop(){
        drive(0.0, 0.0, 0.0);
    }
}
