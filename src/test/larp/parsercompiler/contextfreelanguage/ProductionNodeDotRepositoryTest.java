/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class ProductionNodeDotRepositoryTest
{
    @Test
    public void testFindProductionSymbolAfterDotFindsProductionSymbolWhenDotPresent()
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
    public void testAddDotToProductionRightHandSideAddsDot()
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

        assertEquals(expectedProductionNode, repository.addDotToProductionRightHandSide(productionNode));
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
}
