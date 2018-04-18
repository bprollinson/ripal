package larp.parser.contextfreelanguage;

import larp.parser.regularlanguage.FiniteAutomata;
import larp.parser.regularlanguage.State;

public class LR0ProductionSetDFA extends FiniteAutomata<LR0ProductionSetDFAState>
{
    public LR0ProductionSetDFA(LR0ProductionSetDFAState startState)
    {
        super(startState);
    }
}
