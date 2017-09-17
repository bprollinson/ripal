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
        State state = new TestState("S0", true);

        assertTrue(state.equals(new TestState("S1", true)));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentAcceptsValueOnSingleState()
    {
        State state = new TestState("S0", true);

        assertFalse(state.equals(new TestState("S1", false)));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentNumberOfTransitions()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition('a', state));

        assertFalse(state.equals(new TestState("S1", true)));
    }

    private class TestState extends State
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
