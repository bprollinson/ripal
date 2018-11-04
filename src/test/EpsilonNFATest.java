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

import larp.parser.regularlanguage.EpsilonNFA;
import larp.parser.regularlanguage.EpsilonNFAState;

public class EpsilonNFATest
{
    @Test
    public void testGetStartStateReturnsEpsilonNFAState()
    {
        EpsilonNFAState expectedStartState = new EpsilonNFAState("S0", true);
        EpsilonNFA nfa = new EpsilonNFA(expectedStartState);

        EpsilonNFAState startState = nfa.getStartState();
        assertEquals(expectedStartState, startState);
    }

    @Test
    public void testStructureEqualsReturnsTrue()
    {
        EpsilonNFA nfa = new EpsilonNFA(new EpsilonNFAState("S0", true));

        assertTrue(nfa.structureEquals(new EpsilonNFA(new EpsilonNFAState("S1", true))));
    }

    @Test
    public void testStructureEqualsReturnsFalse()
    {
        EpsilonNFA nfa = new EpsilonNFA(new EpsilonNFAState("S0", true));

        assertFalse(nfa.structureEquals(new EpsilonNFA(new EpsilonNFAState("S1", false))));
    }
}
