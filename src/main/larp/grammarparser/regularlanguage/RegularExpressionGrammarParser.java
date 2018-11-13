/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammarparser.regularlanguage;

import larp.parsetree.regularlanguage.CharacterNode;
import larp.parsetree.regularlanguage.ConcatenationNode;
import larp.parsetree.regularlanguage.KleeneClosureNode;
import larp.parsetree.regularlanguage.OrNode;
import larp.parsetree.regularlanguage.RegularExpressionSyntaxNode;
import larp.token.regularlanguage.CharacterToken;
import larp.token.regularlanguage.CloseParenthesisToken;
import larp.token.regularlanguage.KleeneClosureToken;
import larp.token.regularlanguage.OpenParenthesisToken;
import larp.token.regularlanguage.OrToken;
import larp.token.regularlanguage.RegularExpressionSyntaxToken;

import java.util.ArrayList;
import java.util.List;

public class RegularExpressionGrammarParser
{
    public RegularExpressionSyntaxNode parse(List<RegularExpressionSyntaxToken> tokens)
    {
        RegularExpressionSyntaxNode orNode = this.parseOrExpression(tokens);
        if (orNode != null)
        {
            return orNode;
        }

        ConcatenationNode node = new ConcatenationNode();
        int parenthesisBalance = 0;
        int openParenthesisPosition = -1;

        for (int i = 0; i < tokens.size(); i++)
        {
            RegularExpressionSyntaxToken token = tokens.get(i);

            if (token instanceof CharacterToken && parenthesisBalance == 0)
            {
                node.addChild(new CharacterNode(((CharacterToken)token).getCharacter()));
            }
            else if (token instanceof KleeneClosureToken && parenthesisBalance == 0)
            {
                List<RegularExpressionSyntaxNode> childNodes = node.getChildNodes();
                RegularExpressionSyntaxNode lastNode = childNodes.get(childNodes.size() - 1);
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
                    node.addChild(this.parse(new ArrayList<RegularExpressionSyntaxToken>(tokens.subList(openParenthesisPosition + 1, i))));
                    openParenthesisPosition = -1;
                }
            }
        }

        return node;
    }

    private RegularExpressionSyntaxNode parseOrExpression(List<RegularExpressionSyntaxToken> tokens)
    {
        ConcatenationNode node = new ConcatenationNode();
        int parenthesisBalance = 0;
        int openParenthesisPosition = -1;

        List<Integer> orPositions = new ArrayList<Integer>();
        for (int i = 0; i < tokens.size(); i++)
        {
            RegularExpressionSyntaxToken token = tokens.get(i);

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
                orNode.addChild(this.parse(new ArrayList<RegularExpressionSyntaxToken>(tokens.subList(startPosition, endPosition))));

                startPosition = endPosition + 1;
            }
            orNode.addChild(this.parse(new ArrayList<RegularExpressionSyntaxToken>(tokens.subList(startPosition, tokens.size()))));

            node.addChild(orNode);

            return node;
        }

        return null;
    }
}
