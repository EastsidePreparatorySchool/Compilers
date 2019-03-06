grammar LOLcode;

@header {
   package lol;
}

fragment OBTW: 'obtw'|'OBTW';
fragment BTW: 'btw' | 'BTW';
fragment TLDR: 'tldr' | 'TLDR';
MULTILINECOMMENT: OBTW .*? TLDR -> channel(HIDDEN);
SINGLELINECOMMENT: BTW ~[\r\n\u2028\u2029]* -> channel(HIDDEN);


fragment DIGIT_NO_ZERO: '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9';
fragment DIGIT: DIGIT_NO_ZERO | '0';
MINUS: '-';
DOT:'.';

fragment ID_START: [a-zA-Z_];
fragment ID_CHAR: [a-zA-Z0-9_];

fragment NATURAL_NUMBER: DIGIT_NO_ZERO DIGIT*;
fragment NATURAL_NUMBER_OR_ZERO: '0' | NATURAL_NUMBER;
INTEGER: MINUS? NATURAL_NUMBER_OR_ZERO;
FLOAT: MINUS? NATURAL_NUMBER_OR_ZERO DOT NATURAL_NUMBER_OR_ZERO?;

fragment QUOTE: '"';
STRING: QUOTE .*? QUOTE;

WIN: 'win' | 'WIN';
FAIL: 'fail' | 'FAIL';
BOOLEAN: WIN|FAIL;

NUMBR: 'numbr'|'NUMBR';
NUMBAR: 'numbar'|'NUMBAR';
YARN: 'yarn' |'YARN';
INTO: 'into' |'INTO';
SUM: 'sum' | 'SUM';
DIFF: 'diff' | 'DIFF';
PRODUKT: 'produkt' | 'PRODUKT';
QUOSHUNT: 'quoshunt' | 'QUOSHUNT';
MOD: 'mod' | 'MOD';
BIGGR: 'biggr' | 'BIGGR';
SMALLR: 'smallr' | 'SMALLR';
IZ: 'iz'|'IZ';
MAEK: 'maek'|'MAEK';
THAN: 'than' | 'THAN';
AN: 'an' | 'AN';
OF: 'of' | 'OF';
IT: 'it' | 'IT';
MKAY: 'mkay' | 'MKAY';
SMOOSH: 'smoosh'|'SMOOSH';
TROOF: 'troof' | 'TROOF';
NOOB: 'noob' | 'NOOB';
BOTH: 'both' | 'BOTH';
EITHER: 'either' | 'EITHER';
ANY: 'any' | 'ANY';
ALL: 'all' | 'ALL';
WON: 'won' | 'WON';
NOT: 'not' | 'NOT';
SAEM: 'saem' | 'SAEM';
DIFFRINT: 'diffrint' | 'DIFFRINT';
I: 'i' | 'I';
HAS: 'has' | 'HAS';
A: 'a' | 'A';
R: 'r' | 'R';
ITZ: 'itz' | 'ITZ';
HOW: 'how' | 'HOW';
DUZ: 'duz' | 'DUZ';
YR: 'yr' | 'YR';
IF: 'if' | 'IF';
U: 'u' | 'U';
SAY: 'say' | 'SAY';
SO: 'so' | 'SO';
IM: 'i\'m' | 'I\'M' | 'I\'m';
IN: 'in' | 'IN';
OUTTA: 'outta' | 'OUTTA';
TIL: 'til' | 'TIL';
WILE: 'wile' | 'WILE';
UPPIN: 'uppin' | 'UPPIN';
NERFIN: 'nerfin' | 'NERFIN';
WTF:'wtf' | 'WTF';
QUESTION: '?';
OIC: 'oic' | 'OIC';
OMG: 'omg' | 'OMG';
OMGWTF: 'omgwtf' | 'OMGWTF';
FOUND: 'found' | 'FOUND';
GTFO: 'gtfo' | 'GTFO';
O: 'o' | 'O';
RLY: 'rly' | 'RLY';
YA: 'ya' | 'YA';
NO: 'no' | 'NO';
WAI: 'wai' | 'WAI';
MEBBE: 'mebbe' | 'MEBBE';
GIMMEH: 'gimmeh'|'GIMMEH';
INVISIBLE: 'invisible'|'INVISIBLE';
VISIBLE: 'visible'|'VISIBLE';
EXCLAMATION: '!';
HAI: 'hai' | 'HAI';
KTHXBYE: 'kthxbye' | 'KTHXBYE';


