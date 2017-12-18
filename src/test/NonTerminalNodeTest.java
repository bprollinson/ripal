import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

public class NonTerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNonTerminalNodeWithSameName()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertTrue(node.equals(new NonTerminalNode("S")));
    }

    @Test
    public void testEqualsReturnsFalseForNonTerminalNodeWithDifferentCharacter()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertFalse(node.equals(new NonTerminalNode("T")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
