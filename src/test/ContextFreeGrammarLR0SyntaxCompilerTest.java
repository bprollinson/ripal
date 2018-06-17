import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ContextFreeGrammarLR0SyntaxCompilerTest
{
    @Test
    public void testCompileReturnsEmptyParseTableForEmptyCFG()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileReturnsParseTableForSingleCharacterProductionCFG()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalProductionCFG()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalAndSingleTerminalProductionCFG()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileHandlesMultipleNonTrivialProductionsWithinTheSameState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileReturnsParseTableWithNonTerminalChain()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileReturnsParseTableWithTerminalChain()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileReturnsTableForCFGWithDFAContainingCycle()
    {
        assertEquals(0, 1);
    }
}
