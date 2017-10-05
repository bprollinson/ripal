package larp.grammar.contextfreelanguage;

public class TerminalNode extends ContextFreeGrammarSyntaxNode
{
    private String value;

    public TerminalNode(String value)
    {
        this.value = value;
    }
}
