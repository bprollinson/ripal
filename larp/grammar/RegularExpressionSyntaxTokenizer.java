package larp.grammar;

import java.util.Vector;

public class RegularExpressionSyntaxTokenizer
{
    private char openBrace = '(';
    private char closeBrace = ')';
    private char kleeneClosure = '*';
    private char or = '|';

    public Vector<RegularExpressionSyntaxToken> tokenize(String expression) throws RegularExpressionSyntaxTokenizerException
    {
        Vector<RegularExpressionSyntaxToken> tokens = new Vector<RegularExpressionSyntaxToken>();

        int nestingLevel = 0;
        Character lastCharacter = null;

        for (int i = 0; i < expression.length(); i++)
        {
            char currentCharacter = expression.charAt(i);

            if (currentCharacter == this.openBrace)
            {
                nestingLevel++;
            }

            if (currentCharacter == this.closeBrace)
            {
                nestingLevel--;
            }

            if (nestingLevel < 0)
            {
                throw new IncorrectRegularExpressionNestingException();
            }

            this.assertTokenSequence(lastCharacter, currentCharacter);

            if (currentCharacter == this.openBrace)
            {
                tokens.add(new OpenBraceToken());
            }
            else if (currentCharacter == this.closeBrace)
            {
                tokens.add(new CloseBraceToken());
            }
            else if (currentCharacter == this.kleeneClosure)
            {
                tokens.add(new KleeneClosureToken());
            }
            else if (currentCharacter == this.or)
            {
                tokens.add(new OrToken());
            }
            else
            {
                tokens.add(new CharacterToken(currentCharacter));
            }

            lastCharacter = currentCharacter;
        }

        if (nestingLevel != 0)
        {
            throw new IncorrectRegularExpressionNestingException();
        }

        return tokens;
    }

    private void assertTokenSequence(Character lastCharacter, char currentCharacter) throws RegularExpressionSyntaxTokenizerException
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
