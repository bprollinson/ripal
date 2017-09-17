import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.EpsilonNFA;
import larp.automaton.EpsilonNFAState;

public class EpsilonNFATest
{
    @Test
    public void testEqualsReturnsTrue()
    {
        EpsilonNFA nfa = new EpsilonNFA(new EpsilonNFAState("S0", true));

        assertTrue(nfa.equals(new EpsilonNFA(new EpsilonNFAState("S1", true))));
    }

    @Test
    public void testEqualsReturnsFalse()
    {
        EpsilonNFA nfa = new EpsilonNFA(new EpsilonNFAState("S0", true));

        assertFalse(nfa.equals(new EpsilonNFA(new EpsilonNFAState("S1", false))));
    }
}
