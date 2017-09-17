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

    @Test
    public void testEqualsReturnsFalseForDifferentTransitionChracters()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition('a', state));
        State otherState = new TestState("S1", true);
        otherState.addTransition(new StateTransition('b', otherState));

        assertFalse(state.equals(otherState));
    }

    @Test
    public void testEqualsReturnsTrueForSameTransitionChracters()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition('a', new TestState("S1", true)));
        State otherState = new TestState("S2", true);
        otherState.addTransition(new StateTransition('a', new TestState("S3", true)));

        assertTrue(state.equals(otherState));
    }

    @Test
    public void testEqualsReturnsTrueForSameTransitionCharactersInDifferentOrder()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition('a', new TestState("S1", true)));
        state.addTransition(new StateTransition('b', new TestState("S2", true)));
        State otherState = new TestState("S3", true);
        otherState.addTransition(new StateTransition('b', new TestState("S4", true)));
        otherState.addTransition(new StateTransition('a', new TestState("S5", true)));

        assertTrue(state.equals(otherState));
    }

    @Test
    public void testEqualsReturnsFalseForSubsequentStateInequality()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition('a', new TestState("S1", true)));
        State otherState = new TestState("S2", true);
        otherState.addTransition(new StateTransition('a', new TestState("S3", false)));

        assertFalse(state.equals(otherState));
    }

    @Test
    public void testEqualsReturnsTrueForStateGraphContainingCycle()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition('a', state));
        State otherState = new TestState("S0", true);
        otherState.addTransition(new StateTransition('a', otherState));

        assertTrue(state.equals(otherState));
    }

    private class TestState extends State
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
