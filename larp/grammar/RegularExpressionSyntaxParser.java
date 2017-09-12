package larp.grammar;

import java.util.Vector;

public class RegularExpressionSyntaxParser
{
    public RegularExpressionSyntaxNode parse(Vector<RegularExpressionSyntaxToken> tokens)
    {
        ConcatenationNode node = new ConcatenationNode();
        for (int i = 0; i < tokens.size(); i++)
        {
            RegularExpressionSyntaxToken token = tokens.get(i);

            if (token instanceof CharacterToken)
            {
                node.addChild(new CharacterNode(((CharacterToken)token).getCharacter()));
            }
            else if (token instanceof KleeneClosureToken)
            {
                Vector<RegularExpressionSyntaxNode> childNodes = node.getChildNodes();
                RegularExpressionSyntaxNode lastNode = childNodes.lastElement();
                KleeneClosureNode kleeneClosureNode = new KleeneClosureNode();
                kleeneClosureNode.addChild(lastNode);
                childNodes.set(childNodes.size() - 1, kleeneClosureNode);
            }
        }

        return node;
    }
}
