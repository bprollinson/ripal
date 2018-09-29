package larp.parser.contextfreelanguage;

import larp.ComparableStructure;
import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.util.PairToValueMap;

import java.util.Map;

public class LR0ParseTable implements ComparableStructure
{
    private ContextFreeGrammar contextFreeGrammar;
    private State startState;
    private int size;
    private PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> cells;

    public LR0ParseTable(ContextFreeGrammar contextFreeGrammar, State startState)
    {
        this.contextFreeGrammar = contextFreeGrammar;
        this.startState = startState;
        this.size = 0;
        this.cells = new PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
    }

    public State getStartState()
    {
        return this.startState;
    }

    public void addCell(State state, ContextFreeGrammarSyntaxNode syntaxNode, LR0ParseTableAction action) throws AmbiguousLR0ParseTableException
    {
        new LR0ParseTableCellAvailableAssertion(this, state, syntaxNode, action).validate();

        this.size++;
        this.cells.put(state, syntaxNode, action);
    }

    public LR0ParseTableAction getCell(State state, ContextFreeGrammarSyntaxNode syntaxNode)
    {
        return this.cells.get(state, syntaxNode);
    }

    public ContextFreeGrammar getContextFreeGrammar()
    {
        return this.contextFreeGrammar;
    }

    public int size()
    {
        return this.size;
    }

    public PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> getCells()
    {
        return this.cells;
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

        return this.cells.equals(((LR0ParseTable)other).getCells());
    }

    public boolean structureEquals(Object other)
    {
        if (!(other instanceof LR0ParseTable))
        {
            return false;
        }

        LR0ParseTable otherTable = (LR0ParseTable)other;
        if (!this.contextFreeGrammar.equals(otherTable.getContextFreeGrammar()))
        {
            return false;
        }

        return this.buildCellComparator().equalsCell(this, this.getStartState(), otherTable, otherTable.getStartState());
    }

    protected LR0ParseTableCellComparator buildCellComparator()
    {
        return new LR0ParseTableCellComparator();
    }

    protected class LR0ParseTableCellComparator
    {
        public boolean equalsCell(LR0ParseTable table, State startState, LR0ParseTable otherTable, State otherStartState)
        {
            PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> ourCells = table.getCells();
            Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> ourMap = ourCells.getMap();

            PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherCells = otherTable.getCells();
            Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> otherMap = otherCells.getMap();

            for (Map.Entry<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> ourRow: ourMap.entrySet())
            {
                State ourState = ourRow.getKey();

                for (Map.Entry<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> ourColumn: ourRow.getValue().entrySet())
                {
                    ContextFreeGrammarSyntaxNode ourNode = ourColumn.getKey();
                    LR0ParseTableAction ourAction = ourColumn.getValue();
                }
            }

            return true;
        }
    }
}
