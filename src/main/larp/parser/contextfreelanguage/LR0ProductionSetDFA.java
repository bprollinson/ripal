/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.parser.contextfreelanguage;

import larp.automaton.FiniteAutomata;
import larp.automaton.State;
import larp.grammar.contextfreelanguage.ContextFreeGrammar;

public class LR0ProductionSetDFA extends FiniteAutomata<LR0ProductionSetDFAState>
{
    private ContextFreeGrammar grammar;

    public LR0ProductionSetDFA(LR0ProductionSetDFAState startState, ContextFreeGrammar grammar)
    {
        super(startState);

        this.grammar = grammar;
    }

    public ContextFreeGrammar getGrammar()
    {
        return this.grammar;
    }

    public boolean structureEquals(Object other)
    {
        if (!super.structureEquals(other))
        {
            return false;
        }

        return this.grammar.equals(((LR0ProductionSetDFA)other).getGrammar());
    }
}
