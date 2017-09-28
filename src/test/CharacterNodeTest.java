import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.regularlanguage.CharacterNode;
import larp.grammar.regularlanguage.ConcatenationNode;

public class CharacterNodeTest
{
    @Test
    public void testEqualsReturnsTrueForCharacterNodeWithSameCharacter()
    {
        CharacterNode node = new CharacterNode('a');

        assertTrue(node.equals(new CharacterNode('a')));
    }

    @Test
    public void testEqualsReturnsFalseForCharacterNodeWithDifferenceCharacter()
    {
        CharacterNode node = new CharacterNode('a');

        assertFalse(node.equals(new CharacterNode('b')));
    }

    @Test
    public void testEqualsReturnsFalseForNodeOfOtherType()
    {
        CharacterNode node = new CharacterNode('a');

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
