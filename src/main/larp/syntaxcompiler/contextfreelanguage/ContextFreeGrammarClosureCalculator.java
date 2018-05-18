package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.util.SetMap;

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
        SetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductionsMap = this.calculateStartingNonTerminalProductionsMap(cfg);

        boolean continueExpansion = true;
        while (continueExpansion)
        {
            continueExpansion = this.expandClosure(cfg, startingNonTerminalProductionsMap, productionsClosure);
        }

        return productionsClosure;
    }

    private SetMap<ContextFreeGrammarSyntaxNode, Integer> calculateStartingNonTerminalProductionsMap(ContextFreeGrammar cfg)
    {
        SetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductions = new SetMap<ContextFreeGrammarSyntaxNode, Integer>();
        for (int i = 0; i < cfg.getProductions().size(); i++)
        {
            startingNonTerminalProductions.put(cfg.getProduction(i).getChildNodes().get(0), i);
        }

        return startingNonTerminalProductions;
    }

    private boolean expandClosure(ContextFreeGrammar cfg, SetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductionsMap, Set<ContextFreeGrammarSyntaxNode> productionsClosure)
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

            ProductionNode productionNode = new ProductionNode();
            productionNode.addChild(production.getChildNodes().get(0));
            ConcatenationNode concatenationNode = new ConcatenationNode();
            concatenationNode.addChild(new DotNode());
            ContextFreeGrammarSyntaxNode existingRightSide = production.getChildNodes().get(1);
            for (ContextFreeGrammarSyntaxNode childNode: existingRightSide.getChildNodes())
            {
                concatenationNode.addChild(childNode);
            }
            productionNode.addChild(concatenationNode);

            productionsToAdd.add(productionNode);
        }
    }
}
