import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.parser.regularlanguage.DFAState;
import larp.parser.regularlanguage.StateTransition;

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
    public void testGetNextSTateReturnsNextStateFromSubsequentMatchingTransition()
    {
        DFAState state0 = new DFAState("S0", true);
        DFAState state1 = new DFAState("S1", true);
        DFAState state2 = new DFAState("S2", true);
        state0.addTransition(new StateTransition<Character, DFAState>('a', state1));
        state0.addTransition(new StateTransition<Character, DFAState>('b', state2));

        assertEquals(state2, state0.getNextState('b'));
    }
}
