import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;

public class TerminalTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClassAndValue()
    {
        TerminalToken token = new TerminalToken("a");

        assertTrue(token.equals(new TerminalToken("a")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentValue()
    {
        TerminalToken token = new TerminalToken("a");

        assertFalse(token.equals(new TerminalToken("b")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        TerminalToken token = new TerminalToken("a");

        assertFalse(token.equals(new SeparatorToken()));
    }
}
