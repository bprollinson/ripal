/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammarparser.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import ripal.parsetree.contextfreelanguage.ConcatenationNode;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.ProductionNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;
import ripal.token.contextfreelanguage.EpsilonToken;
import ripal.token.contextfreelanguage.NonTerminalToken;
import ripal.token.contextfreelanguage.SeparatorToken;
import ripal.token.contextfreelanguage.TerminalToken;
import ripal.token.contextfreelanguage.Token;

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
