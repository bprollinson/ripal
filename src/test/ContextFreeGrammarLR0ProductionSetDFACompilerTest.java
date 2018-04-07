import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ContextFreeGrammarLR0ProductionSetDFACompilerTest
{
    @Test
    public void testCompileCreatesDFAForTerminalRuleFromStartState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreatesDFAForNonTerminalRuleFromStartState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreateDFAForTerminalAndNonTerminalRuleFromStartState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreateDFAForTerminalRuleFromSubsequentState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreateDFAForNonTerminalRuleFromSubsequentState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreateDFAForTerminalAndNonTerminalRuleFromSubsequentState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileAddsDeepClosureToStartState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileAddsDeepClosureToSubsequentState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileLoopsTransitionToExistingState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreatesStateChain()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileAddsMultipleProductionsToNextStateBasedOnSameSymbol()
    {
        assertEquals(0, 1);
    }
}
