package larp.parser.contextfreelanguage;

public class LR0ShiftAction extends LR0ParseTableAction
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
}
