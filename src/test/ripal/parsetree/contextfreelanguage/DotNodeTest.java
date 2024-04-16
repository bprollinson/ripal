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

public class DotNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        DotNode node = new DotNode();

        assertTrue(node.equals(new DotNode()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        DotNode node = new DotNode();

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
