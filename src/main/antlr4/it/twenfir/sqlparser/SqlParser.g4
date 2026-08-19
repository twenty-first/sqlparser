parser grammar SqlParser;

options
{
	tokenVocab = SqlLexer ;
}

statement :
    ( alterTableStatement
//    | callStatement
    | closeStatement
    | commitStatement
    | connectStatement
    | createIndexStatement
    | createTableStatement
    | createSequenceStatement
    | declareCursorStatement
    | declareTempTableStatement
    | deleteStatement
    | disconnectStatement
    | dropAliasStatement
    | dropIndexStatement
    | dropTableStatement
    | executeStatement
    | fetchStatement
    | getDiagnosticsStatement
    | insertStatement
    | labelStatement
    | openStatement
    | prepareStatement
    | selectStatement
    | setStatement
    | setOptionStatement
    | truncateStatement
    | updateStatement
    | valuesStatement
//    | catchAll
    )
    ;

selectStatement :
    ( WITH localTableDefinition ( COMMA localTableDefinition )* )? 
    selectExpression //( selectModifier )*
    isolationClause?
    ;

isolationClause :
    WITH ( UR | NC )
    ;

selectExpression :
    simpleSelect ( UNION ALL? ( simpleSelect | LPAR simpleSelect RPAR ) )*
    ;

simpleSelect :
    SELECT ( ALL | DISTINCT )? selectColumn ( COMMA selectColumn )*
    intoClause?
    fromClause?
    whereClause?
    ( GROUP BY orderingTerm ( COMMA orderingTerm )* ( HAVING expression )? )?
    orderByClause?
    ( FOR ( FETCH | READ ) ONLY
    | OPTIMIZE FOR INTEGER ( ROW | ROWS )
    | FETCH FIRST (INTEGER | inputParameter)? ( ROW | ROWS ) ONLY 
    )*
	;

orderByClause :
    ORDER BY orderingTerm ( COMMA orderingTerm )*
    ;
    
insertStatement :
    INSERT INTO table ( LPAR identifier ( COMMA identifier )* RPAR )? insertRowsClause?
    ( insertValuesClause
    | LPAR selectStatement RPAR
    | selectStatement
    )
    isolationClause?
    ;

insertRowsClause :
    simpleInputParameter ROWS
    ;

insertValuesClause :
    VALUES 
    ( combinedInputParameter
    | LPAR 
      ( combinedInputParameter
      | expression 
      )
      ( COMMA expression )*
      RPAR
    )
    ;

updateStatement :
    UPDATE table identifier?
    SET ( ROW | identifier ( POINT identifier )? | LPAR identifier ( POINT identifier )? ( COMMA identifier ( POINT identifier )? )* RPAR ) EQUALS expression 
    ( COMMA ( identifier ( POINT identifier )? | LPAR identifier ( POINT identifier )? RPAR ) EQUALS expression )*
    whereClause?
    ;

deleteStatement :
    DELETE FROM? table whereClause?
    isolationClause?
    ;

dropAliasStatement :
    DROP ALIAS table
    ;
    
dropTableStatement :
	DROP TABLE ( IF EXISTS )? table
	;

dropIndexStatement : 
    DROP INDEX index
    ;

truncateStatement :
    TRUNCATE TABLE? ONLY? table
    ;
    	
setStatement :
    SET ( setTarget | LPAR setTarget ( COMMA setTarget )* RPAR ) EQUALS expression
    ;

setTarget :
    identifier | combinedOutputParameter
    ;
    
valuesStatement :
	VALUES expression ( COMMA expression )* intoClause?
	;
	
fetchStatement :
    FETCH ( FIRST | NEXT )? FROM? identifier ( forClause )? intoClause?
    ;

forClause :
    FOR ( INTEGER | simpleInputParameter ) ROWS
    ;
    
intoClause :
    INTO combinedOutputParameter ( COMMA combinedOutputParameter )*
    ;

fromClause :
    FROM joinSource ( COMMA joinSource )*
    ;

getDiagnosticsStatement :
    GET DIAGNOSTICS simpleOutputParameter EQUALS ( DB2_NUMBER_ROWS | ROW_COUNT )
    ;
    
executeStatement :
    EXECUTE IMMEDIATE? ( identifier | simpleInputParameter ) usingClause?
    ;
        
openStatement :
    OPEN identifier usingClause?
    ;

usingClause :
    USING 
    ( combinedInputParameter ( COMMA combinedInputParameter )* 
    | DESCRIPTOR parameter parameter? 
    )
    ;

declareCursorStatement :
    DECLARE ( ( SCROLL | INSENSITIVE )* CURSOR name = identifier | name = identifier ( SCROLL | INSENSITIVE )* CURSOR ) FOR
    ( stmt = identifier
    | selectStatement
    )
    ( FOR READ ONLY | updateClause )?
    ;
    
updateClause :
    FOR UPDATE ( OF identifier ( COMMA identifier )* )?
    ;

