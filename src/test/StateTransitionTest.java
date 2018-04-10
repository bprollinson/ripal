import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.regularlanguage.State;
import larp.parser.regularlanguage.StateTransition;

public class StateTransitionTest
{
    @Test
    public void testInputEqualsReturnsTrueWhenCharacterInputMatchesExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);

        assertTrue(transition.inputEquals('a'));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenCharacterInputDoesNotMatchExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);

        assertFalse(transition.inputEquals('b'));
    }

    @Test
    public void testInputEqualsReturnsTrueWhenEmptyCharacterInputMatchesExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition(null, state);

        assertTrue(transition.inputEquals(null));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenNonEmptyCharacterInputDoesNotMatchExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition(null, state);

        assertFalse(transition.inputEquals('a'));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenEmptyCharacterInputDoesNotMatchExpectedNonEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition transition = new StateTransition('a', state);

        assertFalse(transition.inputEquals(null));
    }

    @Test
    public void testInputEqualsReturnsTrueWhenStateTransitionInputMatchesExpectedInput()
    {
        assertTrue(false);
    }

    @Test
    public void testInputEqualsReturnsFalseWhenStateTransitionInputDoesNotMatchExpectedInput()
    {
        assertTrue(false);
    }

    @Test
    public void testInputEqualsReturnsTrueWhenEmptyStateTransitionInputMatchesExpectedEmptyInput()
    {
        assertTrue(false);
    }

    @Test
    public void testInputEqualsReturnsFalseWhenNonEmptyStateTransitionInputDoesNotMatchExpectedEmptyInput()
    {
        assertTrue(false);
    }

    @Test
    public void testInputEqualsReturnsFalseWhenEmptyStateTransitionInputDoesNotMatchExpectedNonEmptyInput()
    {
        assertTrue(false);
    }

    private class TestState extends State
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
