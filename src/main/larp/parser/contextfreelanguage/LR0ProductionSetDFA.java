package larp.parser.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.regularlanguage.FiniteAutomata;
import larp.parser.regularlanguage.State;

public class LR0ProductionSetDFA extends FiniteAutomata<LR0ProductionSetDFAState>
{
    public LR0ProductionSetDFA(LR0ProductionSetDFAState startState, ContextFreeGrammar grammar)
    {
        super(startState);
    }
}
