lexer grammar SqlLexer;

// Keywords

ADD		: A D D ;
ALL		: A L L ;
ALTER	: A L T E R ;
AND		: A N D ;
AS		: A S ;
ASC		: A S C ;
BETWEEN	: B E T W E E N ;
BY		: B Y ;
CALL	: C A L L ;
CASE	: C A S E ;
CLOSE	: C L O S E ;
COLLATE	: C O L L A T E ;
COMMIT	: C O M M I T ;
CONCAT	: C O N C A T ;
CREATE	: C R E A T E ;
CURRENT	: C U R R E N T ;
CURSOR	: C U R S O R ;
DATA	: D A T A ;
DATE	: D A T E ;
DAY		: D A Y ;
DAYS	: D A Y S ;
DECLARE	: D E C L A R E ;
DELETE	: D E L E T E ;
DESC	: D E S C ;
DESCRIPTOR	: D E S C R I P T O R ;
DISTINCT	: D I S T I N C T ;
DROP	: D R O P ; 
ELSE	: E L S E ;
END		: E N D ;
EXECUTE	: E X E C U T E ;
EXISTS	: E X I S T S ;
EXP		: E X P ;
FETCH	: F E T C H ;
FIRST	: F I R S T ;
FOR		: F O R ;
FROM	: F R O M ;
GLOBAL	: G L O B A L ;
GROUP	: G R O U P ;
HAVING	: H A V I N G ;
IF		: I F ;
IMMEDIATE	: I M M E D I A T E ;
IN		: I N ;
INDEX	: I N D E X ;
INNER	: I N N E R ;
INSERT	: I N S E R T ;
INTO	: I N T O ;
IS		: I S ;
JOIN	: J O I N ;
KEY		: K E Y ;
LEFT	: L E F T ;
LIKE	: L I K E ;
LN		: L N ;
LOCATE	: L O C A T E ;
LOGGED	: L O G G E D ;
MAX		: M A X ;
MONTH	: M O N T H ;
NEXT	: N E X T ;
NEXTVAL	: N E X T V A L ;
NO		: N O ;
NOT		: N O T ;
NULL	: N U L L ;
OF		: O F ;
ON		: O N ;
ONLY	: O N L Y ;
OPEN	: O P E N ;
OPTIMIZE	: O P T I M I Z E ;
OPTION	: O P T I O N ;
OR		: O R ;
ORDER	: O R D E R ;
OUTER	: O U T E R ;
PREPARE	: P R E P A R E ;
PRESERVE	: P R E S E R V E ;
PRIMARY	: P R I M A R Y ;
READ	: R E A D ;
REPLACE	: R E P L A C E ;
RIGHT	: R I G H T ;
ROW		: R O W ;
ROWS	: R O W S ;
SELECT	: S E L E C T ;
SET		: S E T ;
//SP_IN	: S P '_' I N ;
//SP_INOUT	: S P '_' I N O U T ;
//SP_OUT	: S P '_' O U T ;
TABLE	: T A B L E ;
TEMPORARY	: T E M P ( O R A R Y )?;
THEN	: T H E N ;
TIMESTAMP	: T I M E S T A M P ;
TO		: T O ;
UNION	: U N I O N ;
UNIQUE	: U N I Q U E ;
UPDATE	: U P D A T E ;
UR		: U R ;
USING	: U S I N G ;
//VALUE	: V A L U E ;
VALUES	: V A L U E S ;
WHEN	: W H E N ;
WHERE	: W H E R E ;
WITH	: W I T H ;
WORK	: W O R K ;
YEAR	: Y E A R ;

INTEGER : DIGIT+ ;
DEC_PART : ( '.' | ',' ) DIGIT+ ;

STRING : '\'' ~( '\'' | '\r' | '\n' )* '\'' ;

COMMA       : ',' ;
POINT       : '.' ;
EQUALS      : '=' ;
NEQ         : '<>' ;
LESS        : '<' ;
GRT         : '>' ;
LESS_EQ     : '<=' ;
GRT_EQ      : '>=' ;
LOG_OR      : '||' ;
LOG_AND     : '&&' ;
CAST		: '::' ;
PLUS        : '+' ;
MINUS       : '-' ;
MULT        : '*' ;
SLASH       : '/' ;
COLON       : ':' ;
USCORE      : '_' ;
QUESTION    : '?' ;
LPAR        : '(' ;
RPAR        : ')' ;

IDENTIFIER : ( LETTER | DIGIT ) ( LETTER | DIGIT | '-' | '_' )* ;

RPG_IDENTIFIER : ( LETTER | DIGIT | '$' ) ( LETTER | DIGIT | '$' | '-' | '_' )* ;

DB2_CONSTANT : '*' ( LETTER | DIGIT ) ( LETTER | DIGIT | '-' | '_' )* ;

WHITESPACE : ( WS | NEWLINE )+  { setText(" "); } -> channel(HIDDEN) ;

COMMENT : ( '--' | '//' ) SAME_LINE* NEWLINE  { setText(" "); } -> channel(HIDDEN) ;

// Character groups

fragment WS : ' ' | '\t' | ';' ;
fragment NEWLINE : '\r'? '\n' ;
fragment SAME_LINE : ~( '\n' | '\r' ) ;

fragment LETTER : LOWER | UPPER ;
fragment LOWER : 'a' .. 'z' ;
fragment UPPER : 'A' .. 'Z' ;
fragment DIGIT : '0' .. '9' ;
fragment A : 'A' | 'a' ;
fragment B : 'B' | 'b' ;
fragment C : 'C' | 'c' ;
fragment D : 'D' | 'd' ;
fragment E : 'E' | 'e' ;
fragment F : 'F' | 'f' ;
fragment G : 'G' | 'g' ;
fragment H : 'H' | 'h' ;
fragment I : 'I' | 'i' ;
fragment J : 'J' | 'j' ;
fragment K : 'K' | 'k' ;
fragment L : 'L' | 'l' ;
fragment M : 'M' | 'm' ;
fragment N : 'N' | 'n' ;
fragment O : 'O' | 'o' ;
fragment P : 'P' | 'p' ;
fragment Q : 'Q' | 'q' ;
fragment R : 'R' | 'r' ;
fragment S : 'S' | 's' ;
fragment T : 'T' | 't' ;
fragment U : 'U' | 'u' ;
fragment V : 'V' | 'v' ;
fragment W : 'W' | 'w' ;
fragment X : 'X' | 'x' ;
fragment Y : 'Y' | 'y' ;
fragment Z : 'Z' | 'z' ;
