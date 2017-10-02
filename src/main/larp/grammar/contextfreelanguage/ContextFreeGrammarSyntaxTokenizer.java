package larp.grammar.contextfreelanguage;

import java.util.Vector;

public class ContextFreeGrammarSyntaxTokenizer
{
    public Vector<ContextFreeGrammarSyntaxToken> tokenize(String expression) throws ContextFreeGrammarSyntaxTokenizerException
    {
        Vector<ContextFreeGrammarSyntaxToken> tokens = new Vector<ContextFreeGrammarSyntaxToken>();

        String buffer = "";
        boolean inTerminal = false;

        for (int i = 0; i < expression.length(); i++)
        {
            char currentCharacter = expression.charAt(i);
            if (currentCharacter == ':')
            {
                tokens.add(new NonTerminalToken(buffer));
                buffer = "";
                tokens.add(new SeparatorToken());
            }
            else if (currentCharacter == '"' && !inTerminal)
            {
                inTerminal = true;
            }
            else if (currentCharacter == '"' && inTerminal)
            {
                tokens.add(new TerminalToken(buffer));
                buffer = "";
                inTerminal = false;
            }
            else if (currentCharacter == ' ' && !inTerminal)
            {
                if (buffer.length() > 0)
                {
                    tokens.add(new NonTerminalToken(buffer));
                }
                buffer = "";
            }
            else
            {
                buffer += currentCharacter;
            }
        }

        if (buffer.length() > 0)
        {
            tokens.add(new NonTerminalToken(buffer));
        }

        if (tokens.size() < 2)
        {
            throw new IncorrectContextFreeGrammarStatementPrefixException();
        }
        if (!(tokens.get(0) instanceof NonTerminalToken))
        {
            throw new IncorrectContextFreeGrammarStatementPrefixException();
        }

        return tokens;
    }
}
