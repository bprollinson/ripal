/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import ripal.token.contextfreelanguage.NonTerminalToken;
import ripal.token.contextfreelanguage.SeparatorToken;
import ripal.token.contextfreelanguage.Token;

import java.util.ArrayList;
import java.util.List;

public class GrammarStartingTokensValidAssertionTest
{
    @Test
    public void testValidateThrowsExceptionForFewerThanThreeTokens() throws TokenizerException
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        GrammarStartingTokensValidAssertion assertion = new GrammarStartingTokensValidAssertion(tokens);

        assertThrows(
            IncorrectGrammarStatementPrefixException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateThrowsExceptionWhenFirstTokenIsNotANonterminal() throws TokenizerException
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new SeparatorToken());
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        GrammarStartingTokensValidAssertion assertion = new GrammarStartingTokensValidAssertion(tokens);

        assertThrows(
            IncorrectGrammarStatementPrefixException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void testValidateThrowsExceptionWhenSecondTokenIsNotASeparator() throws TokenizerException
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        GrammarStartingTokensValidAssertion assertion = new GrammarStartingTokensValidAssertion(tokens);

        assertThrows(
            IncorrectGrammarStatementPrefixException.class,
            () -> {
                assertion.validate();
            }
        );
    }

    @Test
    public void TestValidateDoesNotThrowExceptionForValidTokenSequence() throws TokenizerException
    {
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        GrammarStartingTokensValidAssertion assertion = new GrammarStartingTokensValidAssertion(tokens);

        assertion.validate();
    }
}
