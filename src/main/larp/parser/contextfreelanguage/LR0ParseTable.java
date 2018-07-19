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
    private PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> cells;

    public LR0ParseTable(ContextFreeGrammar contextFreeGrammar)
    {
        this.contextFreeGrammar = contextFreeGrammar;
        this.cells = new PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
    }

    public void addCell(State state, ContextFreeGrammarSyntaxNode syntaxNode, LR0ParseTableAction action) throws AmbiguousLR0ParseTableException
    {
        new LR0ParseTableCellAvailableAssertion(this, state, syntaxNode, action).validate();

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

        List<Map.Entry<State, ContextFreeGrammarSyntaxNode>> ourCoveredInputs = new ArrayList<Map.Entry<State, ContextFreeGrammarSyntaxNode>>();
        List<Map.Entry<State, ContextFreeGrammarSyntaxNode>> otherCoveredInputs = new ArrayList<Map.Entry<State, ContextFreeGrammarSyntaxNode>>();

        return new LR0ParseTableComparator().equalsTable(this, otherTable, ourCoveredInputs, otherCoveredInputs);
    }

    protected class LR0ParseTableComparator
    {
        public boolean equalsTable(LR0ParseTable table, LR0ParseTable otherTable, List<Map.Entry<State, ContextFreeGrammarSyntaxNode>> ourCoveredInputs, List<Map.Entry<State, ContextFreeGrammarSyntaxNode>> otherCoveredInputs)
        {
            PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> ourCells = table.getCells();
            Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> ourMap = ourCells.getMap();

            PairToValueMap<State, ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherCells = otherTable.getCells();
            Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> otherMap = otherCells.getMap();

            for (Map.Entry<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> ourEntry: ourMap.entrySet())
            {
                boolean found = false;
                Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> ourValue = ourEntry.getValue();

                for (Map.Entry<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> otherEntry: otherMap.entrySet())
                {
                    Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> otherValue = otherEntry.getValue();

                    if (ourValue.equals(otherValue))
                    {
                        found = true;
                        break;
                    }
                }

                if (!found)
                {
                    return false;
                }
            }

            return true;
        }
    }
}
