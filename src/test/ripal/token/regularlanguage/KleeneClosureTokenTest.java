/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.token.regularlanguage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class KleeneClosureTokenTest
{
    @Test
    public void testEqualsReturnsTrueForNodeWithSameClass()
    {
        KleeneClosureToken token = new KleeneClosureToken();

        assertTrue(token.equals(new KleeneClosureToken()));
    }

    @Test
    public void testEqualsReturnsFalseForNodeWithDifferentClass()
    {
        KleeneClosureToken token = new KleeneClosureToken();

        assertFalse(token.equals(new CharacterToken('a')));
    }
}