prepareStatement : 
    PREPARE identifier FROM simpleInputParameter
    ;
    
closeStatement :
    CLOSE identifier
    ;

connectStatement :
    CONNECT RESET
    ;

disconnectStatement :
    DISCONNECT CURRENT
    ;
    
declareTempTableStatement :
	DECLARE GLOBAL TEMPORARY TABLE table
	( LIKE table
	| AS ( selectStatement | LPAR selectStatement RPAR )
	| tableDefinition
	)
	temporaryTableOption*
	;

tableDefinition :
    LPAR columnDefinition ( COMMA columnDefinition )* RPAR
  ;

columnDefinition :
    identifier typeName ( LPAR INTEGER ( COMMA INTEGER )? RPAR )? ( NOT? NULL | WITH? DEFAULT expression? )*
    ;

typeName :
	( BIGINT
	| CHAR
	| DATE
	| DECIMAL
	| NUMERIC
	| SMALLINT
	| TIME
	| TIMESTAMP
	| VARCHAR
	)
	;
	
temporaryTableOption :
	( ON COMMIT ( DELETE | PRESERVE ) ROWS
	| WITH REPLACE
	| NOT? LOGGED
	| dataClause
	)
	;

createTableStatement :
    CREATE ( orReplaceClause )? TEMPORARY? TABLE ( IF NOT EXISTS )? table
    ( AS ( selectStatement | LPAR selectStatement RPAR )
      ( recordFormatClause
      | dataClause
      )*
    | tableDefinition
    )
    ;

orReplaceClause :
	OR REPLACE
	;

recordFormatClause :
	RCDFMT identifier
	;

dataClause :
    WITH NO? DATA | DEFINITION ONLY
    ;

createSequenceStatement :
	CREATE SEQUENCE ( IF NOT EXISTS )? sequence
	;
	
labelStatement :
    LABEL ON
    ( ( TABLE table | INDEX index ) IS STRING
    | COLUMN table LPAR columnLabel ( COMMA columnLabel )* RPAR
    )
    ;
    
columnLabel :
    identifier TEXT? IS STRING
    ;

createIndexStatement :
	CREATE UNIQUE? INDEX index ON table LPAR orderingTerm ( COMMA orderingTerm )* RPAR 
	( mediaClause
	| memoryClause
//	| USING catchAll
	)*
	;

mediaClause :
    UNIT ( ANY | SSD )
    ;

memoryClause :
    KEEP IN MEMORY ( YES | NO )
    ;
    
alterTableStatement :
    ALTER TABLE table 
    ( ADD PRIMARY KEY LPAR identifier ( COMMA identifier )* RPAR
    | ADD COLUMN columnDefinition
    )*
    ;

commitStatement :
	COMMIT WORK?
	;
	
//callStatement :
//    CALL identifier LPAR callParam ( COMMA callParam )* RPAR
//    ;

//callParam :
//    ( SP_IN { $statement::stmt.remove(new Position($start.getTokenIndex())); }
//      combinedInputParameter
//    | SP_OUT  { $statement::stmt.remove(new Position($start.getTokenIndex())); }
//      combinedOutputParameter
//    | SP_INOUT  { $statement::stmt.remove(new Position($start.getTokenIndex())); }
//      combinedInoutParameter 
//    )
//    ;

setOptionStatement :
    SET OPTION optionClause ( COMMA optionClause )*
    ;

optionClause :
    optionName EQUALS optionValue
    ;

optionName :
    identifier
    | COMMIT
    ;

optionValue :
    DB2_CONSTANT
    ;

whereClause :
    WHERE ( currentClause | expression )
    ;

currentClause :
    CURRENT OF identifier
    ;
        
selectColumn : 
    ( NEXTVAL FOR sequence
    | columnExpression ( AS? ( identifier | RPG_STRING ) )?
    | ( identifier POINT )? MULT
    )
    ;

columnExpression :
    identifier | expression
    ;

joinSource :
    tableOrSelect ( ( INNER | ( LEFT | RIGHT ) OUTER? )? JOIN tableOrSelect ON expression )* 
    ;

tableOrSelect :
    ( table
    | LPAR selectExpression RPAR ( AS? identifier )?
    | TABLE LPAR ( expression ( COMMA expression )* | selectStatement ) RPAR ( AS? identifier )?
    )
    ;

table :
    qualifiedName ( AS? identifier )?
	;

sequence :
    qualifiedName
    ;

index :
	qualifiedName
	;

schemaSeparator :
	( POINT 
	| SLASH
	)
	;

qualifiedName :
    ( library = identifier SLASH                                
    | schema = identifier POINT 
    )?
    name = identifier
    ;
    
localTableDefinition :
  localTable ( AS LPAR selectExpression RPAR )? //( selectModifier )
  ;

localTable :
	identifier ( LPAR identifier ( COMMA identifier )* RPAR )?
	;

simpleOutputParameter :
    ( outputParameter
    | QUESTION
    )
    ;

