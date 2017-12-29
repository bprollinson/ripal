package larp.syntaxtokenizer.regularlanguage;

import larp.token.regularlanguage.CharacterToken;
import larp.token.regularlanguage.CloseBraceToken;
import larp.token.regularlanguage.KleeneClosureToken;
import larp.token.regularlanguage.OpenBraceToken;
import larp.token.regularlanguage.OrToken;
import larp.token.regularlanguage.RegularExpressionSyntaxToken;

import java.util.ArrayList;
import java.util.List;

public class RegularExpressionSyntaxTokenizer
{
    private char openBrace = '(';
    private char closeBrace = ')';
    private char kleeneClosure = '*';
    private char or = '|';

    public List<RegularExpressionSyntaxToken> tokenize(String expression) throws RegularExpressionSyntaxTokenizerException
    {
        List<RegularExpressionSyntaxToken> tokens = new ArrayList<RegularExpressionSyntaxToken>();

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
}
