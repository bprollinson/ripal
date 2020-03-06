/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

public class SLR1Parser extends LR0Parser
{
    public SLR1Parser(LR0ParseTable parseTable)
    {
        super(parseTable);
    }
}
