import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;

public class LR0ProductionSetDFATest
{
    @Test
    public void testStructureEqualsReturnsTrue()
    {
        LR0ProductionSetDFA nfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true));

        assertTrue(nfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", true))));
    }

    @Test
    public void testStructureEqualsReturnsFalse()
    {
        LR0ProductionSetDFA nfa = new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S0", true));

        assertFalse(nfa.structureEquals(new LR0ProductionSetDFA(new LR0ProductionSetDFAState("S1", false))));
    }
}
