import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class TerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForTerminalNodeWithSameValue()
    {
        TerminalNode node = new TerminalNode("a");

        assertEquals(new TerminalNode("a"), node);
    }

    @Test
    public void testEqualsReturnsFalseForTerminalNodeWithDifferentValue()
    {
        TerminalNode node = new TerminalNode("a");

        assertNotEquals(new TerminalNode("b"), node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        TerminalNode node = new TerminalNode("a");

        assertNotEquals(new ConcatenationNode(), node);
    }
}
