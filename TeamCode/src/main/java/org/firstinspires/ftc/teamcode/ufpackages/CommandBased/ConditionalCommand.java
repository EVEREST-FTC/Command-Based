package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;

import java.util.function.BooleanSupplier;

public class ConditionalCommand extends Command{
    private BooleanSupplier condition;
    public ConditionalCommand(BooleanSupplier condition){
        this.condition = condition;
    }

    @Override
    protected boolean isFinished() {
        return condition.getAsBoolean();
    }
}
