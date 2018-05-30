package larp.parser.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.HashMap;
import java.util.Map;

public class LR0ParseTable
{
    private ContextFreeGrammar contextFreeGrammar;
    private Map<State, LR0ParseTableAction> rows;
    private Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> table;

    public LR0ParseTable(ContextFreeGrammar contextFreeGrammar)
    {
        this.contextFreeGrammar = contextFreeGrammar;
        this.rows = new HashMap<State, LR0ParseTableAction>();
        this.table = new HashMap<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>>();
    }

    public void addCell(State state, ContextFreeGrammarSyntaxNode syntaxNode, LR0ParseTableAction action) throws AmbiguousLR0ParseTableException
    {
        new LR0ParseTableCellAvailableAssertion(this, state, syntaxNode, action).validate();

        if (action.isRowLevelAction())
        {
            this.rows.put(state, action);
        }
        else
        {
            Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> entry = this.table.get(state);
            if (entry == null)
            {
                entry = new HashMap<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
            }

            entry.put(syntaxNode, action);
            this.table.put(state, entry);
        }
    }

    public LR0ParseTableAction getRow(State state)
    {
        return this.rows.get(state);
    }

    public LR0ParseTableAction getCell(State state, ContextFreeGrammarSyntaxNode syntaxNode)
    {
        LR0ParseTableAction action = null;
        Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> entry = this.table.get(state);
        if (entry != null)
        {
            action = entry.get(syntaxNode);
        }

        return action;
    }

    public boolean hasCellWithinRow(State state)
    {
        return this.table.get(state) != null;
    }

    public ContextFreeGrammar getContextFreeGrammar()
    {
        return this.contextFreeGrammar;
    }

    public boolean cellsEqual(Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> otherCells)
    {
        return this.table.equals(otherCells);
    }

    public boolean rowsEqual(Map<State, LR0ParseTableAction> otherRows)
    {
        return this.rows.equals(otherRows);
    }

    public boolean cellsEqualOtherTable(LR0ParseTable otherTable)
    {
        return otherTable.cellsEqual(this.table);
    }

    public boolean rowsEqualOtherTable(LR0ParseTable otherTable)
    {
        return otherTable.rowsEqual(this.rows);
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
