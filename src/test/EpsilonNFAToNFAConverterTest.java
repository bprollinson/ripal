import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EpsilonNFAToNFAConverterTest
{
    @Test
    public void testNonEpsilonNFARemainsUnchanged()
    {
        assertEquals(0, 1);
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
