import org.junit.Test;

import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;

public class LR0ParseTableCellAvailableAssertionTest
{
    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForShiftActionWhenCellContainsExistingShiftAction()
    {
    }
}
