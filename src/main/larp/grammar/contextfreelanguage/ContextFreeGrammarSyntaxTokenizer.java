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
        int numEpsilons = 0;
        int numNonTerminals = 0;
        int numTerminals = 0;

        for (int i = 0; i < expression.length(); i++)
        {
            char currentCharacter = expression.charAt(i);
            if (currentCharacter == ':')
            {
                if (buffer.length() > 0)
                {
                    tokens.add(new NonTerminalToken(buffer));
                    numNonTerminals++;
                }
                buffer = "";
                numSeparators++;
                tokens.add(new SeparatorToken());
            }
            else if (currentCharacter == '"' && !inTerminal)
            {
                if (buffer.length() > 0)
                {
                    tokens.add(new NonTerminalToken(buffer));
                    numNonTerminals++;
                    buffer = "";
                }
                inTerminal = true;
            }
            else if (currentCharacter == '"' && inTerminal)
            {
                if (buffer.length() == 0)
                {
                    tokens.add(new EpsilonToken());
                    numEpsilons++;
                }
                else
                {
                    tokens.add(new TerminalToken(buffer));
                    numTerminals++;
                }
                buffer = "";
                inTerminal = false;
            }
            else if (currentCharacter == ' ' && !inTerminal)
            {
                if (buffer.length() > 0)
                {
                    tokens.add(new NonTerminalToken(buffer));
                    numNonTerminals++;
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
            numNonTerminals++;
        }

        if (inTerminal)
        {
            throw new IncorrectContextFreeGrammarQuoteNestingException();
        }

        new ContextFreeGrammarStartingTokenAssertion(tokens).validate();

        if (numSeparators != 1)
        {
            throw new IncorrectContextFreeGrammarSeparatorException();
        }

        boolean epsilonAdded = false;

        Vector<ContextFreeGrammarSyntaxToken> correctedTokens = new Vector<ContextFreeGrammarSyntaxToken>();
        for (int i = 0; i < tokens.size(); i++)
        {
            if (!(tokens.get(i) instanceof EpsilonToken))
            {
                correctedTokens.add(tokens.get(i));
            }

            if (tokens.get(i) instanceof EpsilonToken && numTerminals == 0 && numNonTerminals == 1 && !epsilonAdded)
            {
                correctedTokens.add(tokens.get(i));
                epsilonAdded = true;
            }
        }

        return correctedTokens;
    }
}
