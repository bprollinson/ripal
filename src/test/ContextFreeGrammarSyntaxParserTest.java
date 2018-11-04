/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxparser.contextfreelanguage.ContextFreeGrammarSyntaxParser;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarSyntaxParserTest
{
    @Test
    public void testParseReturnsTreeForEmptyRightHandSize()
    {
        ContextFreeGrammarSyntaxParser parser = new ContextFreeGrammarSyntaxParser();

        List<ContextFreeGrammarSyntaxToken> input = new ArrayList<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new EpsilonToken());
        ContextFreeGrammarSyntaxNode rootNode = parser.parse(input);

        ProductionNode expectedRootNode = new ProductionNode();
        expectedRootNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }

    @Test
    public void testParseReturnsTreeForSingleTerminalRightHandSize()
    {
        ContextFreeGrammarSyntaxParser parser = new ContextFreeGrammarSyntaxParser();

        List<ContextFreeGrammarSyntaxToken> input = new ArrayList<ContextFreeGrammarSyntaxToken>();
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

        List<ContextFreeGrammarSyntaxToken> input = new ArrayList<ContextFreeGrammarSyntaxToken>();
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

        List<ContextFreeGrammarSyntaxToken> input = new ArrayList<ContextFreeGrammarSyntaxToken>();
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
