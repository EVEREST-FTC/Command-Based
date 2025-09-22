package org.firstinspires.ftc.teamcode.ufpackages.CommandBased;

import java.util.function.BooleanSupplier;

public class Trigger implements BooleanSupplier {
    /** Functional interface for the body of a trigger binding. */
    @FunctionalInterface
    private interface BindingBody {
        /**
         * Executes the body of the binding.
         *
         * @param previous The previous state of the condition.
         * @param current The current state of the condition.
         */
        void run(boolean previous, boolean current);
    }

    private final BooleanSupplier m_condition;
    private final Event m_loop;

    /**
     * Creates a new trigger based on the given condition.
     *
     * @param loop The loop instance that polls this trigger.
     * @param condition the condition represented by this trigger
     */
    public Trigger(Event loop, BooleanSupplier condition) {
        m_loop = loop;
        m_condition = condition;
    }

    /**
     * Creates a new trigger based on the given condition.
     *
     * <p>Polled by the default scheduler button loop.
     *
     * @param condition the condition represented by this trigger
     */
    public Trigger(BooleanSupplier condition) {
        this(CommandScheduler.getInstance().getDefaultButtonLoop(), condition);
    }

    /**
     * Adds a binding to the EventLoop.
     *
     * @param body The body of the binding to add.
     */
    private void addBinding(BindingBody body) {
        m_loop.bind(
                new Runnable() {
                    private boolean m_previous = m_condition.getAsBoolean();

                    @Override
                    public void run() {
                        boolean current = m_condition.getAsBoolean();

                        body.run(m_previous, current);

                        m_previous = current;
                    }
                });
    }

    /**
     * Starts the command when the condition changes.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger onChange(Command command) {
        addBinding(
                (previous, current) -> {
                    if (previous != current) {
                        command.schedule();
                    }
                });
        return this;
    }

    /**
     * Starts the given command whenever the condition changes from `false` to `true`.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger onTrue(Command command) {
        addBinding(
                (previous, current) -> {
                    if (!previous && current) {
                        command.schedule();
                    }
                });
        return this;
    }

    /**
     * Starts the given command whenever the condition changes from `true` to `false`.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger onFalse(Command command) {
        addBinding(
                (previous, current) -> {
                    if (previous && !current) {
                        command.schedule();
                    }
                });
        return this;
    }

    /**
     * Starts the given command when the condition changes to `true` and cancels it when the condition
     * changes to `false`.
     *
     * <p>Doesn't re-start the command if it ends while the condition is still `true`. If the command
     * should restart
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger whileTrue(Command command) {
        addBinding(
                (previous, current) -> {
                    if (!previous && current) {
                        command.schedule();
                    } else if (previous && !current) {
                        command.cancel();
                    }
                });
        return this;
    }

    /**
     * Starts the given command when the condition changes to `false` and cancels it when the
     * condition changes to `true`.
     *
     * <p>Doesn't re-start the command if it ends while the condition is still `false`. If the command
     * should restart.
     *
     * @param command the command to start
     * @return this trigger, so calls can be chained
     */
    public Trigger whileFalse(Command command) {
        addBinding(
                (previous, current) -> {
                    if (previous && !current) {
                        command.schedule();
                    } else if (!previous && current) {
                        command.cancel();
                    }
                });
        return this;
    }

    /**
     * Toggles a command when the condition changes from `false` to `true`.
     *
     * @param command the command to toggle
     * @return this trigger, so calls can be chained
     */
    public Trigger toggleOnTrue(Command command) {
        addBinding(
                (previous, current) -> {
                    if (!previous && current) {
                        if (command.isScheduled()) {
                            command.cancel();
                        } else {
                            command.schedule();
                        }
                    }
                });
        return this;
    }

    /**
     * Toggles a command when the condition changes from `true` to `false`.
     *
     * @param command the command to toggle
     * @return this trigger, so calls can be chained
     */
    public Trigger toggleOnFalse(Command command) {
        addBinding(
                (previous, current) -> {
                    if (previous && !current) {
                        if (command.isScheduled()) {
                            command.cancel();
                        } else {
                            command.schedule();
                        }
                    }
                });
        return this;
    }

    @Override
    public boolean getAsBoolean() {
        return m_condition.getAsBoolean();
    }

    /**
     * Composes two triggers with logical AND.
     *
     * @param trigger the condition to compose with
     * @return A trigger which is active when both component triggers are active.
     */
    public Trigger and(BooleanSupplier trigger) {
        return new Trigger(m_loop, () -> m_condition.getAsBoolean() && trigger.getAsBoolean());
    }

    /**
     * Composes two triggers with logical OR.
     *
     * @param trigger the condition to compose with
     * @return A trigger which is active when either component trigger is active.
     */
    public Trigger or(BooleanSupplier trigger) {
        return new Trigger(m_loop, () -> m_condition.getAsBoolean() || trigger.getAsBoolean());
    }

    /**
     * Creates a new trigger that is active when this trigger is inactive, i.e. that acts as the
     * negation of this trigger.
     *
     * @return the negated trigger
     */
    public Trigger negate() {
        return new Trigger(m_loop, () -> !m_condition.getAsBoolean());
    }

    /**
     * Creates a new debounced trigger from this trigger - it will become active when this trigger has
     * been active for longer than the specified period.
     *
     * @param seconds The debounce period.
     * @return The debounced trigger (rising edges debounced only)
     */
}
