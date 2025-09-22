package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;
import java.util.Map;
import java.util.LinkedHashMap;


public class ParallelCommandGroup extends CommandGroup{
    protected final Map<Command, Boolean> commands = new LinkedHashMap<>();

    public ParallelCommandGroup(Command... commands){
        super(commands);
        super.commands.forEach(
                c->this.commands.put(c, true)
        );
    }

    @Override
    protected void initialize() {
        for(Map.Entry<Command, Boolean> entry : this.commands.entrySet()){
            entry.getKey().initialize();
            entry.setValue(true);
        }

    }

    @Override
    public void execute() {
        for(Map.Entry<Command, Boolean> entry : this.commands.entrySet()){
            if(!entry.getValue()||isFinished())continue;
            entry.getKey().execute();
            if(entry.getKey().isFinished()){
                entry.getKey().end(false);
                entry.setValue(false);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        for(Map.Entry<Command, Boolean> entry : this.commands.entrySet()){
            if(entry.getValue()){
                entry.getKey().end(false);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        boolean[] k = {false};
        commands.forEach(
                (command, runningCommand)->{
                    k[0]&=command.isFinished();
                }
        );
        return k[0];
    }

}

