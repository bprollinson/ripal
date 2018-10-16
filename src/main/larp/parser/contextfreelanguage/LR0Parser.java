package larp.parser.contextfreelanguage;

import larp.ComparableStructure;

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

    public boolean structureEquals(Object other)
    {
        if (!(other instanceof LR0Parser))
        {
            return false;
        }

        return this.parseTable.structureEquals(((LR0Parser)other).getParseTable());
    }
}
