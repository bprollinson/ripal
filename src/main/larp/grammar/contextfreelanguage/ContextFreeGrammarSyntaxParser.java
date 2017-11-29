package larp.grammar.contextfreelanguage;

import java.util.List;

public class ContextFreeGrammarSyntaxParser
{
    public ContextFreeGrammarSyntaxNode parse(List<ContextFreeGrammarSyntaxToken> tokens)
    {
        ProductionNode productionNode = new ProductionNode();
        NonTerminalToken nonTerminalToken = (NonTerminalToken)tokens.get(0);
        NonTerminalNode nonTerminalNode = new NonTerminalNode(nonTerminalToken.getName());
        productionNode.addChild(nonTerminalNode);
        ConcatenationNode concatenationNode = new ConcatenationNode();
        productionNode.addChild(concatenationNode);

        for (int i = 2; i < tokens.size(); i++)
        {
            ContextFreeGrammarSyntaxToken token = tokens.get(i);

            if (token instanceof NonTerminalToken)
            {
                NonTerminalToken innerToken = (NonTerminalToken)token;
                concatenationNode.addChild(new NonTerminalNode(innerToken.getName()));
            }
            else if (token instanceof TerminalToken)
            {
                TerminalToken innerToken = (TerminalToken)token;
                concatenationNode.addChild(new TerminalNode(innerToken.getValue()));
            }
            else if (token instanceof EpsilonToken)
            {
                concatenationNode.addChild(new EpsilonNode());
            }
        }

        return productionNode;
    }
}
