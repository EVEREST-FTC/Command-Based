package org.firstinspires.ftc.teamcode;



public class PID {



    private final double kp;
    private double error;

    public PID(double kp){
        this.kp = kp;
    }
    public double calculate(double measure, double alvo){
        error = alvo-measure;
        return error*kp;
    }
    public boolean atSetpoint(){
        return Math.abs(error)<=1;
    }

}
