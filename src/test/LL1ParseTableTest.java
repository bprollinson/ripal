import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.parsetable.LL1ParseTable;

public class LL1ParseTableTest
{
    @Test
    public void testAcceptsReturnsTrueForCorrectCharacterInSingleCharacterCFG()
    {
        LL1ParseTable parseTable = new LL1ParseTable();

        assertEquals(0, 1);
    }

    @Test
    public void testAcceptsReturnsTrueForIncorrectCharacterInSingleCharacterCFG()
    {
        assertEquals(0, 1);
    }
}
