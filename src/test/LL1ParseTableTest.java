import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parsetable.LL1ParseTable;

public class LL1ParseTableTest
{
    @Test
    public void testAcceptsReturnsTrueForCorrectCharacterInSingleCharacterCFG()
    {
        LL1ParseTable parseTable = new LL1ParseTable();

        assertTrue(parseTable.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsTrueForIncorrectCharacterInSingleCharacterCFG()
    {
        LL1ParseTable parseTable = new LL1ParseTable();

        assertFalse(parseTable.accepts("b"));
    }
}
