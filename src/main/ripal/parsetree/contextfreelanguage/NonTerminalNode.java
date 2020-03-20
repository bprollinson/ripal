/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsetree.contextfreelanguage;

public class NonTerminalNode extends Node
{
    private String name;

    public NonTerminalNode(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean equals(Object other)
    {
        if (!super.equals(other))
        {
            return false;
        }

        return this.name.equals(((NonTerminalNode)other).getName());
    }

    public int hashCode()
    {
        return super.hashCode() + this.name.hashCode();
    }
}
