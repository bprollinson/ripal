package larp.grammar.contextfreelanguage;

public class TerminalToken extends ContextFreeGrammarSyntaxToken
{
    private String value;

    public TerminalToken(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }

    public boolean equals(Object other)
    {
        if (!super.equals(other))
        {
            return false;
        }

        return this.value.equals(((TerminalToken)other).getValue());
    }
}
