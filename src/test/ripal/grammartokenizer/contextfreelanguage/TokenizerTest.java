/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.grammartokenizer.contextfreelanguage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import ripal.token.contextfreelanguage.EpsilonToken;
import ripal.token.contextfreelanguage.NonTerminalToken;
import ripal.token.contextfreelanguage.SeparatorToken;
import ripal.token.contextfreelanguage.TerminalToken;
import ripal.token.contextfreelanguage.Token;

import java.util.ArrayList;
import java.util.List;

public class TokenizerTest
{
    @Test
    public void testTokenizeTokenizesSimpleSingleCharacterNonTerminalProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:S");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterNonTerminalOnLeftSideOfProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("Start:S");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("Start"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterNonTerminalOnRightSideOfProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:Start");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("Start"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEpsilonProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesSimpleSingleCharacterTerminalProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"a\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultiCharacterTerminalOnRightSideOfProduction() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"terminal\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesMultipleTerminalsOnRightSideWithoutSpaceBetweenThem() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"terminal1\"\"terminal2\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeIgnoresSpaceBetweenTerminalTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"terminal1\" \"terminal2\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal1"));
        expectedResult.add(new TerminalToken("terminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesSpaceWithinTerminalToken() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"terminal 1\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeIgnoresSpaceBetweenNonTerminalTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:nonterminal1 nonterminal2");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("nonterminal1"));
        expectedResult.add(new NonTerminalToken("nonterminal2"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeHandlesTerminalFollowedDirectlyByNonterminal() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"terminal\"S");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal"));
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenBeforeEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"terminal 1\"\"\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenBeforeEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:S\"\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingTerminalTokenAfterEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"\"\"terminal 1\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesEpsilonFromTokensFromRightHandSideContainingNonTerminalTokenAfterEpsilon() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"\"S");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingMultipleEpsilonTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"\"\"\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingTerminalAndMultipleEpsilonTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"terminal 1\"\"\"\"\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("terminal 1"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeRemovesExtraEpsilonFromRightHandSideContainingNonTerminalAndMultipleEpsilonTokens() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:S\"\"\"\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new NonTerminalToken("S"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesColonWithinTerminalString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\":\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken(":"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEscapedSpecialCharactersWithinTerminalString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"\\\"\\\\\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("\"\\"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeGeneratesStandardCharacterTokenFromCharacterAfterEscapeCharacter() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> result = tokenizer.tokenize("S:\"\\a\"");
        List<Token> expectedResult = new ArrayList<Token>();
        expectedResult.add(new NonTerminalToken("S"));
        expectedResult.add(new SeparatorToken());
        expectedResult.add(new TerminalToken("a"));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeThrowsExceptionForEscapeCharacterOutsideTerminalString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarEscapeCharacterPositionException.class,
            () -> {
                tokenizer.tokenize("S:\\\\");
            }
        );
    }

    @Test
    public void testTokenizeThrowsExceptionForDanglingEscapeCharacter() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            DanglingGrammarEscapeCharacterException.class,
            () -> {
                tokenizer.tokenize("S:\"\\");
            }
        );
    }

    @Test
    public void testTokenizeThrowsExceptionForUnclosedQuoteAtEndOfString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarQuoteNestingException.class,
            () -> {
                tokenizer.tokenize("S:\"a");
            }
        );
    }

    @Test
    public void testTokenizeThrowsExceptionForUnclosedQuoteAtEndOfStringContainingEscapedQuote() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarQuoteNestingException.class,
            () -> {
                tokenizer.tokenize("S:\"\\\"");
            }
        );
    }

    @Test
    public void testTokenizeThrowsExceptionForIncorrectStartingTokenSequence() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarStatementPrefixException.class,
            () -> {
                tokenizer.tokenize(":");
            }
        );
    }

    @Test
    public void testTokenizeThrowsExceptionForIncorrectNumberofSeparators() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarSeparatorException.class,
            () -> {
                tokenizer.tokenize("S::");
            }
        );
    }

    @Test
    public void testExceptionForEscapeCharacterOutsideTerminalStringPrioritizedOverExceptionForDangingEscapeCharacter() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarEscapeCharacterPositionException.class,
            () -> {
                tokenizer.tokenize("S:\\\\\"\\");
            }
        );
    }

    @Test
    public void testExceptionForDangingEscapeCharacterPrioritizedOverExceptionForUncosedQuoteAtEndOfString() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            DanglingGrammarEscapeCharacterException.class,
            () -> {
                tokenizer.tokenize("S:\"\\");
            }
        );
    }

    @Test
    public void testExceptionForDangingEscapeCharacterPrioritizedOverExceptionForIncorrectStartingTokenSequence() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            DanglingGrammarEscapeCharacterException.class,
            () -> {
                tokenizer.tokenize(": \"\\");
            }
        );
    }

    @Test
    public void testExceptionForDangingEscapeCharacterPrioritizedOverExceptionForIncorrectNumberOfSeparators() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            DanglingGrammarEscapeCharacterException.class,
            () -> {
                tokenizer.tokenize("S::\"\\");
            }
        );
    }

    @Test
    public void testExceptionForUncosedQuoteAtEndOfStringPrioritizedOverExceptionForIncorrectStartingTokenSequence() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarQuoteNestingException.class,
            () -> {
                tokenizer.tokenize(": \"");
            }
        );
    }

    @Test
    public void testExceptionForUnclosedQuoteAtEndOfStringPrioritizedOverExceptionForIncorrectNumberOfSeparators() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarQuoteNestingException.class,
            () -> {
                tokenizer.tokenize("S::\"");
            }
        );
    }

    @Test
    public void testExceptionForIncorrectStartingTokenSequencePrioritizedOverExceptionForIncorrectNumberOfSeparators() throws TokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        assertThrows(
            IncorrectGrammarStatementPrefixException.class,
            () -> {
                tokenizer.tokenize("");
            }
        );
    }
}
