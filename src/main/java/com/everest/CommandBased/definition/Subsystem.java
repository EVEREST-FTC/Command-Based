package com.everest.CommandBased.definition;

public interface Subsystem {
    default void periodic(){}
    default String getName(){
        return this.getClass().getSimpleName();
    }
    default void setDefaultCommand(Command defaultCommand){
        CommandScheduler.getInstance().setDefaultCommand(this, defaultCommand);
    }


}
