/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;

public class DotNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        DotNode node = new DotNode();

        assertEquals(new DotNode(), node);
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        DotNode node = new DotNode();

        assertNotEquals(new ConcatenationNode(), node);
    }
}
