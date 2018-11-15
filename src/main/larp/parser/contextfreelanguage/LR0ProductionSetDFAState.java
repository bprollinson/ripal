/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.automaton.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarParseTreeNode;

import java.util.List;
import java.util.Set;

public class LR0ProductionSetDFAState extends State<ContextFreeGrammarParseTreeNode, LR0ProductionSetDFAState>
{
    private Set<ContextFreeGrammarParseTreeNode> productionSet;

    public LR0ProductionSetDFAState(String name, boolean accepting, Set<ContextFreeGrammarParseTreeNode> productionSet)
    {
        super(name, accepting);

        this.productionSet = productionSet;
    }

    public Set<ContextFreeGrammarParseTreeNode> getProductionSet()
    {
        return this.productionSet;
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

            return ((LR0ProductionSetDFAState)state).getProductionSet().equals(((LR0ProductionSetDFAState)otherState).getProductionSet());
        }
    }
}
