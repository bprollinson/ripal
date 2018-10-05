package larp.parser.contextfreelanguage;

import larp.ComparableStructure;
import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.util.PairToValueMap;

import java.util.ArrayList;
import java.util.List;
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

        if (this.startState != ((LR0ParseTable)other).getStartState())
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

        List<State> ourCoveredStates = new ArrayList<State>();
        List<State> otherCoveredStates = new ArrayList<State>();

        return this.buildCellComparator().equalsCell(this, this.getStartState(), otherTable, otherTable.getStartState(), ourCoveredStates, otherCoveredStates);
    }

    protected LR0ParseTableCellComparator buildCellComparator()
    {
        return new LR0ParseTableCellComparator();
    }

    protected class LR0ParseTableCellComparator
    {
        public boolean equalsCell(LR0ParseTable table, State startState, LR0ParseTable otherTable, State otherStartState, List<State> ourCoveredStates, List<State> otherCoveredStates)
        {
            int ourStatePosition = ourCoveredStates.indexOf(startState);
            if (ourStatePosition != -1)
            {
                int otherStatePosition = otherCoveredStates.indexOf(otherStartState);

                return ourStatePosition == otherStatePosition;
            }

            ourCoveredStates.add(startState);
            otherCoveredStates.add(otherStartState);

            PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> ourCells = table.getCells();
            Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> ourMap = ourCells.getMap();
            Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> ourRow = ourMap.get(startState);

            PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherCells = otherTable.getCells();
            Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> otherMap = otherCells.getMap();
            Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherRow = otherMap.get(otherStartState);

            if (ourRow == null)
            {
                return otherRow == null;
            }
            if (!this.rowsHaveSameSize(ourRow, otherRow))
            {
                return false;
            }

            for (Map.Entry<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> ourCell: ourRow.entrySet())
            {
                ContextFreeGrammarSyntaxNode node = ourCell.getKey();
                LR0ParseTableAction ourAction = ourCell.getValue();
                LR0ParseTableAction otherAction = null;
                if (otherRow != null)
                {
                    otherAction = otherRow.get(node);
                }

                if (!this.actionClassesEqual(ourAction, otherAction))
                {
                    return false;
                }
                if (!this.actionInstancesEqual(table, ourAction, otherTable, otherAction, ourCoveredStates, otherCoveredStates))
                {
                    return false;
                }
            }

            return true;
        }

        private boolean rowsHaveSameSize(Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> ourRow, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherRow)
        {
            if (otherRow == null)
            {
                return false;
            }

            return ourRow.size() == otherRow.size();
        }

        private boolean actionClassesEqual(Object object1, Object object2)
        {
            if (object2 == null)
            {
                return false;
            }

            return object1.getClass().equals(object2.getClass());
        }

        private boolean actionInstancesEqual(LR0ParseTable table, LR0ParseTableAction ourAction, LR0ParseTable otherTable, LR0ParseTableAction otherAction, List<State> ourCoveredStates, List<State> otherCoveredStates)
        {
            if (ourAction.supportsTransition())
            {
                return this.equalsCell(table, ourAction.getNextState(), otherTable, otherAction.getNextState(), ourCoveredStates, otherCoveredStates);
            }

            return ourAction.equals(otherAction);
        }
    }
}
