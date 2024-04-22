grammar cal;


prog: 				(decl_list function_list main) * EOF;							# progStm			

decl_list: 			decl SEMI decl_list |;											# declListStm					

decl: 				var_decl 														# varDeclRef
					| const_decl;													# constDeclRef			

var_decl: 			Variable ID COLON (NUMBER|Boolean);								# varDeclStm

const_decl: 		Constant ID COLON (NUMBER|Boolean) EQL expression;				# constDeclStm

function_list:  	function function_list |;										# funcListStm

function: 			type ID LBR parameter_list RBR
					CURLY_LBR 
					decl_list 
					statement_block 
					Return LBR (expression) RBR SEMI
    				CURLY_RBR														# funcDeclStm
    				;

type: 				NUMBER
					| Boolean
					| Void
					;

parameter_list: 	nemp_para_list |;												# nonEmptyParamRef

nemp_para_list: 	ID COLON type 													# singleParamStm
    						| ID COLON type COMMA nemp_para_list;					# multipleParamStm

main: 				Main CURLY_LBR
					decl_list
					statement_block
					CURLY_RBR														# mainStm
					;

statement_block: 	statement statement_block |;									# stmBlockRef

statement: 			<assoc=right> ID EQL expression SEMI							# assignStm
					| ID LBR arg_list RBR SEMI										# funcCallStm
					| CURLY_LBR statement_block CURLY_LBR							# beginStm
					| If condition CURLY_LBR statement_block CURLY_RBR				# conditionalStm
						Else CURLY_LBR statement_block CURLY_RBR
					| While condition CURLY_LBR statement_block CURLY_RBR			# whileStm
					| Skip SEMI														# skipStm
					;

expression: 		bit binary_arith_op bit 										# fragBinArithStm
    				| LBR expression CURLY_RBR										# fragUnaArithStm
    				| ID LBR arg_list CURLY_RBR										# funcCallExpr
    				| bit;															# fragRef

binary_arith_op: 	ADD																# additionStm
					| <assoc=right> MINUS 											# subtractionStm
					;

bit: 				ID 																# idFrag
					| MINUS ID 														# negationStm
					| NUMBER														# intStm
					| True 															# trueStm
					| False															# falseStm
					| LBR expression CURLY_RBR										# parenConditionalStm
					;

condition: 			<assoc=right> TILDE conditionalStm								# boolNegStm
    				| LBR condition RBR  											# parenConditionalStm
    				| expression comp_op expression									# boolArithStm
    				| condition (OR | AND) condition  								# boolEvalStm
    				;

comp_op: 			EQL 															# logEq
					| NEQL 															# logNEq
					| LESS 															# logLess
					| LESSEQL 														# logLessEq
					| GREATER 														# logGreat
					| GREATEREQL;													# logGreatEq

arg_list: 			nemp_arg_list |;												# nonEmptyArgListRef

nemp_arg_list: 		ID 																# idArgRef
					| ID COMMA nemp_arg_list;										# multiIdArgRef


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

fragment Letter:		[A-Za-z]+;
fragment Integer:		'0' | MINUS [1-9] ([0-9])* | [1-9] ([0-9]);
fragment UnderScore:	'_';

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
CURLY_LBR:			'{';
CURLY_RBR:			'}';
FWDS:				'/';

/* res words */

Variable:			V A R I A B L E;
Constant:			C O N S T A N T;
Return:				R E T U R N;
//Integer:			I N T E G E R;
Boolean:			B O O L E A N;
Void:				V O I D;
Main:				M A I N;
If:					I F;
Else:				E L S E;
True:				T R U E;
False:				F A L S E;
While:				W H I L E;
Begin:				B E G I N;
End:				E N D;
Is:					I S;
Skip:				S K I P;

NUMBER:			Integer;

ID: 			Letter (Letter | Integer | '_')*;

COMMENT: '/*' (COMMENT|.)*? '*/' -> skip;

LINE_COMMENT: '//' .*? '\n' -> skip;

WS: 			[ \t\n\r]+ -> skip;
