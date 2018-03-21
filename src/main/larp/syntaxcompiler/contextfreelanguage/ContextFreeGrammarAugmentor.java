package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

import java.util.List;

public class ContextFreeGrammarAugmentor
{
    public ContextFreeGrammar augment(ContextFreeGrammar cfg)
    {
        ContextFreeGrammar newCfg = new ContextFreeGrammar();
        NonTerminalNode startSymbol = cfg.getStartSymbol();

        newCfg.addProduction(new NonTerminalNode(startSymbol.getName() + "'"), cfg.getStartSymbol());

        List<ContextFreeGrammarSyntaxNode> productions = cfg.getProductions();
        for (ContextFreeGrammarSyntaxNode production: productions)
        {
            newCfg.addProduction(production);
        }

        return newCfg;
    }
}