COMMA: ',';
NEWLINE: '\n';
IDENTIFIER: ID_START ID_CHAR*;
ELLIPSIS: '...\n' -> skip;
WS   : [ \r\t] -> skip;
   



literal_value: INTEGER|FLOAT|STRING|BOOLEAN;
atom: literal_value | IT | IDENTIFIER;
vartype: NUMBR | NUMBAR | YARN | TROOF |NOOB;



// expressions

foldable_arg:expr; // need this to allow constant folding, naked_arg, on the other hand, cannot be folded
sum: SUM OF foldable_arg AN foldable_arg;
diff:  DIFF OF foldable_arg AN foldable_arg;
product:   PRODUKT OF foldable_arg AN foldable_arg ;
quotient:   QUOSHUNT OF naked_arg AN naked_arg ;
bigger: IZ BIGGR naked_arg THAN naked_arg;
smallr: IZ SMALLR naked_arg THAN naked_arg;
min: SMALLR OF naked_arg AN naked_arg;
max: BIGGR OF naked_arg AN naked_arg;
mod:   MOD OF naked_arg AN naked_arg ;
not:   NOT naked_arg ;
same:    BOTH (SAEM | DIFFRINT) naked_arg AN naked_arg ;
and:   BOTH OF naked_arg AN naked_arg ;
either:   EITHER OF naked_arg AN naked_arg ;
one:   WON OF naked_arg AN naked_arg ;
all:   ALL OF naked_arg (AN naked_arg)* MKAY;
any:   ANY naked_arg (AN naked_arg)* MKAY;
concat: SMOOSH naked_arg ((AN|COMMA) naked_arg)* MKAY;
cast: MAEK naked_arg A vartype;
var_ref: IDENTIFIER;


expr: sum |
   diff |
   product |
   quotient |
   mod |
   not |
   same |
   and |
   either |
   one |
   all |
   any |
   concat |
   cast |
   var_ref |
   atom;



// variables and assignment
var_decl: I HAS A vartype IDENTIFIER (ITZ expr)?;
var_rvalue: IDENTIFIER;
var_assignment: var_rvalue R expr;


// function declaration
arg_decl: YR vartype IDENTIFIER;
more_args_decl: (AN arg_decl)* ;
args_decl: arg_decl more_args_decl;
return_type: INTO vartype;
func_decl: HOW (IZ|DUZ) I IDENTIFIER args_decl? return_type? separator+ (block separator+)? IF U SAY SO;

// function return
return_statement: (FOUND YR expr) | GTFO;

//function call
naked_arg: expr;
arg: YR naked_arg;
more_args: (AN arg)* MKAY;
args: arg more_args?;
func_call: I (DUZ|IZ) IDENTIFIER args?;

// loops


loop_action: (UPPIN | NERFIN) YR var_rvalue;
loop_condition: (TIL|WILE) expr;
loop_end: IM OUTTA YR;
loop: IM IN YR IDENTIFIER loop_action loop_condition separator+ (block separator+)? loop_end IDENTIFIER;

// switch / case

aswitch: WTF QUESTION separator+ (acase separator+)+ (adefault separator+)? OIC;
acase: OMG literal_value separator+ block;
adefault: OMGWTF separator+ block;


// if / then / else


anif: O RLY QUESTION;
then: YA RLY separator+ block;
elseif: MEBBE expr separator+ block;
anelse: NO WAI separator+ block;
ifthenelseifelse: anif separator+ then (separator+ elseif)* (separator+ anelse)? separator+  OIC;

// input / output. have arguments explcitily called out so we can convert to string in code generation phase

input: GIMMEH var_rvalue;
output_arg: expr;
output_args: output_arg? ((COMMA output_arg)* MKAY)?;
output: VISIBLE output_args EXCLAMATION?;




// statments and blocks

statement_affecting_it:
   var_assignment | 
   var_decl |
   input |
   func_call |
   output |
   expr 
;

core_statement:
   statement_affecting_it |
   loop |
   ifthenelseifelse |
   aswitch 
;

inner_statement: 
   core_statement |
   return_statement |
   GTFO;

top_level_statement: 
   core_statement |
   func_decl
;

separator: COMMA | NEWLINE;

block: (inner_statement (separator+ inner_statement )*)?;
top_level_block: (top_level_statement (separator+ top_level_statement)*)?;

// program block
program: HAI separator+ top_level_block separator+ KTHXBYE;


