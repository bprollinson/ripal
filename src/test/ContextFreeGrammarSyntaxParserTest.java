import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxParser;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.grammar.contextfreelanguage.EpsilonNode;
import larp.grammar.contextfreelanguage.EpsilonToken;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.NonTerminalToken;
import larp.grammar.contextfreelanguage.ProductionNode;
import larp.grammar.contextfreelanguage.SeparatorToken;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.grammar.contextfreelanguage.TerminalToken;

import java.util.Vector;

public class ContextFreeGrammarSyntaxParserTest
{
    @Test
    public void testParseReturnsTreeForEmptyRightHandSize()
    {
        ContextFreeGrammarSyntaxParser parser = new ContextFreeGrammarSyntaxParser();

        Vector<ContextFreeGrammarSyntaxToken> input = new Vector<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new EpsilonToken());
        ContextFreeGrammarSyntaxNode rootNode = parser.parse(input);

        ProductionNode expectedRootNode = new ProductionNode();
        expectedRootNode.addChild(new NonTerminalNode("S"));
        expectedRootNode.addChild(new ConcatenationNode());
        expectedRootNode.addChild(new EpsilonNode());

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseReturnsTreeForSingleTerminalRightHandSize()
    {
        ContextFreeGrammarSyntaxParser parser = new ContextFreeGrammarSyntaxParser();

        Vector<ContextFreeGrammarSyntaxToken> input = new Vector<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new TerminalToken("a"));
        ContextFreeGrammarSyntaxNode rootNode = parser.parse(input);

        ProductionNode expectedRootNode = new ProductionNode();
        expectedRootNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseReturnsTreeForSingleNonTerminalRightHandSize()
    {
        ContextFreeGrammarSyntaxParser parser = new ContextFreeGrammarSyntaxParser();

        Vector<ContextFreeGrammarSyntaxToken> input = new Vector<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new NonTerminalToken("S"));
        ContextFreeGrammarSyntaxNode rootNode = parser.parse(input);

        ProductionNode expectedRootNode = new ProductionNode();
        expectedRootNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("S"));
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseReturnsTreeForMultipleTokenRightHandSize()
    {
        ContextFreeGrammarSyntaxParser parser = new ContextFreeGrammarSyntaxParser();

        Vector<ContextFreeGrammarSyntaxToken> input = new Vector<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new NonTerminalToken("S"));
        input.add(new TerminalToken("a"));
        ContextFreeGrammarSyntaxNode rootNode = parser.parse(input);

        ProductionNode expectedRootNode = new ProductionNode();
        expectedRootNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("S"));
        concatenationNode.addChild(new TerminalNode("a"));
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }
}
