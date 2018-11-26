/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammar.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class GrammarTest
{
    @Test
    public void testGetStartSymbolReturnsStartSymbol()
    {
        Grammar grammar = new Grammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        productionNode.addChild(new TerminalNode("a"));
        grammar.addProduction(productionNode);
        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("T"));
        productionNode.addChild(new TerminalNode("b"));
        grammar.addProduction(productionNode);

        assertEquals(new NonTerminalNode("S"), grammar.getStartSymbol());
    }

    @Test
    public void testGetStartSymbolReturnsNullForEmptyGrammar()
    {
        Grammar grammar = new Grammar();

        assertNull(grammar.getStartSymbol());
    }

    @Test
    public void testEqualsReturnsTrueForTwoEmptyGrammars()
    {
        Grammar grammar = new Grammar();

        Grammar expectedGrammar = new Grammar();

        assertEquals(expectedGrammar, grammar);
    }

    @Test
    public void testEqualsReturnsTrueForTwoGrammarssWithTheSameProductions()
    {
        Grammar grammar = new Grammar();
        ProductionNode node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("a"));
        grammar.addProduction(node);

        Grammar expectedGrammar = new Grammar();
        ProductionNode expectedNode = new ProductionNode();
        expectedNode.addChild(new NonTerminalNode("S"));
        expectedNode.addChild(new TerminalNode("a"));
        expectedGrammar.addProduction(expectedNode);

        assertEquals(expectedGrammar, grammar);
    }

    @Test
    public void testEqualsReturnsFalseForTwoGrammarsWithDifferentProductions()
    {
        Grammar grammar = new Grammar();
        ProductionNode node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("a"));
        grammar.addProduction(node);
        node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("b"));
        grammar.addProduction(node);

        Grammar expectedGrammar = new Grammar();
        ProductionNode expectedNode = new ProductionNode();
        expectedNode.addChild(new NonTerminalNode("S"));
        expectedNode.addChild(new TerminalNode("a"));
        expectedGrammar.addProduction(expectedNode);
        expectedNode = new ProductionNode();
        expectedNode.addChild(new NonTerminalNode("S"));
        expectedNode.addChild(new TerminalNode("c"));
        expectedGrammar.addProduction(expectedNode);

        assertNotEquals(expectedGrammar, grammar);
    }

    @Test
    public void testEqualsReturnsFalseForObjectWithDifferentClass()
    {
        Grammar grammar = new Grammar();

        assertNotEquals(new Object(), grammar);
    }
}
