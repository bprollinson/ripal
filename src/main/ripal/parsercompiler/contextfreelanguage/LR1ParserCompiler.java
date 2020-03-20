/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parsercompiler.contextfreelanguage;

import ripal.parsetree.contextfreelanguage.Node;

import java.util.Set;

public class LR1ParserCompiler extends LR0ParserCompiler
{
    public LR1ParserCompiler()
    {
        this.DFACompiler = new LR1ClosureRuleSetDFACompiler();
    }

    protected boolean shouldReduceForProduction(Node nonTerminalNode, Node terminalNode, Set<Node> lookaheadSymbols)
    {
        return lookaheadSymbols.contains(terminalNode);
    }
}
