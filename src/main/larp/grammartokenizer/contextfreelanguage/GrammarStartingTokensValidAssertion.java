/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import larp.assertion.Assertion;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;
import larp.token.contextfreelanguage.Token;

import java.util.List;

public class GrammarStartingTokensValidAssertion implements Assertion
{
    private List<Token> tokens;

    public GrammarStartingTokensValidAssertion(List<Token> tokens)
    {
        this.tokens = tokens;
    }

    public void validate() throws TokenizerException
    {
        if (tokens.size() < 3)
        {
            throw new IncorrectGrammarStatementPrefixException();
        }
        if (!(tokens.get(0) instanceof NonTerminalToken))
        {
            throw new IncorrectGrammarStatementPrefixException();
        }
        if (!(tokens.get(1) instanceof SeparatorToken))
        {
            throw new IncorrectGrammarStatementPrefixException();
        }
    }
}
