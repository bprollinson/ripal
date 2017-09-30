import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.NonTerminalToken;
import larp.grammar.contextfreelanguage.SeparatorToken;

public class NonTerminalTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClassAndName()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertTrue(token.equals(new NonTerminalToken("S")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentName()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertFalse(token.equals(new NonTerminalToken("T")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        NonTerminalToken token = new NonTerminalToken("S");

        assertFalse(token.equals(new SeparatorToken()));
    }
}
