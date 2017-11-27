package larp.grammar.contextfreelanguage;

import java.util.List;
import java.util.Vector;

public class ContextFreeGrammarSyntaxTokenizer
{
    private boolean inTerminal;
    private int numSeparators;
    private int numTerminals;
    private int numNonTerminals;

    public List<ContextFreeGrammarSyntaxToken> tokenize(String expression) throws ContextFreeGrammarSyntaxTokenizerException
    {
        this.inTerminal = false;
        this.numSeparators = 0;
        this.numTerminals = 0;
        this.numNonTerminals = 0;

        Vector<ContextFreeGrammarSyntaxToken> tokens = this.convertCharactersToTokens(expression);

        if (this.inTerminal)
        {
            throw new IncorrectContextFreeGrammarQuoteNestingException();
        }

        new ContextFreeGrammarStartingTokenAssertion(tokens).validate();

        if (this.numSeparators != 1)
        {
            throw new IncorrectContextFreeGrammarSeparatorException();
        }

        return this.correctEpsilonSetupInTokens(tokens);
    }

    private Vector<ContextFreeGrammarSyntaxToken> convertCharactersToTokens(String expression)
    {
        Vector<ContextFreeGrammarSyntaxToken> tokens = new Vector<ContextFreeGrammarSyntaxToken>();

        String buffer = "";

        for (int i = 0; i < expression.length(); i++)
        {
            buffer = this.processCharacter(tokens, buffer, expression.charAt(i));
        }

        if (buffer.length() > 0)
        {
            tokens.add(new NonTerminalToken(buffer));
            this.numNonTerminals++;
        }

        return tokens;
    }

    private String processCharacter(Vector<ContextFreeGrammarSyntaxToken> tokens, String buffer, char currentCharacter)
    {
        if (currentCharacter == ':')
        {
            buffer = this.processSeparatorCharacter(tokens, buffer);
        }
        else if (currentCharacter == '"' && !this.inTerminal)
        {
            buffer = this.processTerminalStartQuoteCharacter(tokens, buffer);
        }
        else if (currentCharacter == '"' && this.inTerminal)
        {
            buffer = this.processTerminalEndQuoteCharacter(tokens, buffer);
        }
        else if (currentCharacter == ' ' && !this.inTerminal)
        {
            buffer = this.processSpaceCharacter(tokens, buffer);
        }
        else
        {
            buffer += currentCharacter;
        }

        return buffer;
    }

    private String processSeparatorCharacter(Vector<ContextFreeGrammarSyntaxToken> tokens, String buffer)
    {
        if (buffer.length() > 0)
        {
            tokens.add(new NonTerminalToken(buffer));
            this.numNonTerminals++;
        }
        buffer = "";
        this.numSeparators++;
        tokens.add(new SeparatorToken());

        return buffer;
    }

    private String processTerminalStartQuoteCharacter(Vector<ContextFreeGrammarSyntaxToken> tokens, String buffer)
    {
        if (buffer.length() > 0)
        {
            tokens.add(new NonTerminalToken(buffer));
            this.numNonTerminals++;
            buffer = "";
        }
        this.inTerminal = true;

        return buffer;
    }

    private String processTerminalEndQuoteCharacter(Vector<ContextFreeGrammarSyntaxToken> tokens, String buffer)
    {
        if (buffer.length() == 0)
        {
            tokens.add(new EpsilonToken());
        }
        else
        {
            tokens.add(new TerminalToken(buffer));
            this.numTerminals++;
        }
        buffer = "";
        this.inTerminal = false;

        return buffer;
    }

    private String processSpaceCharacter(Vector<ContextFreeGrammarSyntaxToken> tokens, String buffer)
    {
        if (buffer.length() > 0)
        {
            tokens.add(new NonTerminalToken(buffer));
            this.numNonTerminals++;
        }
        buffer = "";

        return buffer;
    }

    private Vector<ContextFreeGrammarSyntaxToken> correctEpsilonSetupInTokens(Vector<ContextFreeGrammarSyntaxToken> tokens)
    {
        Vector<ContextFreeGrammarSyntaxToken> correctedTokens = new Vector<ContextFreeGrammarSyntaxToken>();

        boolean epsilonAdded = false;

        for (int i = 0; i < tokens.size(); i++)
        {
            if (!(tokens.get(i) instanceof EpsilonToken))
            {
                correctedTokens.add(tokens.get(i));
            }

            if (tokens.get(i) instanceof EpsilonToken && this.numTerminals == 0 && this.numNonTerminals == 1 && !epsilonAdded)
            {
                correctedTokens.add(tokens.get(i));
                epsilonAdded = true;
            }
        }

        return correctedTokens;
    }
}
