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

public class FiniteAutomataTest
{
    @Test
    public void testStructureEqualsReturnsTrue()
    {
        TestFiniteAutomata automata = new TestFiniteAutomata(new TestState("S0", true));

        assertTrue(automata.structureEquals(new TestFiniteAutomata(new TestState("S1", true))));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenStatesNotEqual()
    {
        TestFiniteAutomata automata = new TestFiniteAutomata(new TestState("S0", true));

        assertFalse(automata.structureEquals(new TestFiniteAutomata(new TestState("S1", false))));
    }

    @Test
    public void testStructureEqualsReturnsFalseForAutomataWithDifferentClass()
    {
        TestFiniteAutomata automata = new TestFiniteAutomata(new TestState("S0", true));

        assertFalse(automata.structureEquals(new DFA(new DFAState("S1", true))));
    }

    private class TestState extends State<Character, TestState>
    {
        public TestState(String name, boolean accepting)
        {
            super(name, accepting);
        }
    }

    public class TestFiniteAutomata extends FiniteAutomata<TestState>
    {
        public TestFiniteAutomata(TestState startState)
        {
            super(startState);
        }
    }
}
