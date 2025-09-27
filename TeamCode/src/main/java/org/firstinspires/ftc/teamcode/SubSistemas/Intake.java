package org.firstinspires.ftc.teamcode.SubSistemas;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.SubsystemBase;

public class Intake extends SubsystemBase {
    CRServo servo2;

    public Intake(HardwareMap hardwareMap){
        servo2 = hardwareMap.get(CRServo.class,"servo2");
    }
    public void set_power(double power){
        servo2.setPower(power);
    }


}
