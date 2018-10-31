import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;

public class TerminalTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClassAndValue()
    {
        TerminalToken token = new TerminalToken("a");

        assertEquals(new TerminalToken("a"), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentValue()
    {
        TerminalToken token = new TerminalToken("a");

        assertNotEquals(new TerminalToken("b"), token);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        TerminalToken token = new TerminalToken("a");

        assertNotEquals(new SeparatorToken(), token);
    }
}
