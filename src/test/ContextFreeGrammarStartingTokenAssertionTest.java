import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammarStartingTokenAssertion;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxToken;
import larp.grammar.contextfreelanguage.ContextFreeGrammarSyntaxTokenizerException;
import larp.grammar.contextfreelanguage.IncorrectContextFreeGrammarStatementPrefixException;
import larp.grammar.contextfreelanguage.NonTerminalToken;
import larp.grammar.contextfreelanguage.SeparatorToken;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeGrammarStartingTokenAssertionTest
{
    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionForFewerThanThreeTokens() throws ContextFreeGrammarSyntaxTokenizerException
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        ContextFreeGrammarStartingTokenAssertion assertion = new ContextFreeGrammarStartingTokenAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenFirstTokenIsNotANonterminal() throws ContextFreeGrammarSyntaxTokenizerException
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();
        tokens.add(new SeparatorToken());
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokenAssertion assertion = new ContextFreeGrammarStartingTokenAssertion(tokens);

        assertion.validate();
    }

    @Test(expected = IncorrectContextFreeGrammarStatementPrefixException.class)
    public void testValidateThrowsExceptionWhenSecondTokenIsNotASeparator() throws ContextFreeGrammarSyntaxTokenizerException
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokenAssertion assertion = new ContextFreeGrammarStartingTokenAssertion(tokens);

        assertion.validate();
    }

    @Test
    public void TestValidateDoesNotThrowExceptionForValidTokenSequence() throws ContextFreeGrammarSyntaxTokenizerException
    {
        List<ContextFreeGrammarSyntaxToken> tokens = new ArrayList<ContextFreeGrammarSyntaxToken>();
        tokens.add(new NonTerminalToken("S"));
        tokens.add(new SeparatorToken());
        tokens.add(new NonTerminalToken("S"));
        ContextFreeGrammarStartingTokenAssertion assertion = new ContextFreeGrammarStartingTokenAssertion(tokens);

        assertion.validate();
    }
}
