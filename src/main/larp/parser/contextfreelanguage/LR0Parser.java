package larp.parser.contextfreelanguage;

import larp.ComparableStructure;
import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class LR0Parser implements ContextFreeGrammarParser, ComparableStructure
{
    private LR0ParseTable parseTable;
    private ContextFreeGrammar grammar;
    private List<Integer> appliedRules;

    public LR0Parser(LR0ParseTable parseTable)
    {
        this.parseTable = parseTable;
        this.grammar = parseTable.getContextFreeGrammar();
        this.appliedRules = new ArrayList<Integer>();
    }

    public LR0ParseTable getParseTable()
    {
        return this.parseTable;
    }

    public boolean accepts(String inputString)
    {
        this.appliedRules = new ArrayList<Integer>();

        State currentState = this.parseTable.getStartState();

        Vector<Object> stack = new Vector<Object>();
        stack.add(currentState);

        while (true)
        {
            ContextFreeGrammarSyntaxNode characterNode = this.calculateCharacterNode(inputString, stack);
            LR0ParseTableAction action = this.parseTable.getCell(currentState, characterNode);

            if (action == null)
            {
                return false;
            }
            if (action instanceof LR0AcceptAction)
            {
                return true;
            }
            if (action instanceof LR0ShiftAction)
            {
                inputString = inputString.substring(1);
                stack.add(characterNode);
            }
            if (action instanceof LR0ReduceAction)
            {
                this.applyReduction((LR0ReduceAction)action, stack);
            }

            State nextState = action.getNextState();
            if (nextState != null)
            {
                currentState = nextState;
                stack.add(nextState);
            }
            else
            {
                currentState = this.findTopStackState(stack);
            }
        }
    }

    private ContextFreeGrammarSyntaxNode calculateCharacterNode(String inputString, Vector<Object> stack)
    {
        if (stack.elementAt(stack.size() - 1) instanceof ContextFreeGrammarSyntaxNode)
        {
            return (ContextFreeGrammarSyntaxNode)stack.elementAt(stack.size() - 1);
        }
        if (inputString.length() > 0)
        {
            String nextCharacter = inputString.substring(0, 1);
            return new TerminalNode(nextCharacter);
        }

        return new EndOfStringNode();
    }

    private void applyReduction(LR0ReduceAction action, Vector<Object> stack)
    {
        int productionIndex = ((LR0ReduceAction)action).getProductionIndex();

        this.appliedRules.add(productionIndex - 1);

        ContextFreeGrammarSyntaxNode rootNode = this.grammar.getProduction(productionIndex);
        ContextFreeGrammarSyntaxNode leftHandNode = rootNode.getChildNodes().get(0);
        List<ContextFreeGrammarSyntaxNode> rightHandNodes = rootNode.getChildNodes().get(1).getChildNodes();
        int reduceSize = this.calculateReduceSize(rightHandNodes);

        for (int i = 0; i < reduceSize; i++)
        {
            stack.remove(stack.size() - 1);
        }

        stack.add(leftHandNode);
    }

    private State findTopStackState(Vector<Object> stack)
    {
        for (int i = stack.size() - 1; i >= 0; i--)
        {
            Object stackEntry = stack.get(i);
            if (stackEntry instanceof State)
            {
                return (State)stackEntry;
            }
        }

        return null;
    }

    private int calculateReduceSize(List<ContextFreeGrammarSyntaxNode> rightHandNodes)
    {
        if (rightHandNodes.get(0) instanceof EpsilonNode)
        {
            return 0;
        }

        return 2 * rightHandNodes.size();
    }

    public List<Integer> getAppliedRules()
    {
        List<Integer> appliedRulesReversed = new ArrayList<Integer>(this.appliedRules);
        Collections.reverse(appliedRulesReversed);

        return appliedRulesReversed;
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
