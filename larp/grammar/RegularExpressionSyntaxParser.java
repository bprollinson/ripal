package larp.grammar;

import java.util.Vector;

public class RegularExpressionSyntaxParser
{
    public RegularExpressionSyntaxNode parse(Vector<RegularExpressionSyntaxToken> tokens)
    {
        ConcatenationNode node = new ConcatenationNode();
        for (int i = 0; i < tokens.size(); i++)
        {
            CharacterToken token = (CharacterToken)(tokens).get(i);
            node.addChild(new CharacterNode(token.getCharacter()));
        }

        return node;
    }
}
