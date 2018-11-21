/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.token.contextfreelanguage;

public class Token
{
    public boolean equals(Object other)
    {
        return this.getClass().equals(other.getClass());
    }
}
