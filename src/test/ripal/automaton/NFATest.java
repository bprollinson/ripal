/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.automaton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class NFATest
{
    @Test
    public void testGetStartStateReturnsNFAState()
    {
        NFAState expectedStartState = new NFAState("S0", true);
        NFA nfa = new NFA(expectedStartState);

        NFAState startState = nfa.getStartState();
        assertEquals(expectedStartState, startState);
    }
}
