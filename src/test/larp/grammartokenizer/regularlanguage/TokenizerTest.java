/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.grammartokenizer.regularlanguage;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.token.regularlanguage.CharacterToken;
import larp.token.regularlanguage.CloseParenthesisToken;
import larp.token.regularlanguage.EpsilonToken;
import larp.token.regularlanguage.KleeneClosureToken;
import larp.token.regularlanguage.OpenParenthesisToken;
import larp.token.regularlanguage.OrToken;
import larp.token.regularlanguage.RegularExpressionGrammarToken;

import java.util.ArrayList;
import java.util.List;

public class TokenizerTest
{
    @Test
    public void testTokenizeTokenizesString() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("(ab|c)*");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new CharacterToken('b'));
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('c'));
        expectedResult.add(new CloseParenthesisToken());
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesEpsilonExpression() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesExpressionContainingCharacterBeforeSpace() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("a ");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new CharacterToken(' '));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesExpressionContainingCharacterAfterSpace() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize(" a");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken(' '));
        expectedResult.add(new CharacterToken('a'));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesExpressionContainingTwoConsecutiveSpaces() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("  ");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken(' '));
        expectedResult.add(new CharacterToken(' '));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesKleeneClosureAppliedToLeadingEpsilon() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("*");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesOrAppliedToLeadingEpsilon() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("|a");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('a'));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesOrAppliedToTrailingEpsilon() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("a|");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new EpsilonToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesCloseParenthesisAfterOpenParenthesis() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("()");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new CloseParenthesisToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesOrAfterOpenParenthesis() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("(|a)");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new CloseParenthesisToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesKleeneClosureAfterOpenParenthesis() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("(*)");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new KleeneClosureToken());
        expectedResult.add(new CloseParenthesisToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesCloseParenthesisAfterOr() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("(a|)");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new OpenParenthesisToken());
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new CloseParenthesisToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesOrAfterOr() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("a||b");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new OrToken());
        expectedResult.add(new CharacterToken('b'));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesKleeneClosureAfterOr() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("a|*");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken('a'));
        expectedResult.add(new OrToken());
        expectedResult.add(new EpsilonToken());
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeTokenizesKleeneClosureAppliedtoSpace() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize(" *");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken(' '));
        expectedResult.add(new KleeneClosureToken());

        assertEquals(expectedResult, result);
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testTokenizeThrowsExceptionForNegativeParenthesisNesting() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize(")(");
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testTokenizeThrowsExceptionForUnclosedParenthesisAtEndOfString() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("(");
    }

    @Test
    public void testTokenizeTokenizesEscapedSpecialCharacters() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("\\(\\)\\*\\|\\\\");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken('('));
        expectedResult.add(new CharacterToken(')'));
        expectedResult.add(new CharacterToken('*'));
        expectedResult.add(new CharacterToken('|'));
        expectedResult.add(new CharacterToken('\\'));

        assertEquals(expectedResult, result);
    }

    @Test(expected = DanglingRegularExpressionEscapeCharacterException.class)
    public void testTokenizeThrowsExceptionForDanglingEscapeCharacter() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("\\");
    }

    @Test
    public void testTokenizeDoesNotAdjustParenthesisNestingLevelOnEscapedOpenParenthesis() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("\\(");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken('('));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeDoesNotAdjustParenthesisNestingLevelOnEscapedCloseParenthesis() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("\\)");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken(')'));

        assertEquals(expectedResult, result);
    }

    @Test
    public void testTokenizeGeneratesStandardCharacterTokenFromCharacterAfterEscapeCharacter() throws RegularExpressionGrammarTokenizerException
    {
        Tokenizer tokenizer = new Tokenizer();

        List<RegularExpressionGrammarToken> result = tokenizer.tokenize("\\a");
        List<RegularExpressionGrammarToken> expectedResult = new ArrayList<RegularExpressionGrammarToken>();
        expectedResult.add(new CharacterToken('a'));

        assertEquals(expectedResult, result);
    }
}
