import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.parsetree.regularlanguage.CharacterNode;
import larp.parsetree.regularlanguage.ConcatenationNode;
import larp.parsetree.regularlanguage.KleeneClosureNode;
import larp.parsetree.regularlanguage.OrNode;
import larp.parsetree.regularlanguage.RegularExpressionSyntaxNode;
import larp.syntaxparser.regularlanguage.RegularExpressionSyntaxParser;
import larp.token.regularlanguage.CharacterToken;
import larp.token.regularlanguage.CloseParenthesisToken;
import larp.token.regularlanguage.EpsilonToken;
import larp.token.regularlanguage.KleeneClosureToken;
import larp.token.regularlanguage.OpenParenthesisToken;
import larp.token.regularlanguage.OrToken;
import larp.token.regularlanguage.RegularExpressionSyntaxToken;

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
    public void testParseReturnsEmptyConcatenationNodeForEpsilonToken()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new EpsilonToken());
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
        input.add(new OpenParenthesisToken());
        input.add(new CharacterToken('b'));
        input.add(new CloseParenthesisToken());
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
    public void testParseHandlesNestedBraces()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new CharacterToken('a'));
        input.add(new OpenParenthesisToken());
        input.add(new OpenParenthesisToken());
        input.add(new CharacterToken('b'));
        input.add(new CloseParenthesisToken());
        input.add(new CloseParenthesisToken());
        input.add(new CharacterToken('c'));
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        expectedRootNode.addChild(new CharacterNode('a'));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        ConcatenationNode subConcatenationNode = new ConcatenationNode();
        subConcatenationNode.addChild(new CharacterNode('b'));
        concatenationNode.addChild(subConcatenationNode);
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

    @Test
    public void testParseHandlesCharacterBelowParentheses()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new OpenParenthesisToken());
        input.add(new CharacterToken('a'));
        input.add(new CloseParenthesisToken());
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new CharacterNode('a'));
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseHandlesKleeneClosureBelowParentheses()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new OpenParenthesisToken());
        input.add(new CharacterToken('a'));
        input.add(new KleeneClosureToken());
        input.add(new CloseParenthesisToken());
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        ConcatenationNode concatenationNode = new ConcatenationNode();
        KleeneClosureNode kleeneClosureNode = new KleeneClosureNode();
        kleeneClosureNode.addChild(new CharacterNode('a'));
        concatenationNode.addChild(kleeneClosureNode);
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseHandlesOrBelowParentheses()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        List<RegularExpressionSyntaxToken> input = new ArrayList<RegularExpressionSyntaxToken>();
        input.add(new OpenParenthesisToken());
        input.add(new CharacterToken('a'));
        input.add(new OrToken());
        input.add(new CharacterToken('b'));
        input.add(new CloseParenthesisToken());
        RegularExpressionSyntaxNode rootNode = parser.parse(input);

        ConcatenationNode expectedRootNode = new ConcatenationNode();
        OrNode orNode = new OrNode();
        ConcatenationNode concatenationNode = new ConcatenationNode();
        ConcatenationNode concatenationNode1 = new ConcatenationNode();
        concatenationNode1.addChild(new CharacterNode('a'));
        orNode.addChild(concatenationNode1);
        ConcatenationNode concatenationNode2 = new ConcatenationNode();
        concatenationNode2.addChild(new CharacterNode('b'));
        orNode.addChild(concatenationNode2);
        concatenationNode.addChild(orNode);
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }
}
