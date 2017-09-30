import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.TerminalToken;
import larp.grammar.contextfreelanguage.SeparatorToken;

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
