grammar cal;

prog: 				decl_list function_list main;

decl_list: 			decl SEMI decl_list |;

decl: 				var_decl | const_decl;

var_decl: 			Variable ID COLON type;

const_decl: 		Constant ID COLON type ASSIGN expression;

function_list:  	function function_list |;

function: 			type ID LBR parameter_list RBR
					Is decl_list 
					Begin 
					statement_block 
					Return LBR expression
    				| RBR SEMI 
    				End;

type: 				TypeInteger | TypeBoolean | TypeVoid;

parameter_list: 	nemp_parameter_list |;

nemp_para_list: 	ID COLON type 
    						| ID COLON type COMMA nemp_para_list;

main: 				Main Begin decl_list statement_block End;

statement_block: 	LBR statement statement_block RBR |;

statement: 			ID ASSIGN expression SEMI 
    						| ID LBR arg_list RBR SEMI
   							| Begin statement_block End
    						| If condition Begin statement_block End Else Begin statement_block End
    						| While condition Begin statement_block End
    						| Skip SEMI;

expression: 		bit binary_arith_op bit 
    						| LBR expression RBR
    						| ID LBR arg_list RBR
    						| bit;

binary_arith_op: 	PLUS | MINUS;

bit: 				ID | MINUS ID | Digit | True | False;

condition: 			TILDE condition
    						| LBR condition RBR
    						| expression comp_op expression
    						| condition LBR OR OR AND RBR condition;

comp_op: 			EQUAL | NOTEQUAL | LESS | LESSEQUAL | GREATER | GREATEREQUAL;

arg_list: 			nemp_arg_list |;

nemp_arg_list: 		ID | ID COMMA nemp_arg_list;


fragment A: 		'a' | 'A';
fragment B: 		'b' | 'B';
fragment C: 		'c' | 'C';
fragment D: 		'd' | 'D';
fragment E: 		'e' | 'E';
fragment F: 		'f' | 'F';
fragment G: 		'g' | 'G';
fragment H: 		'h' | 'H';
fragment I: 		'i' | 'I';
fragment J: 		'j' | 'J';
fragment K: 		'k' | 'K';
fragment L: 		'l' | 'L';
fragment M: 		'm' | 'M';
fragment N: 		'n' | 'N';
fragment O: 		'o' | 'O';
fragment P: 		'p' | 'P';
fragment Q: 		'q' | 'Q';
fragment R: 		'r' | 'R';
fragment S: 		's' | 'S';
fragment T: 		't' | 'T';
fragment U: 		'u' | 'U';
fragment V: 		'v' | 'V';
fragment W: 		'w' | 'W';
fragment X: 		'x' | 'X';
fragment Y: 		'y' | 'Y';
fragment Z: 		'z' | 'Z';

/* tokens */

COLON:				':';
SEMI:				';';
COMMA:				',';
AND:				'&';
OR:					'|';
MINUS:				'-';
ADD:				'+';
EQL:				'=';
NEQL:				'!=';
GREATER:			'>';
LESS:				'<';
GREATEREQL:			'>=';
LESSEQL:			'<=';
TILDE:				'~';
ASSIGN:				':=';
LBR:				'(';
RBR:				')';
FWDS:				'/';
COMMENT:			'//';
MLINECOMM:			'/*';

/* res words */

Variable:			VARIABLE;
Constant:			CONSTANT;
Return:				RETURN;
Integer:			INTEGER;
Boolean:			BOOLEAN;
Void:				VOID;
Main:				MAIN;
If:					IF;
Else:				ELSE;
True:				TRUE;
False:				FALSE;
While:				WHILE;
Begin:				BEGIN;
End:				END;
Is:					IS;
Skip:				SKIP;

fragment Letter:		[A-Za-z]+;
//fragment Digit:		[0-9];
fragment UnderScore:	'_';


Digit: 			[0-9];

Integer: 		(MINUS? [1-9] Digit*) | '0'+;

Letter: 		[a-zA-Z]+;

ID: 			Letter (Letter | Digit | '_')*;

Comment: '/*' (COMMENT|.)*? '*/' -> skip;

Line_Comment: '//' .*? '\n' -> skip;

WS: 			[ \t\n\r]+ -> skip;
