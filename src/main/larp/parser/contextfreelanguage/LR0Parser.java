package larp.parser.contextfreelanguage;

import larp.ComparableStructure;
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
        String nextCharacter = inputString.substring(0, 1);
        LR0ParseTableAction action = this.parseTable.getCell(this.parseTable.getStartState(), new TerminalNode(nextCharacter));

        return action != null;
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
