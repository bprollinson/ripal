import org.junit.Test;

import larp.automaton.NFA;
import larp.automaton.NFAState;
import larp.automaton.StateTransition;

public class NFAToDFAConverterTest
{
    @Test
    public void testDFARemainsUnchanged()
    {
        NFAToDFAConverter converter = new NFAToDFAConverter();

        NFAState expectedState1 = new NFAState("S0", false);
        NFAState expectedState2 = new NFAState("S1", true);
        expectedState1.addTransition(new StateTransition('a', expectedState2));
        NFA expectedNFA = new NFA(expectedState1);

        assertEquals(expectedNFA, converter.convert(expectedNFA));
    }
}
