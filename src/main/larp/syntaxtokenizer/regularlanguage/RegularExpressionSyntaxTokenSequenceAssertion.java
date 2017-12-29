package larp.syntaxtokenizer.regularlanguage;

public class RegularExpressionSyntaxTokenSequenceAssertion
{
    private char closeBrace;
    private char or;

    public RegularExpressionSyntaxTokenSequenceAssertion(char closeBrace, char or)
    {
        this.closeBrace = closeBrace;
        this.or = or;
    }

    public void validate(Character lastCharacter, char currentCharacter) throws RegularExpressionSyntaxTokenizerException
    {
        if (currentCharacter == this.closeBrace && (lastCharacter == this.or))
        {
            throw new IncorrectCloseBraceApplicationException();
        }
    }
}
