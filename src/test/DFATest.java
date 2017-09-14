import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.automaton.State;
import larp.automaton.DFA;
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
        State state0 = new State("S0", false);
        State state1 = new State("S1", true);
        state0.addTransition(new StateTransition('a', state1));

        return new DFA(state0);
    }
}
