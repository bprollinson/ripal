import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

public class NonTerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNonTerminalNodeWithSameName()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertEquals(new NonTerminalNode("S"), node);
    }

    @Test
    public void testEqualsReturnsFalseForNonTerminalNodeWithDifferentName()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertNotEquals(new NonTerminalNode("T"), node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertNotEquals(new ConcatenationNode(), node);
    }
}
