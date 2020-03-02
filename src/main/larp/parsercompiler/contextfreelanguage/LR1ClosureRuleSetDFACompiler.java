/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parsercompiler.contextfreelanguage;

import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.Node;

import java.util.HashSet;
import java.util.Set;

class LR1ClosureRuleSetDFACompiler extends LR0ClosureRuleSetDFACompiler
{
    protected Set<GrammarClosureRule> buildStartStateClosureRuleSet(Node firstProductionWithDot)
    {
        Set<Node> lookaheadSymbols = new HashSet<Node>();
        lookaheadSymbols.add(new EndOfStringNode());
        Set<GrammarClosureRule> closureRuleSet = new HashSet<GrammarClosureRule>();
        closureRuleSet.add(new GrammarClosureRule(firstProductionWithDot, lookaheadSymbols));

        return closureRuleSet;
    }
}
