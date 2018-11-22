/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.regularlanguage;

public class CharacterNode extends Node
{
    private char character;

    public CharacterNode(char character)
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

        return this.character == ((CharacterNode)other).getCharacter();
    }
}
