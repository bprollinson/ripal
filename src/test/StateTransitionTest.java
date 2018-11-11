/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.State;
import larp.automaton.StateTransition;

public class StateTransitionTest
{
    @Test
    public void testInputEqualsReturnsTrueWhenInputMatchesExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>('a', state);

        assertTrue(transition.inputEquals('a'));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenInputDoesNotMatchExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>('a', state);

        assertFalse(transition.inputEquals('b'));
    }

    @Test
    public void testInputEqualsReturnsTrueWhenEmptyInputMatchesExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>(null, state);

        assertTrue(transition.inputEquals(null));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenNonEmptyInputDoesNotMatchExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>(null, state);

        assertFalse(transition.inputEquals('a'));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenEmptyInputDoesNotMatchExpectedNonEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>('a', state);

        assertFalse(transition.inputEquals(null));
    }

    @Test
    public void testInputEqualsReturnsFalseWhenInputDoesNotMatchExpectedInputOfAnotherType()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>('a', state);

        assertFalse(transition.inputEquals(1));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsTrueWhenInputMatchesExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>('a', state);
        StateTransition<Character, State> otherTransition = new StateTransition<Character, State>('a', state);

        assertTrue(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenInputDoesNotMatchExpectedInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>('a', state);
        StateTransition<Character, State> otherTransition = new StateTransition<Character, State>('b', state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsTrueWhenEmptyInputMatchesExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>(null, state);
        StateTransition<Character, State> otherTransition = new StateTransition<Character, State>(null, state);

        assertTrue(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenNonEmptyInputDoesNotMatchExpectedEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>(null, state);
        StateTransition<Character, State> otherTransition = new StateTransition<Character, State>('a', state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenEmptyInputDoesNotMatchExpectedNonEmptyInput()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>('a', state);
        StateTransition<Character, State> otherTransition = new StateTransition<Character, State>(null, state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testInputEqualsOtherTransitionReturnsFalseWhenInputDoesNotMatchExpectedInputOfAnotherType()
    {
        State state = new TestState("S0", true);
        StateTransition<Character, State> transition = new StateTransition<Character, State>('a', state);
        StateTransition<Character, State> otherTransition = new StateTransition<Character, State>('1', state);

        assertFalse(transition.inputEqualsOtherTransition(otherTransition));
    }

    @Test
    public void testGetNextStateReturnsStateOfExpectedType()
    {
        TestState expectedNextState = new TestState("S0", true);
        StateTransition<Character, TestState> transition = new StateTransition<Character, TestState>('a', expectedNextState);

        TestState nextState = transition.getNextState();
        assertEquals(expectedNextState, nextState);
    }

    private class TestState extends State<Character, TestState>
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
