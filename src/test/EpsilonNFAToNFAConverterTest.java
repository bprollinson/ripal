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
    public void testSingleEpsilonTransitionEliminated()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testFinalStatusObtainedFromStateAfterEpsilonTransition()
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
}
