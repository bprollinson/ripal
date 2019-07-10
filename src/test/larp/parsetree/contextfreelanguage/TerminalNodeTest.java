/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.contextfreelanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClassAndValue()
    {
        TerminalNode node = new TerminalNode("a");

        assertTrue(node.equals(new TerminalNode("a")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentValue()
    {
        TerminalNode node = new TerminalNode("a");

        assertFalse(node.equals(new TerminalNode("b")));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        TerminalNode node = new TerminalNode("a");

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
