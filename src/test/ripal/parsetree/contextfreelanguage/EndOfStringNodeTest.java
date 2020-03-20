/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsetree.contextfreelanguage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EndOfStringNodeTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        EndOfStringNode node = new EndOfStringNode();

        assertTrue(node.equals(new EndOfStringNode()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        EndOfStringNode node = new EndOfStringNode();

        assertFalse(node.equals(new ConcatenationNode()));
    }
}
