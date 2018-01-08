import org.junit.Test;

import larp.parser.contextfreelanguage.AmbiguousLL1ParseTableException;

public class LL1ParseTableCellAvailableAssertionTest
{
    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testValidateThrowsExceptionForCellThatAlreadyExists() throws AmbiguousLL1ParseTableException
    {
    }

    @Test(expected = RuntimeException.class)
    public void testValidateDoesNotThrowExceptionForCellThatDoesNotAlreadyExist() throws AmbiguousLL1ParseTableException
    {
    }
}
