package larp.grammar.regularlanguage;

public class CharacterToken extends RegularExpressionSyntaxToken
{
    private char character;

    public CharacterToken(char character)
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

        return this.character == ((CharacterToken)other).getCharacter();
    }
}
