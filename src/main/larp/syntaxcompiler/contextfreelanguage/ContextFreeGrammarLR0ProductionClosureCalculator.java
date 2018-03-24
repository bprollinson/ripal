package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.util.SetMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextFreeGrammarLR0ProductionClosureCalculator
{
    public Set<ContextFreeGrammarSyntaxNode> calculateClosure(ContextFreeGrammar cfg, Set<ContextFreeGrammarSyntaxNode> productions)
    {
        Set<ContextFreeGrammarSyntaxNode> productionsClosure = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionsClosure.addAll(productions);

        SetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductions = this.calculateStartingNonTerminalProductionsMap(cfg);
        this.expandClosure(cfg, startingNonTerminalProductions, productionsClosure);

        return productionsClosure;
    }

    private SetMap<ContextFreeGrammarSyntaxNode, Integer> calculateStartingNonTerminalProductionsMap(ContextFreeGrammar cfg)
    {
        SetMap<ContextFreeGrammarSyntaxNode, Integer> startingTerminalProductions = new SetMap<ContextFreeGrammarSyntaxNode, Integer>();
        for (int i = 0; i < cfg.getProductions().size(); i++)
        {
            startingTerminalProductions.put(cfg.getProduction(i).getChildNodes().get(0), i);
        }

        return startingTerminalProductions;
    }

    private void expandClosure(ContextFreeGrammar cfg, SetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductions, Set<ContextFreeGrammarSyntaxNode> productionsClosure)
    {
        for (ContextFreeGrammarSyntaxNode production: productionsClosure)
        {
            List<ContextFreeGrammarSyntaxNode> childNodes = production.getChildNodes();
            boolean lastNodeWasDot = false;

            for (int i = 0; i < childNodes.get(1).getChildNodes().size(); i++)
            {
                ContextFreeGrammarSyntaxNode childNode = childNodes.get(1).getChildNodes().get(i);
                if (childNode instanceof DotNode)
                {
                    lastNodeWasDot = true;
                    continue;
                }

                if (childNode instanceof NonTerminalNode && lastNodeWasDot)
                {
                    this.addProductionsForNonTerminal(cfg, startingNonTerminalProductions.get(childNode), productionsClosure);
                }

                lastNodeWasDot = false;
            }
        }
    }

    private void addProductionsForNonTerminal(ContextFreeGrammar cfg, Set<Integer> productions, Set<ContextFreeGrammarSyntaxNode> productionsClosure)
    {
        for (int i: productions)
        {
            ContextFreeGrammarSyntaxNode production = cfg.getProductions().get(i);

            ProductionNode productionNode = new ProductionNode();
            productionNode.addChild(production.getChildNodes().get(0));
            ConcatenationNode concatenationNode = new ConcatenationNode();
            concatenationNode.addChild(new DotNode());
            for (ContextFreeGrammarSyntaxNode childNode: production.getChildNodes().get(1).getChildNodes())
            {
                concatenationNode.addChild(childNode);
            }
            productionNode.addChild(concatenationNode);

            productionsClosure.add(productionNode);
        }
    }
}
