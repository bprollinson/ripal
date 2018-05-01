import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ContextFreeGrammarClosureCalculatorTest
{
    @Test
    public void testCalculateAddsSimpleTerminalProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateAddsSimpleNonTerminalProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenNoneAvailable()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotAddProductionWhenNonReachable()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateAddsTerminalChain()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateAddsClosureFromNonFirstNonTerminalInProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotAddProductionsForNonTerminal()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotAddProductionsForSubsequentNonTerminal()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringImmediateInfiniteLoop()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateCalculatesClosureIgnoringEventualInfiniteLoop()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelTerminals()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateExpandsEventualNonTerminalIntoParallelTerminals()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateExpandsImmediateNonTerminalIntoParallelNonTerminals()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateExpandsEventualNonTerminalIntoParallelNonTerminals()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateAddsTerminalChainInReverseOrder()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateDoesNotExpandProductionSetWhenDotMissing()
    {
        assertEquals(0, 1);
    }
}
