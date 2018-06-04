package larp.parser.contextfreelanguage;

public class LR0ReduceAction implements LR0ParseTableAction
{
    private int productionIndex;

    public LR0ReduceAction(int productionIndex)
    {
        this.productionIndex = productionIndex;
    }

    public boolean isRowLevelAction()
    {
        return true;
    }

    public int getProductionIndex()
    {
        return this.productionIndex;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ReduceAction))
        {
            return false;
        }

        return this.productionIndex == ((LR0ReduceAction)other).getProductionIndex();
    }
}
