/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.regularlanguage;

import larp.token.regularlanguage.CharacterToken;
import larp.token.regularlanguage.CloseParenthesisToken;
import larp.token.regularlanguage.EpsilonToken;
import larp.token.regularlanguage.KleeneClosureToken;
import larp.token.regularlanguage.OpenParenthesisToken;
import larp.token.regularlanguage.OrToken;
import larp.token.regularlanguage.RegularExpressionGrammarToken;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer
{
    private char openParenthesis = '(';
    private char closeParenthesis = ')';
    private char kleeneClosure = '*';
    private char or = '|';
    private char escape = '\\';

    private int nestingLevel;
    private boolean escaping;

    public List<RegularExpressionGrammarToken> tokenize(String expression) throws TokenizerException
    {
        List<RegularExpressionGrammarToken> tokens = new ArrayList<RegularExpressionGrammarToken>();

        this.nestingLevel = 0;
        this.escaping = false;

        Character lastCharacter = null;

        for (int i = 0; i < expression.length(); i++)
        {
            char currentCharacter = expression.charAt(i);
            this.processCharacter(tokens, currentCharacter, lastCharacter);

            lastCharacter = currentCharacter;
        }

        new RegularExpressionFinalNestingLevelValidAssertion(this.nestingLevel).validate();
        new RegularExpressionFinalEscapingStatusValidAssertion(this.escaping).validate();

        this.addPostfixEpsilon(tokens, lastCharacter);

        return tokens;
    }

    private void processCharacter(List<RegularExpressionGrammarToken> tokens, char currentCharacter, Character lastCharacter) throws TokenizerException
    {
        if (this.escaping)
        {
            this.escaping = false;
            tokens.add(new CharacterToken(currentCharacter));

            return;
        }

        if (currentCharacter == this.openParenthesis)
        {
            this.nestingLevel++;
        }
        if (currentCharacter == this.closeParenthesis)
        {
            this.nestingLevel--;
        }
        if (currentCharacter == this.escape)
        {
            this.escaping = true;

            return;
        }

        new RegularExpressionIntermediateNestingLevelValidAssertion(this.nestingLevel).validate();

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
    }

    private void addEpsilonBasedOnTokenSequence(List<RegularExpressionGrammarToken> tokens, Character lastCharacter)
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

    private void addPrefixEpsilon(List<RegularExpressionGrammarToken> tokens)
    {
        if (tokens.size() == 0)
        {
            tokens.add(new EpsilonToken());
        }
    }

    private void addPostfixEpsilon(List<RegularExpressionGrammarToken> tokens, Character lastCharacter)
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
