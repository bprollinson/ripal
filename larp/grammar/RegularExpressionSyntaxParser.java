package larp.grammar;

public class RegularExpressionSyntaxParser
{
    private char openBrace = '(';
    private char closeBrace = ')';
    private char kleeneClosure = '*';
    private char or = '|';

    public void parse(String expression) throws Exception
    {
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

            lastCharacter = currentCharacter;
        }

        if (nestingLevel != 0)
        {
            throw new IncorrectRegularExpressionNestingException();
        }
    }
}
