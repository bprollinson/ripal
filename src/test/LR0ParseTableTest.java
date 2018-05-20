import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ParseTable;

public class LR0ParseTableTest
{
    @Test
    public void testAddCellThrowsExceptionForTwoShiftActionsWithTheSameStateAndSymbol()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testAddCellThrowsExceptionForTwoReduceActionsWithTheSameState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testAddCellThrowsExceptionForShiftActionConflictingWithExistingReduceActionWithSameState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testAddCellThrowsExceptionForReduceActionConflictingWithExistingShiftActionWithSameState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsTrueForEmptyCFGAndNoTableEntries()
    {
        LR0ParseTable parseTable = new LR0ParseTable(new ContextFreeGrammar());
        LR0ParseTable otherParseTable = new LR0ParseTable(new ContextFreeGrammar());

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyCFGAndTableEntries()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentCFGs()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentShiftTableEntries()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsFalseForDifferentReduceTableEntries()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder()
    {
        assertEquals(0, 1);
    }
}
