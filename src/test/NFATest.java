import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.parser.regularlanguage.NFA;
import larp.parser.regularlanguage.NFAState;

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
