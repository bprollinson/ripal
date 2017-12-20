import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;

public class SeparatorTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        SeparatorToken token = new SeparatorToken();

        assertTrue(token.equals(new SeparatorToken()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        SeparatorToken token = new SeparatorToken();

        assertFalse(token.equals(new NonTerminalToken("S")));
    }
}
