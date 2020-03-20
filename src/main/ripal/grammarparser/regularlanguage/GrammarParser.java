/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammarparser.regularlanguage;

import ripal.parsetree.regularlanguage.CharacterNode;
import ripal.parsetree.regularlanguage.ConcatenationNode;
import ripal.parsetree.regularlanguage.KleeneClosureNode;
import ripal.parsetree.regularlanguage.Node;
import ripal.parsetree.regularlanguage.OrNode;
import ripal.token.regularlanguage.CharacterToken;
import ripal.token.regularlanguage.CloseParenthesisToken;
import ripal.token.regularlanguage.KleeneClosureToken;
import ripal.token.regularlanguage.OpenParenthesisToken;
import ripal.token.regularlanguage.OrToken;
import ripal.token.regularlanguage.Token;

import java.util.ArrayList;
import java.util.List;

public class GrammarParser
{
    public Node parse(List<Token> tokens)
    {
        Node orNode = this.parseOrExpression(tokens);
        if (orNode != null)
        {
            return orNode;
        }

        ConcatenationNode node = new ConcatenationNode();
        int parenthesisBalance = 0;
        int openParenthesisPosition = -1;

        for (int i = 0; i < tokens.size(); i++)
        {
            Token token = tokens.get(i);

            if (token instanceof CharacterToken && parenthesisBalance == 0)
            {
                node.addChild(new CharacterNode(((CharacterToken)token).getCharacter()));
            }
            else if (token instanceof KleeneClosureToken && parenthesisBalance == 0)
            {
                List<Node> childNodes = node.getChildNodes();
                Node lastNode = childNodes.get(childNodes.size() - 1);
                KleeneClosureNode kleeneClosureNode = new KleeneClosureNode();
                kleeneClosureNode.addChild(lastNode);
                childNodes.set(childNodes.size() - 1, kleeneClosureNode);
            }
            else if (token instanceof OpenParenthesisToken)
            {
                if (parenthesisBalance == 0)
                {
                    openParenthesisPosition = i;
                }
                parenthesisBalance++;
            }
            else if (token instanceof CloseParenthesisToken)
            {
                parenthesisBalance--;

                if (parenthesisBalance == 0)
                {
                    node.addChild(this.parse(new ArrayList<Token>(tokens.subList(openParenthesisPosition + 1, i))));
                    openParenthesisPosition = -1;
                }
            }
        }

        return node;
    }

    private Node parseOrExpression(List<Token> tokens)
    {
        ConcatenationNode node = new ConcatenationNode();
        int parenthesisBalance = 0;
        int openParenthesisPosition = -1;

        List<Integer> orPositions = new ArrayList<Integer>();
        for (int i = 0; i < tokens.size(); i++)
        {
            Token token = tokens.get(i);

            if (token instanceof OrToken && parenthesisBalance == 0)
            {
                orPositions.add(i);
            }
            else if (token instanceof OpenParenthesisToken)
            {
                parenthesisBalance++;
            }
            else if (token instanceof CloseParenthesisToken)
            {
                parenthesisBalance--;
            }
        }

        if (orPositions.size() > 0)
        {
            OrNode orNode = new OrNode();

            int startPosition = 0;
            for (int i = 0; i < orPositions.size(); i++)
            {
                int endPosition = orPositions.get(i);
                orNode.addChild(this.parse(new ArrayList<Token>(tokens.subList(startPosition, endPosition))));

                startPosition = endPosition + 1;
            }
            orNode.addChild(this.parse(new ArrayList<Token>(tokens.subList(startPosition, tokens.size()))));

            node.addChild(orNode);

            return node;
        }

        return null;
    }
}
