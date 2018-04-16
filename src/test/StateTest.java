import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.regularlanguage.State;
import larp.parser.regularlanguage.StateTransition;

public class StateTest
{
    @Test
    public void testStructureEqualsReturnsTrueForSameAcceptsValueOnSingleState()
    {
        State state = new TestState("S0", true);

        assertTrue(state.structureEquals(new TestState("S1", true)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentAcceptsValueOnSingleState()
    {
        State state = new TestState("S0", true);

        assertFalse(state.structureEquals(new TestState("S1", false)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentNumberOfTransitions()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character>('a', state));

        assertFalse(state.structureEquals(new TestState("S1", true)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentTransitionCharacters()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character>('a', state));
        State otherState = new TestState("S1", true);
        otherState.addTransition(new StateTransition<Character>('b', otherState));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameTransitionCharacters()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character>('a', new TestState("S1", true)));
        State otherState = new TestState("S2", true);
        otherState.addTransition(new StateTransition<Character>('a', new TestState("S3", true)));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameTransitionCharactersInDifferentOrder()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character>('a', new TestState("S1", true)));
        state.addTransition(new StateTransition<Character>('b', new TestState("S2", true)));
        State otherState = new TestState("S3", true);
        otherState.addTransition(new StateTransition<Character>('b', new TestState("S4", true)));
        otherState.addTransition(new StateTransition<Character>('a', new TestState("S5", true)));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForSubsequentStateInequality()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character>('a', new TestState("S1", true)));
        State otherState = new TestState("S2", true);
        otherState.addTransition(new StateTransition<Character>('a', new TestState("S3", false)));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForStateGraphContainingCycle()
    {
        State state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character>('a', state));
        State otherState = new TestState("S0", true);
        otherState.addTransition(new StateTransition<Character>('a', otherState));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForStateGraphContainingDifferentCycle()
    {
        State state1 = new TestState("S0", true);
        State state2 = new TestState("S1", true);
        State state3 = new TestState("S1", true);
        state1.addTransition(new StateTransition<Character>('a', state2));
        state2.addTransition(new StateTransition<Character>('a', state3));
        state3.addTransition(new StateTransition<Character>('a', state1));

        State otherState1 = new TestState("S0", true);
        State otherState2 = new TestState("S1", true);
        State otherState3 = new TestState("S1", true);
        otherState1.addTransition(new StateTransition<Character>('a', otherState2));
        otherState2.addTransition(new StateTransition<Character>('a', otherState3));
        otherState3.addTransition(new StateTransition<Character>('a', otherState2));

        assertFalse(state1.structureEquals(otherState1));
    }

    @Test
    public void testStructureEqualsReturnsFalseForUnmatchedLoopFoundInParallelRecursion()
    {
        State state1 = new TestState("S0", false);
        State state2 = new TestState("S1", false);
        State state3 = new TestState("S2", false);
        State state4 = new TestState("S3", false);
        state1.addTransition(new StateTransition<Character>('a', state2));
        state1.addTransition(new StateTransition<Character>('b', state3));
        state2.addTransition(new StateTransition<Character>('a', state4));
        state3.addTransition(new StateTransition<Character>('a', state4));

        State otherState1 = new TestState("S0", false);
        State otherState2 = new TestState("S1", false);
        State otherState3 = new TestState("S2", false);
        State otherState4 = new TestState("S4", false);
        State otherState5 = new TestState("S5", false);
        otherState1.addTransition(new StateTransition<Character>('a', otherState2));
        otherState1.addTransition(new StateTransition<Character>('b', otherState3));
        otherState2.addTransition(new StateTransition<Character>('a', otherState4));
        otherState3.addTransition(new StateTransition<Character>('a', otherState5));

        assertFalse(state1.structureEquals(otherState1));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentStateClass()
    {
        State state = new TestState("S0", true);
        State otherState = new OtherTestState("S1", true);

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentStateClassInSubsequentState()
    {
        State state = new TestState("S0", true);
        State nextState = new TestState("S1", true);
        state.addTransition(new StateTransition<Character>('a', nextState));

        State otherState = new TestState("S2", true);
        State otherNextState = new OtherTestState("S3", true);
        otherState.addTransition(new StateTransition<Character>('a', otherNextState));

        assertFalse(state.structureEquals(otherState));
    }

    private class TestState extends State<Character>
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }

    private class OtherTestState extends State<Character>
    {
        public OtherTestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
