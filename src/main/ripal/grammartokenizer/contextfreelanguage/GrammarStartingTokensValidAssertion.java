/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import ripal.assertion.Assertion;
import ripal.token.contextfreelanguage.NonTerminalToken;
import ripal.token.contextfreelanguage.SeparatorToken;
import ripal.token.contextfreelanguage.Token;

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
