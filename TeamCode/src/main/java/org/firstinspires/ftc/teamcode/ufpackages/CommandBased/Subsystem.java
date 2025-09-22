package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;


public interface Subsystem {
    default void periodic(){}
    default String getName(){
        return this.getClass().getSimpleName();
    }
    default void setDefaultCommand(Command defaultCommand){
        CommandScheduler.getInstance().setDefaultCommand(this, defaultCommand);
    }


}
