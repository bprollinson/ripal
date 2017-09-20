import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.EpsilonNFA;
import larp.automaton.EpsilonNFAState;
import larp.automaton.EpsilonNFAToNFAConverter;
import larp.automaton.StateTransition;

public class EpsilonNFAToNFAConverterTest
{
    @Test
    public void testNonEpsilonNFARemainsUnchanged()
    {
        EpsilonNFAToNFAConverter converter = new EpsilonNFAToNFAConverter();

        EpsilonNFAState state1 = new EpsilonNFAState("S0", false);
        EpsilonNFAState state2 = new EpsilonNFAState("S1", true);
        state1.addTransition(new StateTransition('a', state2));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state1);

        assertEquals(expectedEpsilonNFA, converter.convert(expectedEpsilonNFA));
    }

    @Test
    public void testStartStateSingleEpsilonTransitionEliminated()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testNonStartStateSingleEpsilonTransitionEliminated()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testStartStateFinalStatusObtainedFromStateAfterEpsilonTransition()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testNonStartStateFinalStatusObtainedFromStateAfterEpsilonTransition()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testSingleTransitionTransferredFromAfterEpsilonTransition()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testMultipleEpsilonTransitionsNavigated()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCycleNavigated()
    {
        assertEquals(0, 1);
    }
}
