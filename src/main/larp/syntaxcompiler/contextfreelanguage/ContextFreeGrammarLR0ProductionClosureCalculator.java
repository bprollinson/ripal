package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.util.SetMap;

import java.util.HashSet;
import java.util.Set;

public class ContextFreeGrammarLR0ProductionClosureCalculator
{
    public Set<ContextFreeGrammarSyntaxNode> calculateClosure(ContextFreeGrammar cfg, Set<ContextFreeGrammarSyntaxNode> productions)
    {
        Set<ContextFreeGrammarSyntaxNode> productionsClosure = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionsClosure.addAll(productions);

        SetMap<ContextFreeGrammarSyntaxNode, Integer> startingTerminalProductions = this.calculateStartingTerminalProductionsMap(cfg);

        return productionsClosure;
    }

    private SetMap<ContextFreeGrammarSyntaxNode, Integer> calculateStartingTerminalProductionsMap(ContextFreeGrammar cfg)
    {
        SetMap<ContextFreeGrammarSyntaxNode, Integer> startingTerminalProductions = new SetMap<ContextFreeGrammarSyntaxNode, Integer>();
        for (int i = 0; i < cfg.getProductions().size(); i++)
        {
            startingTerminalProductions.put(cfg.getProduction(i).getChildNodes().get(0), i);
        }

        return startingTerminalProductions;
    }
}
