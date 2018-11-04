/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.token.regularlanguage;

public class CharacterToken extends RegularExpressionSyntaxToken
{
    private char character;

    public CharacterToken(char character)
    {
        this.character = character;
    }

    public char getCharacter()
    {
        return this.character;
    }

    public boolean equals(Object other)
    {
        if (!super.equals(other))
        {
            return false;
        }

        return this.character == ((CharacterToken)other).getCharacter();
    }
}
