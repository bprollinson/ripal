/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.parser.contextfreelanguage;

import ripal.automaton.State;
import ripal.parsercompiler.contextfreelanguage.GrammarClosureRule;
import ripal.parsetree.contextfreelanguage.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LR0ClosureRuleSetDFAState extends State<Node, LR0ClosureRuleSetDFAState>
{
    private Set<GrammarClosureRule> closureRules;

    public LR0ClosureRuleSetDFAState(String name, boolean accepting)
    {
        this(name, accepting, new HashSet<GrammarClosureRule>());
    }

    public LR0ClosureRuleSetDFAState(String name, boolean accepting, Set<GrammarClosureRule> closureRules)
    {
        super(name, accepting);

        this.closureRules = closureRules;
    }

    public Set<GrammarClosureRule> getClosureRules()
    {
        return this.closureRules;
    }

    protected StateComparator buildStateComparator()
    {
        return new LR0ClosureRuleSetDFAStateComparator();
    }

    private class LR0ClosureRuleSetDFAStateComparator extends StateComparator
    {
        public boolean equalsState(State state, State otherState, List<State> ourCoveredStates, List<State> otherCoveredStates)
        {
            if (!super.equalsState(state, otherState, ourCoveredStates, otherCoveredStates))
            {
                return false;
            }

            return ((LR0ClosureRuleSetDFAState)state).getClosureRules().equals(((LR0ClosureRuleSetDFAState)otherState).getClosureRules());
        }
    }
}
