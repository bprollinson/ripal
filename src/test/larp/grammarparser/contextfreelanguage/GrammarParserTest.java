/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammarparser.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.Node;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;
import larp.token.contextfreelanguage.Token;

import java.util.ArrayList;
import java.util.List;

public class GrammarParserTest
{
    @Test
    public void testParseReturnsTreeForEmptyRightHandSize()
    {
        GrammarParser parser = new GrammarParser();

        List<Token> input = new ArrayList<Token>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new EpsilonToken());
        Node rootNode = parser.parse(input);

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
        GrammarParser parser = new GrammarParser();

        List<Token> input = new ArrayList<Token>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new TerminalToken("a"));
        Node rootNode = parser.parse(input);

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
        GrammarParser parser = new GrammarParser();

        List<Token> input = new ArrayList<Token>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new NonTerminalToken("S"));
        Node rootNode = parser.parse(input);

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
        GrammarParser parser = new GrammarParser();

        List<Token> input = new ArrayList<Token>();
        input.add(new NonTerminalToken("S"));
        input.add(new SeparatorToken());
        input.add(new NonTerminalToken("S"));
        input.add(new TerminalToken("a"));
        Node rootNode = parser.parse(input);

        ProductionNode expectedRootNode = new ProductionNode();
        expectedRootNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("S"));
        concatenationNode.addChild(new TerminalNode("a"));
        expectedRootNode.addChild(concatenationNode);

        assertEquals(expectedRootNode, rootNode);
    }
}
