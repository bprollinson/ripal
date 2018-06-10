package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.util.ValueToSetMap;

import java.util.HashSet;
import java.util.Set;

public class ContextFreeGrammarClosureCalculator
{
    private ProductionNodeDotRepository productionNodeDotRepository;

    public ContextFreeGrammarClosureCalculator()
    {
        this.productionNodeDotRepository = new ProductionNodeDotRepository();
    }

    public Set<ContextFreeGrammarSyntaxNode> calculate(ContextFreeGrammar cfg, Set<ContextFreeGrammarSyntaxNode> productionSet)
    {
        Set<ContextFreeGrammarSyntaxNode> productionsClosure = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionsClosure.addAll(productionSet);
        ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductionsMap = this.calculateStartingNonTerminalProductionsMap(cfg);

        boolean continueExpansion = true;
        while (continueExpansion)
        {
            continueExpansion = this.expandClosure(cfg, startingNonTerminalProductionsMap, productionsClosure);
        }

        return productionsClosure;
    }

    private ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer> calculateStartingNonTerminalProductionsMap(ContextFreeGrammar cfg)
    {
        ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductions = new ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer>();
        for (int i = 0; i < cfg.getProductions().size(); i++)
        {
            startingNonTerminalProductions.put(cfg.getProduction(i).getChildNodes().get(0), i);
        }

        return startingNonTerminalProductions;
    }

    private boolean expandClosure(ContextFreeGrammar cfg, ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductionsMap, Set<ContextFreeGrammarSyntaxNode> productionsClosure)
    {
        Set<ContextFreeGrammarSyntaxNode> productionsToAdd = new HashSet<ContextFreeGrammarSyntaxNode>();

        for (ContextFreeGrammarSyntaxNode productionNode: productionsClosure)
        {
            ContextFreeGrammarSyntaxNode nextSymbol = this.productionNodeDotRepository.findProductionSymbolAfterDot(productionNode);
            if (nextSymbol != null)
            {
                this.addProductionsForNonTerminal(cfg, startingNonTerminalProductionsMap.get(nextSymbol), productionsToAdd);
            }
        }

        return productionsClosure.addAll(productionsToAdd);
    }

    private void addProductionsForNonTerminal(ContextFreeGrammar cfg, Set<Integer> productionIndices, Set<ContextFreeGrammarSyntaxNode> productionsToAdd)
    {
        for (int productionIndex: productionIndices)
        {
            ContextFreeGrammarSyntaxNode production = cfg.getProductions().get(productionIndex);

            ContextFreeGrammarSyntaxNode productionNode = this.productionNodeDotRepository.addDotToProductionRightHandSide(production);
            productionsToAdd.add(productionNode);
        }
    }
}
