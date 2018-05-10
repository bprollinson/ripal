package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.util.SetMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContextFreeGrammarLR0ProductionSetDFACompiler
{
    private ContextFreeGrammarAugmentor grammarAugmentor;
    private ContextFreeGrammarClosureCalculator closureCalculator;
    private Map<Set<ContextFreeGrammarSyntaxNode>, LR0ProductionSetDFAState> productionSetToStateMap;

    public ContextFreeGrammarLR0ProductionSetDFACompiler()
    {
        this.grammarAugmentor = new ContextFreeGrammarAugmentor();
        this.closureCalculator = new ContextFreeGrammarClosureCalculator();
    }

    public LR0ProductionSetDFA compile(ContextFreeGrammar cfg)
    {
        this.productionSetToStateMap = new HashMap<Set<ContextFreeGrammarSyntaxNode>, LR0ProductionSetDFAState>();
        ContextFreeGrammar augmentedCfg = this.grammarAugmentor.augment(cfg);

        Set<ContextFreeGrammarSyntaxNode> productionSet = new HashSet<ContextFreeGrammarSyntaxNode>();
        ContextFreeGrammarSyntaxNode firstProductionWithDot = this.addDotToProductionRightHandSide(augmentedCfg.getProduction(0));
        productionSet.add(firstProductionWithDot);
        productionSet = this.closureCalculator.calculate(cfg, productionSet);
        LR0ProductionSetDFAState startState = new LR0ProductionSetDFAState("", false, productionSet);

        this.productionSetToStateMap.put(productionSet, startState);

        this.compileAndAttachAdjacentStates(startState);

        return new LR0ProductionSetDFA(startState);
    }

    private ContextFreeGrammarSyntaxNode addDotToProductionRightHandSide(ContextFreeGrammarSyntaxNode productionNode)
    {
        ProductionNode newProductionNode = new ProductionNode();
        newProductionNode.addChild(productionNode.getChildNodes().get(0));

        List<ContextFreeGrammarSyntaxNode> childNodes = productionNode.getChildNodes().get(1).getChildNodes();
        ConcatenationNode newConcatenationNode = new ConcatenationNode();
        newConcatenationNode.addChild(new DotNode());
        for (ContextFreeGrammarSyntaxNode childNode: childNodes)
        {
            newConcatenationNode.addChild(childNode);
        }
        newProductionNode.addChild(newConcatenationNode);

        return newProductionNode;
    }

    private void compileAndAttachAdjacentStates(LR0ProductionSetDFAState state)
    {
        SetMap<ContextFreeGrammarSyntaxNode, ContextFreeGrammarSyntaxNode> symbolToNextClosureMap = new SetMap<ContextFreeGrammarSyntaxNode, ContextFreeGrammarSyntaxNode>();

        Set<ContextFreeGrammarSyntaxNode> productionSet = state.getProductionSet();
        for (ContextFreeGrammarSyntaxNode productionNode: productionSet)
        {
            ContextFreeGrammarSyntaxNode nextSymbol = this.findProductionSymbolAfterDot(productionNode);
            if (nextSymbol != null)
            {
                ContextFreeGrammarSyntaxNode productionWithDotShifted = this.shiftDotInProduction(productionNode);
                symbolToNextClosureMap.put(nextSymbol, productionWithDotShifted);
            }
        }
    }

    private ContextFreeGrammarSyntaxNode findProductionSymbolAfterDot(ContextFreeGrammarSyntaxNode productionNode)
    {
        List<ContextFreeGrammarSyntaxNode> childNodes = productionNode.getChildNodes().get(1).getChildNodes();

        boolean lastNodeWasDot = false;
        for (ContextFreeGrammarSyntaxNode childNode: childNodes)
        {
            if (lastNodeWasDot)
            {
                return childNode;
            }

            lastNodeWasDot = childNode instanceof DotNode;
        }

        return null;
    }

    private ContextFreeGrammarSyntaxNode shiftDotInProduction(ContextFreeGrammarSyntaxNode productionNode)
    {
        return null;
    }
}
