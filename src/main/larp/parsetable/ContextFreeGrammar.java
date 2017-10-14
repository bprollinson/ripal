package larp.parsetable;

import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;

import java.util.Vector;

public class ContextFreeGrammar
{
    private Vector<ContextFreeGrammarSyntaxNode> productions;

    public ContextFreeGrammar()
    {
        this.productions = new Vector<ContextFreeGrammarSyntaxNode>();
    }

    public void addProduction(ContextFreeGrammarSyntaxNode productionNode)
    {
        this.productions.add(productionNode);
    }

    public NonTerminalNode getStartSymbol()
    {
        return (NonTerminalNode)this.productions.get(0).getChildNodes().get(0);
    }
}
