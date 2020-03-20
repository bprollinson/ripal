/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import ripal.parsetree.contextfreelanguage.ConcatenationNode;
import ripal.parsetree.contextfreelanguage.DotNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.ProductionNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class ProductionNodeDotRepositoryTest
{
    @Test
    public void testFindProductionSymbolAfterDotReturnsProductionSymbolWhenDotPresent()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new DotNode());
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);

        TerminalNode expectedNode = new TerminalNode("b");

        assertEquals(expectedNode, repository.findProductionSymbolAfterDot(productionNode));
    }

    @Test
    public void testFindProductionSymbolAfterDotReturnsNullWhenDotNotPresent()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);

        assertNull(repository.findProductionSymbolAfterDot(productionNode));
    }

    @Test
    public void testFindProductionSymbolAfterDotReturnsNullWhenDotAtEnd()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new TerminalNode("b"));
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        assertNull(repository.findProductionSymbolAfterDot(productionNode));
    }

    @Test
    public void testFindProductionSymbolsAfterDotReturnsProductionSymbolsWhenDotPresent()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new DotNode());
        concatenationNode.addChild(new TerminalNode("b"));
        concatenationNode.addChild(new TerminalNode("c"));
        productionNode.addChild(concatenationNode);

        List<Node> expectedNodes = new ArrayList<Node>();
        expectedNodes.add(new TerminalNode("b"));
        expectedNodes.add(new TerminalNode("c"));

        assertEquals(expectedNodes, repository.findProductionSymbolsAfterDot(productionNode));
    }

    @Test
    public void testFindProductionSymbolsAfterDotReturnsNullWhenDotNotPresent()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);

        assertNull(repository.findProductionSymbolsAfterDot(productionNode));
    }

    public void testFindProductionSymbolsAfterDotReturnsEmptyListWhenDotAtEnd()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new TerminalNode("b"));
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        assertEquals(new ArrayList<Node>(), repository.findProductionSymbolsAfterDot(productionNode));
    }

    @Test
    public void testAddDotToProductionAddsDot()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);

        ProductionNode expectedProductionNode = new ProductionNode();
        expectedProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode expectedConcatenationNode = new ConcatenationNode();
        expectedConcatenationNode.addChild(new DotNode());
        expectedConcatenationNode.addChild(new TerminalNode("a"));
        expectedConcatenationNode.addChild(new TerminalNode("b"));
        expectedProductionNode.addChild(expectedConcatenationNode);

        assertEquals(expectedProductionNode, repository.addDotToProduction(productionNode));
    }

    @Test
    public void testShiftDotInProductionShiftsDot()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new DotNode());
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);

        ProductionNode expectedProductionNode = new ProductionNode();
        expectedProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode expectedConcatenationNode = new ConcatenationNode();
        expectedConcatenationNode.addChild(new TerminalNode("a"));
        expectedConcatenationNode.addChild(new TerminalNode("b"));
        expectedConcatenationNode.addChild(new DotNode());
        expectedProductionNode.addChild(expectedConcatenationNode);

        assertEquals(expectedProductionNode, repository.shiftDotInProduction(productionNode));
    }

    @Test
    public void testShiftDotInProductionRemovesTrailingDot()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new TerminalNode("b"));
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        ProductionNode expectedProductionNode = new ProductionNode();
        expectedProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode expectedConcatenationNode = new ConcatenationNode();
        expectedConcatenationNode.addChild(new TerminalNode("a"));
        expectedConcatenationNode.addChild(new TerminalNode("b"));
        expectedProductionNode.addChild(expectedConcatenationNode);

        assertEquals(expectedProductionNode, repository.shiftDotInProduction(productionNode));
    }

    @Test
    public void testShiftDotInProductionDoesNothingWhenDotNotFound()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);

        ProductionNode expectedProductionNode = new ProductionNode();
        expectedProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode expectedConcatenationNode = new ConcatenationNode();
        expectedConcatenationNode.addChild(new TerminalNode("a"));
        expectedConcatenationNode.addChild(new TerminalNode("b"));
        expectedProductionNode.addChild(expectedConcatenationNode);

        assertEquals(expectedProductionNode, repository.shiftDotInProduction(productionNode));
    }

    @Test
    public void testRemoveDotFromProductionRemovesSingleDot()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        ProductionNode expectedProductionNode = new ProductionNode();
        expectedProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode expectedConcatenationNode = new ConcatenationNode();
        expectedConcatenationNode.addChild(new TerminalNode("a"));
        expectedProductionNode.addChild(expectedConcatenationNode);

        assertEquals(expectedProductionNode, repository.removeDotFromProduction(productionNode));
    }

    @Test
    public void testRemoveDotFromProductionRemovesMultipleDots()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        concatenationNode.addChild(new DotNode());
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        ProductionNode expectedProductionNode = new ProductionNode();
        expectedProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode expectedConcatenationNode = new ConcatenationNode();
        expectedConcatenationNode.addChild(new TerminalNode("a"));
        expectedProductionNode.addChild(expectedConcatenationNode);

        assertEquals(expectedProductionNode, repository.removeDotFromProduction(productionNode));
    }

    @Test
    public void testRemoveDotFromProductionDoesNothingWhenDotNotFound()
    {
        ProductionNodeDotRepository repository = new ProductionNodeDotRepository();

        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);

        ProductionNode expectedProductionNode = new ProductionNode();
        expectedProductionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode expectedConcatenationNode = new ConcatenationNode();
        expectedConcatenationNode.addChild(new TerminalNode("a"));
        expectedProductionNode.addChild(expectedConcatenationNode);

        assertEquals(expectedProductionNode, repository.removeDotFromProduction(productionNode));
    }
}
