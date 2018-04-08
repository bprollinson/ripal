package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.HashSet;
import java.util.Set;

public class ContextFreeGrammarLR0ProductionSetDFACompiler
{
    public LR0ProductionSetDFA compile(ContextFreeGrammar cfg)
    {
        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        LR0ProductionSetDFAState startState = new LR0ProductionSetDFAState("S0", false, productionSet);

        return new LR0ProductionSetDFA(startState);
    }
}
