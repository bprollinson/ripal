import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.statemachine.State;
import larp.statemachine.StateMachine;
import larp.statemachine.StateTransition;

public class StateMachineTest
{
    @Test
    public void testAcceptsReturnsFalseInNonExceptingState()
    {
        StateMachine machine = this.buildStateMachine();

        assertEquals(false, machine.accepts(""));
    }

    @Test
    public void testAcceptsReturnsTrueInExceptingState()
    {
        StateMachine machine = this.buildStateMachine();

        assertEquals(true, machine.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForMissingTransition()
    {
        StateMachine machine = this.buildStateMachine();

        assertEquals(false, machine.accepts("ab"));
    }

    private StateMachine buildStateMachine()
    {
        State state0 = new State("S0", false);
        State state1 = new State("S1", true);
        state0.addTransition(new StateTransition('a', state1));

        return new StateMachine(state0);
    }
}
