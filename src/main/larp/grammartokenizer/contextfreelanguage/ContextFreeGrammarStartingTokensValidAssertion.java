/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.contextfreelanguage;

import larp.assertion.Assertion;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;

import java.util.List;

public class ContextFreeGrammarStartingTokensValidAssertion implements Assertion
{
    private List<ContextFreeGrammarSyntaxToken> tokens;

    public ContextFreeGrammarStartingTokensValidAssertion(List<ContextFreeGrammarSyntaxToken> tokens)
    {
        this.tokens = tokens;
    }

    public void validate() throws ContextFreeGrammarTokenizerException
    {
        if (tokens.size() < 3)
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
    }
}
