import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.State;
import larp.automaton.StateMachine;
import larp.automaton.StateTransition;

public class StateMachineTest
{
    @Test
    public void testAcceptsReturnsFalseInNonExceptingState()
    {
        StateMachine machine = this.buildStateMachine();

        assertFalse(machine.accepts(""));
    }

    @Test
    public void testAcceptsReturnsTrueInExceptingState()
    {
        StateMachine machine = this.buildStateMachine();

        assertTrue(machine.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForMissingTransition()
    {
        StateMachine machine = this.buildStateMachine();

        assertFalse(machine.accepts("ab"));
    }

    private StateMachine buildStateMachine()
    {
        State state0 = new State("S0", false);
        State state1 = new State("S1", true);
        state0.addTransition(new StateTransition('a', state1));

        return new StateMachine(state0);
    }
}
