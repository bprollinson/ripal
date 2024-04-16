/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.automaton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DFATest
{
    @Test
    public void testGetStartStateReturnsDFAState()
    {
        DFAState expectedStartState = new DFAState("S0", true);
        DFA dfa = new DFA(expectedStartState);

        DFAState startState = dfa.getStartState();
        assertEquals(expectedStartState, startState);
    }

    @Test
    public void testAcceptsReturnsFalseInNonExceptingState()
    {
        DFA dfa = this.buildDFA();

        assertFalse(dfa.accepts(""));
    }

    @Test
    public void testAcceptsReturnsTrueInExceptingState()
    {
        DFA dfa = this.buildDFA();

        assertTrue(dfa.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForMissingTransition()
    {
        DFA dfa = this.buildDFA();

        assertFalse(dfa.accepts("ab"));
    }

    private DFA buildDFA()
    {
        DFAState state0 = new DFAState("S0", false);
        DFAState state1 = new DFAState("S1", true);
        state0.addTransition(new StateTransition<Character, DFAState>('a', state1));

        return new DFA(state0);
    }
}
