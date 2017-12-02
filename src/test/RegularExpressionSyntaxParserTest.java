import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.regularlanguage.CharacterNode;
import larp.grammar.regularlanguage.CharacterToken;
import larp.grammar.regularlanguage.CloseBraceToken;
import larp.grammar.regularlanguage.ConcatenationNode;
import larp.grammar.regularlanguage.KleeneClosureNode;
import larp.grammar.regularlanguage.KleeneClosureToken;
import larp.grammar.regularlanguage.OpenBraceToken;
import larp.grammar.regularlanguage.OrNode;
import larp.grammar.regularlanguage.OrToken;
import larp.grammar.regularlanguage.RegularExpressionSyntaxNode;
import larp.grammar.regularlanguage.RegularExpressionSyntaxParser;
import larp.grammar.regularlanguage.RegularExpressionSyntaxToken;

import java.util.ArrayList;
import java.util.List;

public class RegularExpressionSyntaxParserTest
{
    @Test
    public void testParseReturnsEmptyConcatenationNodeForEmptyString()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseReturnsConcatenationNodeForSingleCharacter()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
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

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new CharacterToken('a'));
        input.add(new CharacterToken('b'));
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        expectedRootNode.addChild(new CharacterNode('a'));
        expectedRootNode.addChild(new CharacterNode('b'));

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseAddsKleeneClosureNode()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new CharacterToken('a'));
        input.add(new CharacterToken('b'));
        input.add(new KleeneClosureToken());
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        expectedRootNode.addChild(new CharacterNode('a'));
        KleeneClosureNode kleeneClosureNode = new KleeneClosureNode();
        kleeneClosureNode.addChild(new CharacterNode('b'));
        expectedRootNode.addChild(kleeneClosureNode);

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseHandlesBraces()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new CharacterToken('a'));
        input.add(new OpenBraceToken());
        input.add(new CharacterToken('b'));
        input.add(new CloseBraceToken());
        input.add(new CharacterToken('c'));
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        expectedRootNode.addChild(new CharacterNode('a'));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new CharacterNode('b'));
        expectedRootNode.addChild(concatenationNode);
        expectedRootNode.addChild(new CharacterNode('c'));

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseHandlesOr()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new CharacterToken('a'));
        input.add(new OrToken());
        input.add(new CharacterToken('b'));
        input.add(new OrToken());
        input.add(new CharacterToken('c'));
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        OrNode orNode = new OrNode();
        ConcatenationNode child1 = new ConcatenationNode();
        child1.addChild(new CharacterNode('a'));
        orNode.addChild(child1);
        ConcatenationNode child2 = new ConcatenationNode();
        child2.addChild(new CharacterNode('b'));
        orNode.addChild(child2);
        ConcatenationNode child3 = new ConcatenationNode();
        child3.addChild(new CharacterNode('c'));
        orNode.addChild(child3);
        expectedRootNode.addChild(orNode);

        assertEquals(expectedRootNode, rootNode);
    }
}
