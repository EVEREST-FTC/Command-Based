package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;

public abstract class Command implements Sendable {
    private final Set<Subsystem> m_requirements = new HashSet<>();
    protected Command(){
    }
    protected void initialize(){}
    public WrapperCommand finalmente(Runnable acao){
        return new WrapperCommand(this){
            @Override
            public void end(boolean interrupted) {
                super.end(interrupted);
                acao.run();
            }
        };
    }
    public void execute(){}
    public void end(boolean interrupted){}
    protected boolean isFinished(){ return false; }
    public Set<Subsystem> getRequirements(){
        return this.m_requirements;
    }

    public final void addRequirements(Subsystem... requirements){
        this.m_requirements.addAll(Arrays.asList(requirements));
    }
    public final void addRequirements(Collection<Subsystem> requirements){
        this.m_requirements.addAll(requirements);
    }
    @Override
    public void initSendable(SendableBuilder builder) {

    }
    public void schedule(){
        CommandScheduler.getInstance().schedule(this);
    }
    public void cancel(){
        CommandScheduler.getInstance().cancel(this);
    }
    public boolean isScheduled(){
        return CommandScheduler.getInstance().isScheduled(this);
    }
    public Command ateQUe(BooleanSupplier condition){
        return new ParallelRaceGroup(
                this, new ConditionalCommand(condition)
        );
    }
    public boolean hasRequirement(Subsystem requirement){return getRequirements().contains(requirement);}
    public InterruptBehavior getInterruptionBehavior() {
        return InterruptBehavior.cancelSelf;
    }
    public Command espere(double seconds){
        return new ParallelRaceGroup(
                this, new WaitCommand(seconds)
        );
    }
    public SequentialCommandGroup antesDe(Command before) {
        return new SequentialCommandGroup(before, this);
    }
   /* public Command onEnd(Runnable action){
        return this
    }*/
    public SequentialCommandGroup depois(Command after){
        return new SequentialCommandGroup(this,after);
    }

    public boolean runsWhenDisabled(){ return false; }
    public enum InterruptBehavior{
        cancelSelf,
        cancelIncoming
    }

}