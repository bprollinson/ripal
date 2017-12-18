package larp.tokenizer.regularlanguage;

public class RegularExpressionSyntaxTokenSequenceAssertion
{
    private char openBrace;
    private char closeBrace;
    private char kleeneClosure;
    private char or;

    public RegularExpressionSyntaxTokenSequenceAssertion(char openBrace, char closeBrace, char kleeneClosure, char or)
    {
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
        this.kleeneClosure = kleeneClosure;
        this.or = or;
    }

    public void validate(Character lastCharacter, char currentCharacter) throws RegularExpressionSyntaxTokenizerException
    {
        if (currentCharacter == this.kleeneClosure && (lastCharacter == null || lastCharacter == this.openBrace))
        {
            throw new IncorrectKleeneClosureApplicationException();
        }

        if (currentCharacter == this.or && (lastCharacter == null || lastCharacter == this.openBrace))
        {
            throw new IncorrectOrApplicationException();
        }

        if (currentCharacter == this.closeBrace && (lastCharacter == this.or))
        {
            throw new IncorrectCloseBraceApplicationException();
        }
    }
}
