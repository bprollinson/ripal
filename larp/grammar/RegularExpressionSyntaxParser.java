package larp.grammar;

import java.util.Vector;

public class RegularExpressionSyntaxParser
{
    public RegularExpressionSyntaxNode parse(Vector<RegularExpressionSyntaxToken> tokens)
    {
        ConcatenationNode node = new ConcatenationNode();
        int braceBalance = 0;
        int openBracePosition = -1;

        for (int i = 0; i < tokens.size(); i++)
        {
            RegularExpressionSyntaxToken token = tokens.get(i);

            if (token instanceof CharacterToken && braceBalance == 0)
            {
                node.addChild(new CharacterNode(((CharacterToken)token).getCharacter()));
            }
            else if (token instanceof KleeneClosureToken && braceBalance == 0)
            {
                Vector<RegularExpressionSyntaxNode> childNodes = node.getChildNodes();
                RegularExpressionSyntaxNode lastNode = childNodes.lastElement();
                KleeneClosureNode kleeneClosureNode = new KleeneClosureNode();
                kleeneClosureNode.addChild(lastNode);
                childNodes.set(childNodes.size() - 1, kleeneClosureNode);
            }
            else if (token instanceof OpenBraceToken)
            {
                braceBalance++;
                openBracePosition = i;
            }
            else if (token instanceof CloseBraceToken)
            {
                braceBalance--;

                if (braceBalance == 0)
                {
                    node.addChild(this.parse(new Vector(tokens.subList(openBracePosition + 1, i))));
                    openBracePosition = -1;
                }
            }
        }

        return node;
    }
}
