import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parsetree.regularlanguage.CharacterNode;
import larp.parsetree.regularlanguage.KleeneClosureNode;

public class KleeneClosureNodeTest
{
    @Test
    public void testEqualsReturnsTrueForKleeneClosureNodeWithNoChildren()
    {
        KleeneClosureNode node = new KleeneClosureNode();

        assertTrue(node.equals(new KleeneClosureNode()));
    }

    @Test
    public void testEqualsReturnsTrueForKleeneClosureNodeWithSameSubtree()
    {
        KleeneClosureNode node = new KleeneClosureNode();
        node.addChild(new CharacterNode('a'));

        KleeneClosureNode otherNode = new KleeneClosureNode();
        otherNode.addChild(new CharacterNode('a'));

        assertTrue(node.equals(otherNode));
    }

    @Test
    public void testEqualsReturnsFalseForKleeneClosureNodeWithDifferentSubtree()
    {
        KleeneClosureNode node = new KleeneClosureNode();
        node.addChild(new CharacterNode('a'));

        KleeneClosureNode otherNode = new KleeneClosureNode();
        otherNode.addChild(new CharacterNode('b'));

        assertFalse(node.equals(otherNode));
    }
}
