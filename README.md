# LARP: Language Analysis and Responsive Parsing

## Regular Languages

### Step 1: Tokenize Regular Expression

* Initialize an empty list of tokens found
* Initialize parenthesis depth as 0
* For each character in the regular expression
  * If the character is an open parenthesis, increment parenthesis depth
  * If the character is a close parenthesis, decrement parenthesis depth
  * If parenthesis depth is negative, fail
  * If the current character is a kleene closure or an or symbol and the token list is empty, append epsilon to the token list
  * If the current character is a close parenthesis, kleene closure or and or symbol and the last token was an open parenthesis or an or token, append epsilon to the token list
  * If the character is a an open parenthesis, close parenthesis, kleene closure, or symbol or character, add it to the list
* If parenthesis depth is not zero, fail
* If the token list contains no tokens or the last token was an or symbol, append epsilon to the token list
* Return the token list

### Step 2: Convert Token List to Parse Tree

* If there is at least one or token at the top level (not within parentheses)
  * Create a concatenation node
  * For each segment of the token list separated by top-level or tokens (excluding the or token), perform the conversion recursively, appending the result as a child of the concatenation node
  * Return the concatenation node
* Otherwise, create a concatenation node
* Initialize parenthesis depth as 0
* For each token in the list
  * If the token is an open parenthesis, increment parenthesis depth
  * If the token is a close parenthesis, decrement parenthesis depth
  * If the token is a character and parenthesis depth is 0, append a character node as a child of the concatenation node
  * If the token is a kleene closure symbol and parenthesis depth is 0, re-parent the previous child as a child of a new kleene closure node
  * If the token is a close parenthesis token which brings parenthesis depth back to 0, perform the conversion recursively on the contents of the parentheses, appending the result as the child of a concatenation node
* Return the concatenation node

### Step 3: Convert Parse Tree to Epsilon-NFA

* Build the state group (Epsilon-NFA with tracked first and last states) from the root node of the parse tree as follows
  * If the parse tree node is a concatenation node, build state groups for all child nodes, then insert epsilon transitions between the end state of each and the start state of the next
  * If the parse tree node is a character node, the state group has two-node Epsilon-NFA with a transition between the states matching the character from the node
  * If the parse tree node is a kleene closure node, build the child node state group, then add epsilon transitions in both directions between the resulting start and end states
  * If the parse tree node is a concatenation node, build state groups for all child nodes, then insert epsilon transitions from a new state state to each group's start state and from each group's end state to a new end state
* Return an Epsilon-NFA with a start state matching that of the top-level state group, setting the corresponding end state to accepting

### Step 4: Convert Epsilon-NFA to NFA

### Step 5: Convert NFA to DFA

### Step 6: Feed String to DFA

## Context-Free Languages

### Step 1: Tokenize Context-Free Grammar

### Step 2: Convert Token List to Parse Tree

### Step 3: Convert Parse Tree into Parse Table

### Step 4: Attempt to Match String using Parse Table
