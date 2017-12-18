import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class TerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForTerminalNodeWithSameValue()
    {
        TerminalNode node = new TerminalNode("a");

        assertTrue(node.equals(new TerminalNode("a")));
    }

    @Test
    public void testEqualsReturnsFalseForTerminalNodeWithDifferentValue()
    {
        TerminalNode node = new TerminalNode("a");

        assertFalse(node.equals(new TerminalNode("b")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        TerminalNode node = new TerminalNode("a");

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
