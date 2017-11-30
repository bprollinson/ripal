package larp.grammar.contextfreelanguage;

import java.util.List;

public class ContextFreeGrammarStartingTokenAssertion
{
    private List<ContextFreeGrammarSyntaxToken> tokens;

    public ContextFreeGrammarStartingTokenAssertion(List<ContextFreeGrammarSyntaxToken> tokens)
    {
        this.tokens = tokens;
    }

    public void validate() throws ContextFreeGrammarSyntaxTokenizerException
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
