package larp.parser.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.regularlanguage.FiniteAutomata;
import larp.parser.regularlanguage.State;

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
