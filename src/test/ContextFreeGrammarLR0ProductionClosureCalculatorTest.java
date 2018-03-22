import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ContextFreeGrammarLR0ProductionClosureCalculatorTest
{
    @Test
    public void testCalculateClosureDoesNotAddItemsWhenDotNotPresent()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateClosureDoesNotAddItemsWhenDotIsNotBeforeNonTerminal()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateClosureDoesNotAddItemsWhenDotAtEndOfProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateClosureAddsSingleProductionWhenDotIsBeforeNonTerminalWithSingleProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateClosureAddsMultipleProductionsWhenDotIsBeforeNonTerminalWithMultipleProductions()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateClosureRemovesDuplicateProduction()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCalculateClosureAddsSingleProductionWhenSetContainsOneRelevantAndOneIrrelevantProduction()
    {
        assertEquals(0, 1);
    }
}
