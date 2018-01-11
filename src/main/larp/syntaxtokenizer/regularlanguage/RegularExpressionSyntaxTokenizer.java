package larp.syntaxtokenizer.regularlanguage;

import larp.token.regularlanguage.CharacterToken;
import larp.token.regularlanguage.CloseParenthesisToken;
import larp.token.regularlanguage.EpsilonToken;
import larp.token.regularlanguage.KleeneClosureToken;
import larp.token.regularlanguage.OpenParenthesisToken;
import larp.token.regularlanguage.OrToken;
import larp.token.regularlanguage.RegularExpressionSyntaxToken;

import java.util.ArrayList;
import java.util.List;

public class RegularExpressionSyntaxTokenizer
{
    private char openParenthesis = '(';
    private char closeParenthesis = ')';
    private char kleeneClosure = '*';
    private char or = '|';
    private char escape = '\\';

    public List<RegularExpressionSyntaxToken> tokenize(String expression) throws RegularExpressionSyntaxTokenizerException
    {
        List<RegularExpressionSyntaxToken> tokens = new ArrayList<RegularExpressionSyntaxToken>();

        int nestingLevel = 0;
        boolean escaping = false;
        Character lastCharacter = null;

        for (int i = 0; i < expression.length(); i++)
        {
            char currentCharacter = expression.charAt(i);

            if (currentCharacter == this.openParenthesis)
            {
                nestingLevel++;
            }
            if (currentCharacter == this.closeParenthesis)
            {
                nestingLevel--;
            }
            if (currentCharacter == this.escape && !escaping)
            {
                escaping = true;
                continue;
            }
            if (escaping)
            {
                escaping = false;
                tokens.add(new CharacterToken(currentCharacter));
                continue;
            }

            new RegularExpressionIntermediateNestingLevelValidAssertion(nestingLevel).validate();

            if (currentCharacter == this.openParenthesis)
            {
                tokens.add(new OpenParenthesisToken());
            }
            else if (currentCharacter == this.closeParenthesis)
            {
                this.addEpsilonBasedOnTokenSequence(tokens, lastCharacter);

                tokens.add(new CloseParenthesisToken());
            }
            else if (currentCharacter == this.kleeneClosure)
            {
                this.addPrefixEpsilon(tokens);
                this.addEpsilonBasedOnTokenSequence(tokens, lastCharacter);

                tokens.add(new KleeneClosureToken());
            }
            else if (currentCharacter == this.or)
            {
                this.addPrefixEpsilon(tokens);
                this.addEpsilonBasedOnTokenSequence(tokens, lastCharacter);

                tokens.add(new OrToken());
            }
            else
            {
                tokens.add(new CharacterToken(currentCharacter));
            }

            lastCharacter = currentCharacter;
        }

        new RegularExpressionFinalNestingLevelValidAssertion(nestingLevel).validate();

        this.addPostfixEpsilon(tokens, lastCharacter);

        return tokens;
    }

    private void addEpsilonBasedOnTokenSequence(List<RegularExpressionSyntaxToken> tokens, Character lastCharacter)
    {
        if (lastCharacter != null && lastCharacter == this.openParenthesis)
        {
            tokens.add(new EpsilonToken());
        }
        if (lastCharacter != null && lastCharacter == this.or)
        {
            tokens.add(new EpsilonToken());
        }
    }

    private void addPrefixEpsilon(List<RegularExpressionSyntaxToken> tokens)
    {
        if (tokens.size() == 0)
        {
            tokens.add(new EpsilonToken());
        }
    }

    private void addPostfixEpsilon(List<RegularExpressionSyntaxToken> tokens, Character lastCharacter)
    {
        if (tokens.size() == 0)
        {
            tokens.add(new EpsilonToken());
        }
        if (lastCharacter != null && lastCharacter == this.or)
        {
            tokens.add(new EpsilonToken());
        }
    }
}
