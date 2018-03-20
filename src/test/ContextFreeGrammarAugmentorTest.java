import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarAugmentor;

public class ContextFreeGrammarAugmentorTest
{
    @Test
    public void testAugmentAddsNewStartStateToValidGrammar()
    {
        ContextFreeGrammarAugmentor augmentor = new ContextFreeGrammarAugmentor();

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        cfg.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        ContextFreeGrammar expectedGrammar = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"));
        cfg.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        cfg.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        assertEquals(expectedGrammar, augmentor.augment(cfg));
    }
}
