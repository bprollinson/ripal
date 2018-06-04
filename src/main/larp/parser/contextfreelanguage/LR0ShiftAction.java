package larp.parser.contextfreelanguage;

public class LR0ShiftAction implements LR0ParseTableAction
{
    private int stateIndex;

    public LR0ShiftAction(int stateIndex)
    {
        this.stateIndex = stateIndex;
    }

    public boolean isRowLevelAction()
    {
        return false;
    }

    public int getStateIndex()
    {
        return this.stateIndex;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ShiftAction))
        {
            return false;
        }

        return this.stateIndex == ((LR0ShiftAction)other).getStateIndex();
    }
}
