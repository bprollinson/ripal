import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.CharacterNode;
import larp.grammar.CharacterToken;
import larp.grammar.ConcatenationNode;
import larp.grammar.RegularExpressionSyntaxNode;
import larp.grammar.RegularExpressionSyntaxParser;
import larp.grammar.RegularExpressionSyntaxToken;

import java.util.Vector;

public class RegularExpressionSyntaxParserTest
{
    @Test
    public void testParseReturnsConcatenationNodeForSingleCharacter()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        Vector<RegularExpressionSyntaxToken> input = new Vector<RegularExpressionSyntaxToken>();
        input.add(new CharacterToken('a'));
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        expectedRootNode.addChild(new CharacterNode('a'));

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseReturnsConcatenationNodeForMultipleCharacters()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        Vector<RegularExpressionSyntaxToken> input = new Vector<RegularExpressionSyntaxToken>();
        input.add(new CharacterToken('a'));
        input.add(new CharacterToken('b'));
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        expectedRootNode.addChild(new CharacterNode('a'));
        expectedRootNode.addChild(new CharacterNode('b'));

        assertEquals(expectedRootNode, rootNode);
    }
}
