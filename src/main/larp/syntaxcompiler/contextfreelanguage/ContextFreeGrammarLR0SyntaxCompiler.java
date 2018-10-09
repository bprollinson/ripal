package larp.syntaxcompiler.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parser.regularlanguage.StateTransition;
import larp.parsetree.contextfreelanguage.ConcatenationNode;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.DotNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.ProductionNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.List;

public class ContextFreeGrammarLR0SyntaxCompiler
{
    private ContextFreeGrammarLR0ProductionSetDFACompiler DFACompiler;

    public ContextFreeGrammarLR0SyntaxCompiler()
    {
        this.DFACompiler = new ContextFreeGrammarLR0ProductionSetDFACompiler();
    }

    public LR0ParseTable compile(ContextFreeGrammar grammar) throws AmbiguousLR0ParseTableException
    {
        if (grammar.getStartSymbol() == null)
        {
            return new LR0ParseTable(grammar, null);
        }

        LR0ProductionSetDFA DFA = this.DFACompiler.compile(grammar);
        LR0ProductionSetDFAState startState = DFA.getStartState();
        LR0ParseTable parseTable = new LR0ParseTable(grammar, startState);

        this.processState(parseTable, startState);

        return parseTable;
    }

    private void processState(LR0ParseTable parseTable, LR0ProductionSetDFAState state) throws AmbiguousLR0ParseTableException
    {
        List<StateTransition<ContextFreeGrammarSyntaxNode,LR0ProductionSetDFAState>> stateTransitions = state.getTransitions();
        for (StateTransition<ContextFreeGrammarSyntaxNode,LR0ProductionSetDFAState> stateTransition: stateTransitions)
        {
            ContextFreeGrammarSyntaxNode input = stateTransition.getInput();
            LR0ProductionSetDFAState nextState = stateTransition.getNextState();

            if (input instanceof TerminalNode)
            {
                parseTable.addCell(state, input, new LR0ShiftAction(nextState));
            }
            if (input instanceof NonTerminalNode)
            {
                parseTable.addCell(state, input, new LR0GotoAction(nextState));
            }
            if (nextState.isAccepting())
            {
                parseTable.addCell(state, input, new LR0AcceptAction());
            }
            this.processReduceActions(parseTable, state);

            this.processState(parseTable, nextState);
        }
    }

    private void processReduceActions(LR0ParseTable parseTable, LR0ProductionSetDFAState state) throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = parseTable.getContextFreeGrammar();
        List<ContextFreeGrammarSyntaxNode> productions = cfg.getProductions();

        for (ContextFreeGrammarSyntaxNode production: productions)
        {
            ContextFreeGrammarSyntaxNode concatenationNode = production.getChildNodes().get(1);
            List<ContextFreeGrammarSyntaxNode> childNodes = concatenationNode.getChildNodes();
            int numChildNodes = childNodes.size();

            if (childNodes.get(numChildNodes - 1) instanceof DotNode)
            {
                int productionPosition = this.findProductionPosition(production, productions);
                if (productionPosition != -1)
                {
                    parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(productionPosition));
                }
            }
        }
    }

    private int findProductionPosition(ContextFreeGrammarSyntaxNode production, List<ContextFreeGrammarSyntaxNode> productions)
    {
        ContextFreeGrammarSyntaxNode originalProduction = this.removeDotFromProduction(production);

        return productions.indexOf(originalProduction);
    }

    private ContextFreeGrammarSyntaxNode removeDotFromProduction(ContextFreeGrammarSyntaxNode production)
    {
        ProductionNode newProduction = new ProductionNode();
        newProduction.addChild(production.getChildNodes().get(0));

        ConcatenationNode newConcatenationNode = new ConcatenationNode();
        ContextFreeGrammarSyntaxNode concatenationNode = production.getChildNodes().get(1);
        for (int i = 0; i < concatenationNode.getChildNodes().size() - 1; i++)
        {
            newConcatenationNode.addChild(concatenationNode.getChildNodes().get(i));
        }
        newProduction.addChild(newConcatenationNode);

        return newProduction;
    }
}
