package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
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

    public Set<ContextFreeGrammarSyntaxNode> calculate(ContextFreeGrammar grammar, Set<ContextFreeGrammarSyntaxNode> productionSet)
    {
        Set<ContextFreeGrammarSyntaxNode> productionsClosure = new HashSet<ContextFreeGrammarSyntaxNode>();
        productionsClosure.addAll(productionSet);
        ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductionsMap = this.calculateStartingNonTerminalProductionsMap(grammar);

        boolean continueExpansion = true;
        while (continueExpansion)
        {
            continueExpansion = this.expandClosure(grammar, startingNonTerminalProductionsMap, productionsClosure);
        }

        return productionsClosure;
    }

    private ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer> calculateStartingNonTerminalProductionsMap(ContextFreeGrammar grammar)
    {
        ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductions = new ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer>();
        for (int i = 0; i < grammar.getProductions().size(); i++)
        {
            startingNonTerminalProductions.put(grammar.getProduction(i).getChildNodes().get(0), i);
        }

        return startingNonTerminalProductions;
    }

    private boolean expandClosure(ContextFreeGrammar grammar, ValueToSetMap<ContextFreeGrammarSyntaxNode, Integer> startingNonTerminalProductionsMap, Set<ContextFreeGrammarSyntaxNode> productionsClosure)
    {
        Set<ContextFreeGrammarSyntaxNode> productionsToAdd = new HashSet<ContextFreeGrammarSyntaxNode>();

        for (ContextFreeGrammarSyntaxNode productionNode: productionsClosure)
        {
            ContextFreeGrammarSyntaxNode nextSymbol = this.productionNodeDotRepository.findProductionSymbolAfterDot(productionNode);
            if (nextSymbol != null)
            {
                this.addProductionsForNonTerminal(grammar, startingNonTerminalProductionsMap.get(nextSymbol), productionsToAdd);
            }
            if (nextSymbol instanceof EpsilonNode)
            {
                productionsToAdd.add(this.buildEpsilonProduction(productionNode.getChildNodes().get(0)));
            }
        }

        return productionsClosure.addAll(productionsToAdd);
    }

    private void addProductionsForNonTerminal(ContextFreeGrammar grammar, Set<Integer> productionIndices, Set<ContextFreeGrammarSyntaxNode> productionsToAdd)
    {
        for (int productionIndex: productionIndices)
        {
            ContextFreeGrammarSyntaxNode production = grammar.getProductions().get(productionIndex);

            ContextFreeGrammarSyntaxNode productionNode = this.productionNodeDotRepository.addDotToProductionRightHandSide(production);
            productionsToAdd.add(productionNode);
        }
    }

    private ProductionNode buildEpsilonProduction(ContextFreeGrammarSyntaxNode leftHandNonTerminal)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(leftHandNonTerminal);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new EpsilonNode());
        concatenationNode.addChild(new DotNode());
        productionNode.addChild(concatenationNode);

        return productionNode;
    }
}
