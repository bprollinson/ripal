package larp.parsetable;

public class LL1ParseTable
{
    private ContextFreeGrammar contextFreeGrammar;

    public LL1ParseTable(ContextFreeGrammar contextFreeGrammar)
    {
        this.contextFreeGrammar = contextFreeGrammar;
    }

    public boolean accepts(String inputString)
    {
        return true;
    }
}
