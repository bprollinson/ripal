package larp.parser.contextfreelanguage;

public class LR0GotoAction implements LR0ParseTableAction
{
    private int stateIndex;

    public LR0GotoAction(int stateIndex)
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
        if (!(other instanceof LR0GotoAction))
        {
            return false;
        }

        return this.stateIndex == ((LR0GotoAction)other).getStateIndex();
    }
}
