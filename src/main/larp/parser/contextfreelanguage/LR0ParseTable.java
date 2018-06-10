package larp.parser.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.util.PairToValueMap;

import java.util.HashMap;
import java.util.Map;

public class LR0ParseTable
{
    private ContextFreeGrammar contextFreeGrammar;
    private Map<State, LR0ParseTableAction> rows;
    private PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> cells;

    public LR0ParseTable(ContextFreeGrammar contextFreeGrammar)
    {
        this.contextFreeGrammar = contextFreeGrammar;
        this.rows = new HashMap<State, LR0ParseTableAction>();
        this.cells = new PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
    }

    public void addCell(State state, ContextFreeGrammarSyntaxNode syntaxNode, LR0ParseTableAction action) throws AmbiguousLR0ParseTableException
    {
        new LR0ParseTableCellAvailableAssertion(this, state, syntaxNode, action).validate();

        if (action.isRowLevelAction())
        {
            this.addRowEntry(state, action);
        }
        else
        {
            this.addCellEntry(state, syntaxNode, action);
        }
    }

    private void addRowEntry(State state, LR0ParseTableAction action)
    {
        this.rows.put(state, action);
    }

    private void addCellEntry(State state, ContextFreeGrammarSyntaxNode syntaxNode, LR0ParseTableAction action)
    {
        this.cells.put(state, syntaxNode, action);
    }

    public LR0ParseTableAction getRow(State state)
    {
        return this.rows.get(state);
    }

    public LR0ParseTableAction getCell(State state, ContextFreeGrammarSyntaxNode syntaxNode)
    {
        return this.cells.get(state, syntaxNode);
    }

    public boolean hasCellWithinRow(State state)
    {
        return this.cells.hasValueForFirstKey(state);
    }

    public ContextFreeGrammar getContextFreeGrammar()
    {
        return this.contextFreeGrammar;
    }

    public boolean rowsEqual(Map<State, LR0ParseTableAction> otherRows)
    {
        return this.rows.equals(otherRows);
    }

    public boolean rowsEqualOtherTable(LR0ParseTable otherTable)
    {
        return otherTable.rowsEqual(this.rows);
    }

    public boolean cellsEqual(PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherCells)
    {
        return this.cells.equals(otherCells);
    }

    public boolean cellsEqualOtherTable(LR0ParseTable otherTable)
    {
        return otherTable.cellsEqual(this.cells);
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ParseTable))
        {
            return false;
        }

        if (!this.contextFreeGrammar.equals(((LR0ParseTable)other).getContextFreeGrammar()))
        {
            return false;
        }

        return this.cellsEqualOtherTable((LR0ParseTable)other) && this.rowsEqualOtherTable((LR0ParseTable)other);
    }
}
