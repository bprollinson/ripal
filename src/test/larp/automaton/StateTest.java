/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.automaton;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class StateTest
{
    @Test
    public void testStructureEqualsReturnsTrueForSameAcceptsValueOnSingleState()
    {
        TestState state = new TestState("S0", true);

        assertTrue(state.structureEquals(new TestState("S1", true)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentAcceptsValueOnSingleState()
    {
        TestState state = new TestState("S0", true);

        assertFalse(state.structureEquals(new TestState("S1", false)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentNumberOfTransitions()
    {
        TestState state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character, TestState>('a', state));

        assertFalse(state.structureEquals(new TestState("S1", true)));
    }

    @Test
    public void testStructureEqualsReturnsFalseForDifferentTransitionCharacters()
    {
        TestState state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character, TestState>('a', state));
        TestState otherState = new TestState("S1", true);
        otherState.addTransition(new StateTransition<Character, TestState>('b', otherState));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameTransitionCharacters()
    {
        TestState state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character, TestState>('a', new TestState("S1", true)));
        TestState otherState = new TestState("S2", true);
        otherState.addTransition(new StateTransition<Character, TestState>('a', new TestState("S3", true)));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForSameTransitionCharactersInDifferentOrder()
    {
        TestState state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character, TestState>('a', new TestState("S1", true)));
        state.addTransition(new StateTransition<Character, TestState>('b', new TestState("S2", true)));
        TestState otherState = new TestState("S3", true);
        otherState.addTransition(new StateTransition<Character, TestState>('b', new TestState("S4", true)));
        otherState.addTransition(new StateTransition<Character, TestState>('a', new TestState("S5", true)));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForSubsequentStateInequality()
    {
        TestState state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character, TestState>('a', new TestState("S1", true)));
        TestState otherState = new TestState("S2", true);
        otherState.addTransition(new StateTransition<Character, TestState>('a', new TestState("S3", false)));

        assertFalse(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsTrueForStateGraphContainingCycle()
    {
        TestState state = new TestState("S0", true);
        state.addTransition(new StateTransition<Character, TestState>('a', state));
        TestState otherState = new TestState("S0", true);
        otherState.addTransition(new StateTransition<Character, TestState>('a', otherState));

        assertTrue(state.structureEquals(otherState));
    }

    @Test
    public void testStructureEqualsReturnsFalseForStateGraphContainingDifferentCycle()
    {
        TestState state1 = new TestState("S0", true);
        TestState state2 = new TestState("S1", true);
        TestState state3 = new TestState("S1", true);
        state1.addTransition(new StateTransition<Character, TestState>('a', state2));
        state2.addTransition(new StateTransition<Character, TestState>('a', state3));
        state3.addTransition(new StateTransition<Character, TestState>('a', state1));

        TestState otherState1 = new TestState("S0", true);
        TestState otherState2 = new TestState("S1", true);
        TestState otherState3 = new TestState("S1", true);
        otherState1.addTransition(new StateTransition<Character, TestState>('a', otherState2));
        otherState2.addTransition(new StateTransition<Character, TestState>('a', otherState3));
        otherState3.addTransition(new StateTransition<Character, TestState>('a', otherState2));

        assertFalse(state1.structureEquals(otherState1));
    }

    @Test
    public void testStructureEqualsReturnsFalseForUnmatchedCycleFoundInParallelRecursion()
    {
        TestState state1 = new TestState("S0", false);
        TestState state2 = new TestState("S1", false);
        TestState state3 = new TestState("S2", false);
        TestState state4 = new TestState("S3", false);
        state1.addTransition(new StateTransition<Character, TestState>('a', state2));
        state1.addTransition(new StateTransition<Character, TestState>('b', state3));
        state2.addTransition(new StateTransition<Character, TestState>('a', state4));
        state3.addTransition(new StateTransition<Character, TestState>('a', state4));

        TestState otherState1 = new TestState("S0", false);
        TestState otherState2 = new TestState("S1", false);
        TestState otherState3 = new TestState("S2", false);
        TestState otherState4 = new TestState("S4", false);
        TestState otherState5 = new TestState("S5", false);
        otherState1.addTransition(new StateTransition<Character, TestState>('a', otherState2));
        otherState1.addTransition(new StateTransition<Character, TestState>('b', otherState3));
        otherState2.addTransition(new StateTransition<Character, TestState>('a', otherState4));
        otherState3.addTransition(new StateTransition<Character, TestState>('a', otherState5));

        assertFalse(state1.structureEquals(otherState1));
    }

    @Test
    public void testStructureEqualsReturnsFalseForStateWithDifferentClass()
    {
        TestState state = new TestState("S0", true);
        OtherTestState otherState = new OtherTestState("S1", true);

        assertFalse(state.structureEquals(otherState));
    }

    private class TestState extends State<Character, TestState>
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }

    private class OtherTestState extends State<Character, OtherTestState>
    {
        public OtherTestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }
}
