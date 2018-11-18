/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.contextfreelanguage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class TerminalNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClassAndValue()
    {
        TerminalNode node = new TerminalNode("a");

        assertEquals(new TerminalNode("a"), node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithSameClassAndDifferentValue()
    {
        TerminalNode node = new TerminalNode("a");

        assertNotEquals(new TerminalNode("b"), node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        TerminalNode node = new TerminalNode("a");

        assertNotEquals(new ConcatenationNode(), node);
    }
}
