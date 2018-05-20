import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LR0ParseTableTest
{
    @Test
    public void testAddCellThrowsExceptionForTwoCellsWithTheSameStateAndSymbol()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testAddCellThrowsExceptionForTwoReduceActionsWithSameState()
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
        assertEquals(0, 1);
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
