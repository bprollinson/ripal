/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

import org.junit.Test;

import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarStartingTokensValidAssertion;
import larp.syntaxtokenizer.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.syntaxtokenizer.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;
import larp.token.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.token.contextfreelanguage.NonTerminalToken;
import larp.token.contextfreelanguage.SeparatorToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarStartingTokensValidAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionForFewerThanThreeTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenFirstTokenIsNotANonterminal() throws ContextFreeGrammarSyntaxTokenizerException
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();
        tokens.add(new SeparatorToken());
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenSecondTokenIsNotASeparator() throws ContextFreeGrammarSyntaxTokenizerException
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }

    @Test
    public void TestValidateDoesNotThrowExceptionForValidTokenSequence() throws ContextFreeGrammarSyntaxTokenizerException
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokensValidAssertion assertion = new ContextFreeGrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }
}
