/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.assertion.Assertion;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

public class LL1ParseTableCellAvailableAssertion implements Assertion
{
    private LL1ParseTable parseTable;
    private NonTerminalNode nonTerminalNode;
    private ContextFreeGrammarParseTreeNode terminalNode;

    public LL1ParseTableCellAvailableAssertion(LL1ParseTable parseTable, NonTerminalNode nonTerminalNode, ContextFreeGrammarParseTreeNode terminalNode)
    {
        this.parseTable = parseTable;
        this.nonTerminalNode = nonTerminalNode;
        this.terminalNode = terminalNode;
    }

    public void validate() throws AmbiguousLL1ParseTableException
    {
        if (this.parseTable.getCell(this.nonTerminalNode, this.terminalNode) != null)
        {
            throw new LL1ApplyApplyConflictException();
        }
    }
}
