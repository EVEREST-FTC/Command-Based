package com.everest.CommandBased.util;
import com.everest.CommandBased.definition.Command;

import java.util.function.BooleanSupplier;

public class ConditionalCommand extends Command {
    private final BooleanSupplier condition;
    public ConditionalCommand(BooleanSupplier condition){
        this.condition = condition;
    }

    @Override
    public boolean isFinished() {
        return condition.getAsBoolean();
    }
}
