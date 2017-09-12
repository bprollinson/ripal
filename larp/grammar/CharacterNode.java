package larp.grammar;

public class CharacterNode extends RegularExpressionSyntaxNode
{
    private char character;

    public CharacterNode(char character)
    {
        this.character = character;
    }
}
