package larp.grammar;

public class RegularExpressionSyntaxParser
{
    private char openBrace = '(';
    private char closeBrace = ')';

    public void parse(String expression) throws Exception
    {
        int nestingLevel = 0;

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
        }

        if (nestingLevel != 0)
        {
            throw new IncorrectRegularExpressionNestingException();
        }
    }
}
