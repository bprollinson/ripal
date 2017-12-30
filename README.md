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

### Step 2: Convert Token List to Parse Tree

### Step 3: Convert Parse Tree to Epsilon-NFA

### Step 4: Convert Epsilon-NFA to NFA

### Step 5: Convert NFA to DFA

### Step 6: Feed String to DFA

## Context-Free Languages

### Step 1: Tokenize Context-Free Grammar

### Step 2: Convert Token List to Parse Tree

### Step 3: Convert Parse Tree into Parse Table

### Step 4: Attempt to Match String using Parse Table
