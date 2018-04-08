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

    private class TestState extends State
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