combinedOutputParameter :
    ( outputParameter indicator?
    | QUESTION
    )
    ;
		
outputParameter :
    parameter
	;

simpleInputParameter :
    ( inputParameter
    | QUESTION
    )
    ;
    
combinedInputParameter :
    ( inputParameter indicator?
    | QUESTION
    )
    ;

inputParameter :
    parameter
    ;

simpleInoutParameter :
    ( inoutParameter
    | QUESTION
    )
    ;
    
combinedInoutParameter :
    ( inoutParameter indicator?
    | QUESTION
    )
    ;

inoutParameter :
    parameter
    ;

orderingTerm :
    expression ( ASC | DESC )?
    ;
    
expression : 
	term ( binaryOp term )* postfixOp?
	;

castExpr :
    CAST LPAR expression AS expression RPAR
    ;
    
term :
    currentTimestamp
    | prefixOp?
    ( factor
    | castExpr
    | decimalCall
    | dateCall
    | timestampCall
//    | function exprList?
//    | identifier exprList?
    | functionCall
    | exprList 
    | LPAR selectStatement RPAR
    | CASE expression? ( WHEN expression THEN expression )+ ( ELSE expression )? END
    )
	;

functionCall :
    function
    ( exprList | LPAR RPAR | LPAR expression FROM expression FOR expression RPAR )?
    ( OVER LPAR ( PARTITION BY columnExpression )? orderByClause? RPAR )?
    ;
    
function :
	( CONCAT
	| DAY
	| DAYS
	| IFNULL
	| LOCATE
	| MAX
	| MONTH
	| NEXTVAL
	| VALUE
	| YEAR
	| typeName
	| identifier
	)
	;
	
exprList :
    LPAR expression ( COMMA expression )* RPAR
    ;

decimalCall :
	decimalFunction
	( LPAR 
	  ( combinedInputParameter	
	  | expression 
	  ) 
	  RPAR
	)?
	;

decimalFunction :
	( EXP
	| LN
	)
	;

dateCall :
	DATE
	( LPAR 
	  ( combinedInputParameter	
	  | expression 
	  ) 
	  RPAR
	)?
	;
	
timestampCall :
	TIMESTAMP 
	( LPAR 
	  ( combinedInputParameter	
	  | expression 
	  ) 
	  RPAR
	)?
	;
	
currentTimestamp :
    CURRENT ( USCORE )? TIMESTAMP
    ;

factor :
    ( STRING
    | number
    | combinedInputParameter
    | NULL
    | MULT
    | identifier POINT MULT
    | identifier POINT identifier
    )
    ;
            
binaryOp :
    ( AND
    | AS
    | BETWEEN
    | CAST_OP
    | COLLATE
    | CONCAT
    | NOT? IN
    | IS
    | NOT? LIKE
    | OR
    | POINT
    | EQUALS
    | LESS    
    | GRT
    | LESS_EQ
    | GRT_EQ
    | PLUS
    | MINUS
    | SLASH
    | MULT
    | LOG_OR
    | LOG_AND
    | NEQ
    | PARAM_IS
    )
    ;

prefixOp :
	( ALL
	| CURRENT
	| DISTINCT
	| NOT? EXISTS
	| NOT
	| PLUS
	| MINUS
	)
	;
	
postfixOp :
    ( DAY
    | DAYS
    | MONTH
    | YEAR
    )
    ;

indicator :
    parameter
    ;
        	
parameter :
    COLON ( identifier POINT )? identifier ( LPAR INTEGER RPAR )?
    ;

number :
    INTEGER | floating
    ;

floating :
//	INTEGER DEC_PART
	INTEGER ( COMMA | POINT ) INTEGER
	;
	
identifier :
    IDENTIFIER
    | RPG_IDENTIFIER
    | DATA
    | DESC
    | LEFT
    | RIGHT
    | ROWS
    | TEMPORARY 
    | TEXT
    | TIMESTAMP
    | VALUE
    | UPDATE
    | YEAR
    ;
    
//catchAll :
//    sqlWord+ ( sqlSeparator+ sqlWord* )*
//    ;
	
//sqlWord :
//    ( ALL
//    | BY
//    | CLOSE
//    | DECLARE
//    | ELSE
//    | END
//    | FROM
//    | IS
//    | OF
//    | ON
//    | OPEN
//    | READ
//    | SET
//    | TO
//    | USING
//    | VALUE
//    | WHEN
//    | identifier
//    | STRING
//    | number
//    | combinedInputParameter
//    )
//    ;

//sqlSeparator :
//    ( COMMA
//    | POINT
//    | EQUALS
//    | LESS    
//    | GRT
//    | LESS_EQ
//    | GRT_EQ
//    | LPAR
//    | RPAR
//    | PLUS
//    | MINUS
//    | SLASH
//    | MULT
//    | LOG_OR
//    | LOG_AND
//    | NEQ
//    )
//    ;
