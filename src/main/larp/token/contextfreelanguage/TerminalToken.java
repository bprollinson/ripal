/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.token.contextfreelanguage;

public class TerminalToken extends Token
{
    private String value;

    public TerminalToken(String value)
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

        return this.value.equals(((TerminalToken)other).getValue());
    }
}
