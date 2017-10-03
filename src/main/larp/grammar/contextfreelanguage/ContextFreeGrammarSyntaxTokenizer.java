package larp.grammar.contextfreelanguage;

import java.util.Vector;

public class ContextFreeGrammarSyntaxTokenizer
{
    public Vector<ContextFreeGrammarSyntaxToken> tokenize(String expression) throws ContextFreeGrammarSyntaxTokenizerException
    {
        Vector<ContextFreeGrammarSyntaxToken> tokens = new Vector<ContextFreeGrammarSyntaxToken>();

        String buffer = "";
        boolean inTerminal = false;
        int numSeparators = 0;

        for (int i = 0; i < expression.length(); i++)
        {
            char currentCharacter = expression.charAt(i);
            if (currentCharacter == ':')
            {
                tokens.add(new NonTerminalToken(buffer));
                buffer = "";
                numSeparators++;
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

        if (inTerminal)
        {
            throw new IncorrectContextFreeGrammarQuoteNestingException();
        }

        if (tokens.size() < 2)
        {
            throw new IncorrectContextFreeGrammarStatementPrefixException();
        }
        if (!(tokens.get(0) instanceof NonTerminalToken))
        {
            throw new IncorrectContextFreeGrammarStatementPrefixException();
        }
        if (!(tokens.get(1) instanceof SeparatorToken))
        {
            throw new IncorrectContextFreeGrammarStatementPrefixException();
        }

        if (numSeparators != 1)
        {
            throw new IncorrectContextFreeGrammarSeparatorException();
        }

        return tokens;
    }
}
