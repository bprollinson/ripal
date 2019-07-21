/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.automaton.State;
import larp.parsercompiler.contextfreelanguage.GrammarClosureRule;
import larp.parsetree.contextfreelanguage.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LR0ProductionSetDFAState extends State<Node, LR0ProductionSetDFAState>
{
    private Set<GrammarClosureRule> closureRules;

    public LR0ProductionSetDFAState(String name, boolean accepting)
    {
        this(name, accepting, new HashSet<GrammarClosureRule>());
    }

    public LR0ProductionSetDFAState(String name, boolean accepting, Set<GrammarClosureRule> closureRules)
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
        return new LR0ProductionSetDFAStateComparator();
    }

    private class LR0ProductionSetDFAStateComparator extends StateComparator
    {
        public boolean equalsState(State state, State otherState, List<State> ourCoveredStates, List<State> otherCoveredStates)
        {
            if (!super.equalsState(state, otherState, ourCoveredStates, otherCoveredStates))
            {
                return false;
            }

            return ((LR0ProductionSetDFAState)state).getClosureRules().equals(((LR0ProductionSetDFAState)otherState).getClosureRules());
        }
    }
}
