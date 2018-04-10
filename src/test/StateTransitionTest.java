import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.regularlanguage.State;
import larp.parser.regularlanguage.StateTransition;

public class StateTransitionTest
{
    @Test
    public void testInputEqualsReturnsTrueWhenInputMatchesExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);

        assertTrue(transition.inputEquals('a'));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenInputDoesNotMatchExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);

        assertFalse(transition.inputEquals('b'));
    }

    @Test
    public void testInputEqualsReturnsTrueWhenEmptyInputMatchesExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition(null, state);

        assertTrue(transition.inputEquals(null));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenNonEmptyInputDoesNotMatchExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition(null, state);

        assertFalse(transition.inputEquals('a'));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenEmptyInputDoesNotMatchExpectedNonEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);

        assertFalse(transition.inputEquals(null));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsTrueWhenInputMatchesExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);
        StateTransition otherTransition = new StateTransition('a', state);

        assertTrue(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenInputDoesNotMatchExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);
        StateTransition otherTransition = new StateTransition('b', state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsTrueWhenEmptyInputMatchesExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition(null, state);
        StateTransition otherTransition = new StateTransition(null, state);

        assertTrue(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenNonEmptyInputDoesNotMatchExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition(null, state);
        StateTransition otherTransition = new StateTransition('a', state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenEmptyInputDoesNotMatchExpectedNonEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);
        StateTransition otherTransition = new StateTransition(null, state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    private class TestState extends State
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
