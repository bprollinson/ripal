/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.automaton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DFAStateTest
{
    @Test
    public void testGetNextStateReturnsNullWhenInputDoesNotMatchAnyTransitionInput()
    {
        DFAState state = new DFAState("S0", true);

        assertEquals(null, state.getNextState('a'));
    }

    @Test
    public void testGetNextStateReturnsNextStateFromSingleMatchingTransition()
    {
        DFAState state0 = new DFAState("S0", true);
        DFAState state1 = new DFAState("S1", true);
        state0.addTransition(new StateTransition<Character, DFAState>('a', state1));

        assertEquals(state1, state0.getNextState('a'));
    }

    @Test
    public void testGetNextStateReturnsNextStateFromSubsequentMatchingTransition()
    {
        DFAState state0 = new DFAState("S0", true);
        DFAState state1 = new DFAState("S1", true);
        DFAState state2 = new DFAState("S2", true);
        state0.addTransition(new StateTransition<Character, DFAState>('a', state1));
        state0.addTransition(new StateTransition<Character, DFAState>('b', state2));

        assertEquals(state2, state0.getNextState('b'));
    }
}
