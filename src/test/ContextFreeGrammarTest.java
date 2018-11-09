/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class ContextFreeGrammarTest
{
    @Test
    public void testGetStartSymbolReturnsStartSymbol()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
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
    public void testGetStartSymbolReturnsNullForEmptyCFG()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        assertNull(grammar.getStartSymbol());
    }

    @Test
    public void testEqualsReturnsTrueForTwoEmptyCFGs()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();

        ContextFreeGrammar expectedGrammar = new ContextFreeGrammar();

        assertEquals(expectedGrammar, grammar);
    }

    @Test
    public void testEqualsReturnsTrueForTwoCFGsWithTheSameProductions()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("a"));
        grammar.addProduction(node);

        ContextFreeGrammar expectedGrammar = new ContextFreeGrammar();
        ProductionNode expectedNode = new ProductionNode();
        expectedNode.addChild(new NonTerminalNode("S"));
        expectedNode.addChild(new TerminalNode("a"));
        expectedGrammar.addProduction(expectedNode);

        assertEquals(expectedGrammar, grammar);
    }

    @Test
    public void testEqualsReturnsFalseForTwoCFGSWithDifferentProductions()
    {
        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("a"));
        grammar.addProduction(node);
        node = new ProductionNode();
        node.addChild(new NonTerminalNode("S"));
        node.addChild(new TerminalNode("b"));
        grammar.addProduction(node);

        ContextFreeGrammar expectedGrammar = new ContextFreeGrammar();
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
        throw new RuntimeException();
    }
}
