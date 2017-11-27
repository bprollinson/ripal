package larp.grammar.regularlanguage;

import java.util.List;
import java.util.Vector;

public class RegularExpressionSyntaxTokenizer
{
    private char openBrace = '(';
    private char closeBrace = ')';
    private char kleeneClosure = '*';
    private char or = '|';

    public List<RegularExpressionSyntaxToken> tokenize(String expression) throws RegularExpressionSyntaxTokenizerException
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
        new RegularExpressionSyntaxTokenSequenceAssertion(this.openBrace, this.closeBrace, this.kleeneClosure, this.or).validate(lastCharacter, currentCharacter);
    }
}
