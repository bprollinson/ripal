import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.State;
import larp.automaton.StateTransition;

public class StateTest
{
    @Test
    public void testEqualsReturnsTrueForSameAcceptsValueOnSingleState()
    {
        State state = new State("S0", true);

        assertTrue(state.equals(new State("S1", true)));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentcceptsValueOnSingleState()
    {
        State state = new State("S0", true);

        assertFalse(state.equals(new State("S1", false)));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentNumberOfTransitions()
    {
        State state = new State("S0", true);
        state.addTransition(new StateTransition('a', state));

        assertFalse(state.equals(new State("S1", true)));
    }
}
