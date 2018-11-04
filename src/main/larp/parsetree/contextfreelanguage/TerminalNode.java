/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsetree.contextfreelanguage;

public class TerminalNode extends ContextFreeGrammarSyntaxNode
{
    private String value;

    public TerminalNode(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public boolean equals(Object other)
    {
        if (!super.equals(other))
        {
            return false;
        }

        return this.value.equals(((TerminalNode)other).getValue());
    }

    public int hashCode()
    {
        return super.hashCode() + this.value.hashCode();
    }
}
