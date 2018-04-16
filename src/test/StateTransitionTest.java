import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.regularlanguage.State;
import larp.parser.regularlanguage.StateTransition;

import java.util.ArrayList;
import java.util.List;

public class StateTransitionTest
{
    @Test
    public void testInputEqualsReturnsTrueWhenInputMatchesExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>('a', state);

        assertTrue(transition.inputEquals('a'));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenInputDoesNotMatchExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>('a', state);

        assertFalse(transition.inputEquals('b'));
    }

    @Test
    public void testInputEqualsReturnsTrueWhenEmptyInputMatchesExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>(null, state);

        assertTrue(transition.inputEquals(null));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenNonEmptyInputDoesNotMatchExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>(null, state);

        assertFalse(transition.inputEquals('a'));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenEmptyInputDoesNotMatchExpectedNonEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>('a', state);

        assertFalse(transition.inputEquals(null));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenInputDoesNotMatchExpectedInputOfAnotherType()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>('a', state);

        assertFalse(transition.inputEquals(1));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsTrueWhenInputMatchesExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>('a', state);
        StateTransition<Character> otherTransition = new StateTransition<Character>('a', state);

        assertTrue(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenInputDoesNotMatchExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>('a', state);
        StateTransition<Character> otherTransition = new StateTransition<Character>('b', state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsTrueWhenEmptyInputMatchesExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>(null, state);
        StateTransition<Character> otherTransition = new StateTransition<Character>(null, state);

        assertTrue(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenNonEmptyInputDoesNotMatchExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>(null, state);
        StateTransition<Character> otherTransition = new StateTransition<Character>('a', state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenEmptyInputDoesNotMatchExpectedNonEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>('a', state);
        StateTransition<Character> otherTransition = new StateTransition<Character>(null, state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenInputDoesNotMatchExpectedInputOfAnotherType()
    {
        State state = new TestState("S0", true);
        StateTransition<Character> transition = new StateTransition<Character>('a', state);
        StateTransition<Character> otherTransition = new StateTransition<Character>('1', state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    private class TestState extends State<Character>
    {
        protected List<StateTransition<Character>> transitions;

        public TestState(String name, boolean accepting)
        {
            super(name, accepting);

            this.transitions = new ArrayList<StateTransition<Character>>();
        }

        public void addTransition(StateTransition<Character> transition)
        {
            this.transitions.add(transition);
        }

        public int countTransitions()
        {
            return this.transitions.size();
        }

        public List<StateTransition<Character>> getTransitions()
        {
            return this.transitions;
        }
    }
}
