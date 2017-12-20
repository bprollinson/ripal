package larp.syntaxparser.contextfreelanguage;

import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.TerminalToken;

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
