package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ParseTable;

public class ContextFreeGrammarLR0SyntaxCompiler
{
    public LR0ParseTable compile(ContextFreeGrammar grammar)
    {
        return new LR0ParseTable(grammar, null);
    }
}
