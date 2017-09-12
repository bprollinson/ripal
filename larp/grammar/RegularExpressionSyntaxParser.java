package larp.grammar;

import java.util.Vector;

public class RegularExpressionSyntaxParser
{
    public RegularExpressionSyntaxNode parse(Vector<RegularExpressionSyntaxToken> tokens)
    {
        ConcatenationNode node = new ConcatenationNode();
        CharacterToken token = (CharacterToken)(tokens).get(0);
        node.addChild(new CharacterNode(token.getCharacter()));

        return node;
    }
}
