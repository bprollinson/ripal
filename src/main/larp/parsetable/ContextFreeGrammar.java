package larp.parsetable;

import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.ProductionNode;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammar
{
    private List<ContextFreeGrammarSyntaxNode> productions;

    public ContextFreeGrammar()
    {
        this.productions = new ArrayList<ContextFreeGrammarSyntaxNode>();
    }

    public void addProduction(ContextFreeGrammarSyntaxNode productionNode)
    {
        this.productions.add(productionNode);
    }

    public void addProduction(NonTerminalNode nonTerminalNode, ContextFreeGrammarSyntaxNode... rightHandNodes)
    {
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(nonTerminalNode);

        ConcatenationNode concatenationNode = new ConcatenationNode();
        for (ContextFreeGrammarSyntaxNode rightHandNode: rightHandNodes)
        {
            concatenationNode.addChild(rightHandNode);
        }
        productionNode.addChild(concatenationNode);

        this.addProduction(productionNode);
    }

    public ContextFreeGrammarSyntaxNode getProduction(int index)
    {
        return this.productions.get(index);
    }

    public List<ContextFreeGrammarSyntaxNode> getProductions()
    {
        return this.productions;
    }

    public NonTerminalNode getStartSymbol()
    {
        return (NonTerminalNode)this.productions.get(0).getChildNodes().get(0);
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof ContextFreeGrammar))
        {
            return false;
        }

        return this.productions.equals(((ContextFreeGrammar)other).getProductions());
    }
}
