package larp.grammar.contextfreelanguage;

import java.util.Vector;

public class ContextFreeGrammarSyntaxTokenizer
{
    public Vector<ContextFreeGrammarSyntaxToken> tokenize(String expression)
    {
        Vector<ContextFreeGrammarSyntaxToken> tokens = new Vector<ContextFreeGrammarSyntaxToken>();

        for (int i = 0; i < expression.length(); i++)
        {
            char currentCharacter = expression.charAt(i);
            if (currentCharacter == ':')
            {
                tokens.add(new SeparatorToken());
            }
            else
            {
                tokens.add(new NonTerminalToken(currentCharacter + ""));
            }
        }

        return tokens;
    }
}
