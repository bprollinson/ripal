import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.DFA;
import larp.automaton.DFAState;
import larp.automaton.StateTransition;

public class DFATest
{
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
        state0.addTransition(new StateTransition('a', state1));

        return new DFA(state0);
    }
}
