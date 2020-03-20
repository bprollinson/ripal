/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammarparser.contextfreelanguage;

import ripal.parsetree.contextfreelanguage.ConcatenationNode;
import ripal.parsetree.contextfreelanguage.EpsilonNode;
import ripal.parsetree.contextfreelanguage.Node;
import ripal.parsetree.contextfreelanguage.NonTerminalNode;
import ripal.parsetree.contextfreelanguage.ProductionNode;
import ripal.parsetree.contextfreelanguage.TerminalNode;
import ripal.token.contextfreelanguage.EpsilonToken;
import ripal.token.contextfreelanguage.NonTerminalToken;
import ripal.token.contextfreelanguage.TerminalToken;
import ripal.token.contextfreelanguage.Token;

import java.util.List;

public class GrammarParser
{
    public Node parse(List<Token> tokens)
    {
        ProductionNode productionNode = new ProductionNode();
        NonTerminalToken nonTerminalToken = (NonTerminalToken)tokens.get(0);
        NonTerminalNode nonTerminalNode = new NonTerminalNode(nonTerminalToken.getName());
        productionNode.addChild(nonTerminalNode);
        ConcatenationNode concatenationNode = new ConcatenationNode();
        productionNode.addChild(concatenationNode);

        for (int i = 2; i < tokens.size(); i++)
        {
            Token token = tokens.get(i);

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
