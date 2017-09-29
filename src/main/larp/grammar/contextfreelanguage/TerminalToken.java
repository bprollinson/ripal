package larp.grammar.contextfreelanguage;

public class TerminalToken extends ContextFreeGrammarSyntaxToken
{
    private String value;

    public TerminalToken(String value)
    {
        this.value = value;
    }
}
