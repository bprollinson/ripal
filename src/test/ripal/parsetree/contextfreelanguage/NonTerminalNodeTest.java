/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsetree.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class NonTerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClassAndName()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertTrue(node.equals(new NonTerminalNode("S")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentName()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertFalse(node.equals(new NonTerminalNode("T")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        NonTerminalNode node = new NonTerminalNode("S");

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
