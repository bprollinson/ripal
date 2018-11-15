/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammarparser.contextfreelanguage.ContextFreeGrammarParser;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarParserTest
{
    @Test
    public void testParseReturnsTreeForEmptyRightHandSize()
    {
        ContextFreeGrammarParser parser = new ContextFreeGrammarParser();

        List<ContextFreeGrammarSyntaxToken> input = new ArrayList<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new EpsilonToken());
        ContextFreeGrammarParseTreeNode rootNode = parser.parse(input);

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
        ContextFreeGrammarParser parser = new ContextFreeGrammarParser();

        List<ContextFreeGrammarSyntaxToken> input = new ArrayList<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new TerminalToken("a"));
        ContextFreeGrammarParseTreeNode rootNode = parser.parse(input);

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
        ContextFreeGrammarParser parser = new ContextFreeGrammarParser();

        List<ContextFreeGrammarSyntaxToken> input = new ArrayList<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new NonTerminalToken("S"));
        ContextFreeGrammarParseTreeNode rootNode = parser.parse(input);

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
        ContextFreeGrammarParser parser = new ContextFreeGrammarParser();

        List<ContextFreeGrammarSyntaxToken> input = new ArrayList<ContextFreeGrammarSyntaxToken>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new NonTerminalToken("S"));
        input.add(new TerminalToken("a"));
        ContextFreeGrammarParseTreeNode rootNode = parser.parse(input);

        ProductionNode expectedRootNode = new ProductionNode();
        expectedRootNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("S"));
        concatenationNode.addChild(new TerminalNode("a"));
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }
}
