package com.everest.CommandBased.util;

import com.everest.CommandBased.definition.Clock;
import com.everest.CommandBased.definition.Command;

public class WaitCommand extends Command {
    protected final Clock timer;
    private final double duration;

    public WaitCommand(double duration, Clock timer) {
        this.duration = duration;
        this.timer = timer;
    }

    @Override
    public void initialize() {
        this.timer.reset();
    }

    @Override
    public boolean isFinished() {
        return this.timer.getTime()>=duration;
    }
}
