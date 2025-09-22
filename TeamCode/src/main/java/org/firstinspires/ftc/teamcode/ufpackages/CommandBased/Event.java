package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashSet;

public final class Event {
    private final Collection<Runnable> bindings = new LinkedHashSet<>();
    private boolean running;
    public Event(){}

    public void bind(Runnable action){
        if(running){
            throw new ConcurrentModificationException("cannot bind events while running!");
        }
        bindings.add(action);
    }
    @SuppressWarnings("PMD.UnusedAssigment")
    public void poll(){
        try{
            running = true;
            bindings.forEach(Runnable::run);
        }finally {
            running = false;
        }
    }

    public void clear(){
        if(running){
            throw new ConcurrentModificationException("cannot clear events while running");
        }
        bindings.clear();
    }
}


