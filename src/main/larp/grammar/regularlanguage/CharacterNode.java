package larp.grammar.regularlanguage;

public class CharacterNode extends RegularExpressionSyntaxNode
{
    private char character;

    public CharacterNode(char character)
    {
        this.character = character;
    }

    public char getCharacter()
    {
        return this.character;
    }

    public boolean equals(Object other)
    {
        if (!super.equals(other))
        {
            return false;
        }

        return this.character == ((CharacterNode)other).getCharacter();
    }
}
