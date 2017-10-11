package larp.grammar.contextfreelanguage;

import java.util.Vector;

public class ContextFreeGrammarSyntaxParser
{
    public ContextFreeGrammarSyntaxNode parse(Vector<ContextFreeGrammarSyntaxToken> tokens)
    {
        ProductionNode productionNode = new ProductionNode();
        NonTerminalToken nonTerminalToken = (NonTerminalToken)tokens.get(0);
        NonTerminalNode nonTerminalNode = new NonTerminalNode(nonTerminalToken.getName());
        productionNode.addChild(nonTerminalNode);
        productionNode.addChild(new ConcatenationNode());

        return productionNode;
    }
}
