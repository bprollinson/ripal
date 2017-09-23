import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.DFA;
import larp.automaton.DFAState;
import larp.automaton.NFA;
import larp.automaton.NFAState;
import larp.automaton.NFAToDFAConverter;
import larp.automaton.StateTransition;

public class NFAToDFAConverterTest
{
    @Test
    public void testDFARemainsUnchanged()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        NFAState expectedState1 = new NFAState("S0", false);
        NFAState expectedState2 = new NFAState("S1", false);
        expectedState1.addTransition(new StateTransition('a', expectedState2));
        NFA expectedNFA = new NFA(expectedState1);

        assertEquals(expectedNFA, converter.convert(expectedNFA));
    }

    @Test
    public void testMultipleTransitionsForSameCharacterCombinedFromStartState()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        DFAState expectedState1 = new DFAState("S0", false);
        DFAState expectedState2 = new DFAState("S0", false);
        expectedState1.addTransition(new StateTransition('a', expectedState2));
        DFA expectedDFA = new DFA(expectedState1);

        NFAState state1 = new NFAState("S0", false);
        NFAState state2 = new NFAState("S1", false);
        NFAState state3 = new NFAState("S2", false);
        state1.addTransition(new StateTransition('a', state2));
        state1.addTransition(new StateTransition('a', state3));
        NFA NFA = new NFA(state1);

        assertEquals(expectedDFA, converter.convert(NFA));
    }
}
