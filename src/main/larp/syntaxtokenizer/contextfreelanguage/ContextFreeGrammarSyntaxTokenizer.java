package larp.syntaxtokenizer.contextfreelanguage;

import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.token.contextfreelanguage.EpsilonToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.TerminalToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarSyntaxTokenizer
{
    private boolean inTerminal;
    private boolean escaping;
    private int numSeparators;
    private int numTerminals;
    private int numNonTerminals;

    public List<ContextFreeGrammarSyntaxToken> tokenize(String expression) throws ContextFreeGrammarSyntaxTokenizerException
    {
        this.inTerminal = false;
        this.escaping = false;
        this.numSeparators = 0;
        this.numTerminals = 0;
        this.numNonTerminals = 0;

        List<ContextFreeGrammarSyntaxToken> tokens = this.convertCharactersToTokens(expression);

        new ContextFreeGrammarFinalQuoteNestingCorrectAssertion(this.inTerminal).validate();
        new ContextFreeGrammarStartingTokensValidAssertion(tokens).validate();
        new ContextFreeGrammarCorrectNumberOfSeparatorsAssertion(this.numSeparators).validate();

        return this.correctEpsilonSetupInTokens(tokens);
    }

    private List<ContextFreeGrammarSyntaxToken> convertCharactersToTokens(String expression)
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();

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

    private String processCharacter(List<ContextFreeGrammarSyntaxToken> tokens, String buffer, char currentCharacter)
    {
        if (this.escaping)
        {
            this.escaping = false;
            buffer += currentCharacter;

            return buffer;
        }

        if (currentCharacter == '\\')
        {
            this.escaping = true;

            return buffer;
        }

        if (currentCharacter == ':' && !this.inTerminal)
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

    private String processSeparatorCharacter(List<ContextFreeGrammarSyntaxToken> tokens, String buffer)
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

    private String processTerminalStartQuoteCharacter(List<ContextFreeGrammarSyntaxToken> tokens, String buffer)
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

    private String processTerminalEndQuoteCharacter(List<ContextFreeGrammarSyntaxToken> tokens, String buffer)
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

    private String processSpaceCharacter(List<ContextFreeGrammarSyntaxToken> tokens, String buffer)
    {
        if (buffer.length() > 0)
        {
            tokens.add(new NonTerminalToken(buffer));
            this.numNonTerminals++;
        }
        buffer = "";

        return buffer;
    }

    private List<ContextFreeGrammarSyntaxToken> correctEpsilonSetupInTokens(List<ContextFreeGrammarSyntaxToken> tokens)
    {
        List<ContextFreeGrammarSyntaxToken> correctedTokens = new ArrayList<ContextFreeGrammarSyntaxToken>();

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
