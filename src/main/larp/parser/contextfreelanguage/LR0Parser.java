package larp.parser.contextfreelanguage;

import larp.ComparableStructure;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.TerminalNode;

public class LR0Parser implements ComparableStructure
{
    private LR0ParseTable parseTable;

    public LR0Parser(LR0ParseTable parseTable)
    {
        this.parseTable = parseTable;
    }

    public LR0ParseTable getParseTable()
    {
        return this.parseTable;
    }

    public boolean accepts(String inputString)
    {
        State currentState = this.parseTable.getStartState();

        while (inputString.length() > 0)
        {
            String nextCharacter = inputString.substring(0, 1);
            inputString = inputString.substring(1);

            LR0ParseTableAction action = this.parseTable.getCell(currentState, new TerminalNode(nextCharacter));
            if (action == null)
            {
                return false;
            }

            currentState = action.getNextState();
        }

        return true;
    }

    public boolean structureEquals(Object other)
    {
        if (!(other instanceof LR0Parser))
        {
            return false;
        }

        return this.parseTable.structureEquals(((LR0Parser)other).getParseTable());
    }
}
