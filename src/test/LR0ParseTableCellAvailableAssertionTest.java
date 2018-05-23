import org.junit.Test;
import static org.junit.Assert.assertEquals;

import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;

public class LR0ParseTableCellAvailableAssertionTest
{
    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForShiftActionWhenCellContainsExistingShiftAction()
    {
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenCellContainsTheSameStateAndDifferentSymbol()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenCellContainsDifferentStateAndTheSameSymbol()
    {
        assertEquals(0, 1);
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForReduceActionWhenTableContainsReduceActionWithTheSameState()
    {
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForShiftActionWhenTableContainsReduceActionWithSameState()
    {
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForReduceActionWhenTableContainsShiftActionWithSameState()
    {
    }
}
