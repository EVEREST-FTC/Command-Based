package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;
public class InstantCommand extends FunctionalCommand {
    /**
     * Creates a new InstantCommand that runs the given Runnable with the given requirements.
     *
     * @param toRun the Runnable to run
     * @param requirements the subsystems required by this command
     */
    public InstantCommand(Runnable toRun, Subsystem... requirements) {
        super(toRun, () -> {}, interrupted -> {}, () -> true, requirements);
    }

    /**
     * Creates a new InstantCommand with a Runnable that does nothing. Useful only as a no-arg
     * constructor to call implicitly from subclass constructors.
     */
    public InstantCommand() {
        this(() -> {});
    }
}