package larp.parser.contextfreelanguage;

public class LR0ReduceAction extends LR0ParseTableAction
{
    private int productionIndex;

    public LR0ReduceAction(int productionIndex)
    {
        this.productionIndex = productionIndex;
    }
}
