package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;

public class ContextFreeGrammarLR0SyntaxCompiler
{
    private ContextFreeGrammarLR0ProductionSetDFACompiler DFACompiler;

    public ContextFreeGrammarLR0SyntaxCompiler()
    {
        this.DFACompiler = new ContextFreeGrammarLR0ProductionSetDFACompiler();
    }

    public LR0ParseTable compile(ContextFreeGrammar grammar)
    {
        if (grammar.getStartSymbol() == null)
        {
            return new LR0ParseTable(grammar, null);
        }

        LR0ProductionSetDFA DFA = this.DFACompiler.compile(grammar);

        return new LR0ParseTable(grammar, DFA.getStartState());
    }
}
