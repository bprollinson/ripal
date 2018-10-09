package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.regularlanguage.StateTransition;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.List;

public class ContextFreeGrammarLR0SyntaxCompiler
{
    private ContextFreeGrammarLR0ProductionSetDFACompiler DFACompiler;

    public ContextFreeGrammarLR0SyntaxCompiler()
    {
        this.DFACompiler = new ContextFreeGrammarLR0ProductionSetDFACompiler();
    }

    public LR0ParseTable compile(ContextFreeGrammar grammar) throws AmbiguousLR0ParseTableException
    {
        if (grammar.getStartSymbol() == null)
        {
            return new LR0ParseTable(grammar, null);
        }

        LR0ProductionSetDFA DFA = this.DFACompiler.compile(grammar);
        LR0ProductionSetDFAState startState = DFA.getStartState();
        LR0ParseTable parseTable = new LR0ParseTable(grammar, startState);

        this.processState(parseTable, startState);

        return parseTable;
    }

    private void processState(LR0ParseTable parseTable, LR0ProductionSetDFAState state) throws AmbiguousLR0ParseTableException
    {
        List<StateTransition<ContextFreeGrammarSyntaxNode,LR0ProductionSetDFAState>> stateTransitions = state.getTransitions();
        for (StateTransition<ContextFreeGrammarSyntaxNode,LR0ProductionSetDFAState> stateTransition: stateTransitions)
        {
            ContextFreeGrammarSyntaxNode input = stateTransition.getInput();
            LR0ProductionSetDFAState nextState = stateTransition.getNextState();

            if (nextState.isAccepting())
            {
                parseTable.addCell(state, input, new LR0AcceptAction());
            }
        }
    }
}
