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
    | createIndexStatement
    | createTableStatement
    | declareCursorStatement
    | declareTempTableStatement
    | deleteStatement
    | executeStatement
    | fetchStatement
    | insertStatement
    | openStatement
    | prepareStatement
    | selectStatement
    | setStatement
    | setOptionStatement
    | updateStatement
    | valuesStatement
    | catchAll
    )
    ;

selectStatement :
    ( WITH localTableDefinition ( COMMA localTableDefinition )* )? 
    selectExpression //( selectModifier )*
    ( WITH UR )?
    ;

selectExpression :
    simpleSelect ( UNION ALL? simpleSelect )*
    ;

simpleSelect :
    SELECT ( ALL | DISTINCT )? selectColumn ( COMMA selectColumn )*
    intoClause?
    ( FROM joinSource ( COMMA joinSource )* )?
    whereClause?
    ( GROUP BY orderingTerm ( COMMA orderingTerm )* ( HAVING expression )? )?
    ( ORDER BY orderingTerm ( COMMA orderingTerm )* )?
    ( FOR ( FETCH | READ ) ONLY
    | OPTIMIZE FOR INTEGER ( ROW | ROWS )
    | FETCH FIRST INTEGER? ( ROW | ROWS ) ONLY 
    )*
	;
	    
insertStatement :
    INSERT INTO table ( LPAR identifier ( COMMA identifier )* RPAR )? insertRowsClause?
    ( insertValuesClause
    | LPAR selectStatement RPAR
    | selectStatement
    )
    ;

insertRowsClause :
    simpleInputParameter ROWS
    ;

insertValuesClause :
    VALUES LPAR 
    ( combinedInputParameter
    | expression 
    )
    ( COMMA expression )*
    RPAR
    ;

updateStatement :
    UPDATE table SET identifier ( POINT identifier )? EQUALS expression 
    ( COMMA identifier ( POINT identifier )? EQUALS expression )*
    whereClause?
    ;

deleteStatement :
    DELETE FROM? table whereClause?
    ;

setStatement :
    SET ( identifier | combinedOutputParameter ) EQUALS expression
    ;

valuesStatement :
	VALUES expression ( COMMA expression )* intoClause?
	;
	
fetchStatement :
    FETCH NEXT? FROM? identifier intoClause
    ;
        
intoClause :
    INTO combinedOutputParameter ( COMMA combinedOutputParameter )*
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
    DECLARE ( CURSOR name = identifier | name = identifier CURSOR ) FOR
    ( stmt = identifier
    | simpleSelect
    )
    ;
    
prepareStatement : 
    PREPARE identifier FROM simpleInputParameter
    ;
    
closeStatement :
    CLOSE identifier
    ;
    
declareTempTableStatement :
	DECLARE GLOBAL TEMPORARY TABLE table
	( LIKE table 
	| tableDefinition
	)
	temporaryTableOption*
	;

tableDefinition :
    LPAR identifier expression ( COMMA identifier expression )* RPAR
  ;

temporaryTableOption :
	( ON COMMIT ( DELETE | PRESERVE ) ROWS
	| WITH REPLACE
	| NOT? LOGGED
	)
	;

createTableStatement :
    CREATE ( orReplaceClause )? TEMPORARY? TABLE ( IF NOT EXISTS )? table
    ( AS simpleSelect ( WITH NO? DATA )?
    | tableDefinition
    )
    ;

orReplaceClause :
	OR REPLACE
	;
	
createIndexStatement :
	CREATE UNIQUE? INDEX index ON table LPAR orderingTerm ( COMMA orderingTerm )* RPAR 
	( USING catchAll )?
	;

alterTableStatement :
ALTER TABLE identifier ADD PRIMARY KEY LPAR identifier ( COMMA identifier )* RPAR
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
    WHERE expression
    ;
        
selectColumn : 
    ( NEXTVAL FOR sequence
    | columnExpression ( AS? identifier )?
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
    )
    ;

table :
	( identifier								
	| identifier schemaSeparator identifier	
	)
	( AS? identifier )?
	;

sequence :
    identifier
    ;

index :
	( identifier 
	| identifier schemaSeparator identifier
	)
	;

schemaSeparator :
	( POINT 
	| SLASH
	)
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
	
term :
    currentTimestamp
    | prefixOp?
    ( factor
    | decimalCall
    | dateCall
    | timestampCall
//    | function exprList?
//    | identifier exprList?
    | functionCall
    | exprList 
    | LPAR selectExpression RPAR
    | CASE expression? ( WHEN expression THEN expression )+ ( ELSE expression )? END
    )
	;

functionCall :
    ( function | identifier ) exprList?
    ;
    
function :
	( CONCAT
	| DAY
	| DAYS
	| LOCATE
	| MAX
	| MONTH
//	| VALUE
	| YEAR
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
    | CAST
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
	INTEGER DEC_PART
	;
	
identifier :
    IDENTIFIER | RPG_IDENTIFIER
    ;
    
catchAll :
    sqlWord+ ( sqlSeparator+ sqlWord* )*
    ;
	
sqlWord :
    ( ALL
    | BY
    | CLOSE
    | DECLARE
    | ELSE
    | END
    | FROM
    | IS
    | OF
    | ON
    | OPEN
    | READ
    | SET
    | TO
    | USING
//    | VALUE
    | WHEN
    | identifier
    | STRING
    | number
    | combinedInputParameter
    )
    ;

sqlSeparator :
    ( COMMA
    | POINT
    | EQUALS
    | LESS    
    | GRT
    | LESS_EQ
    | GRT_EQ
    | LPAR
    | RPAR
    | PLUS
    | MINUS
    | SLASH
    | MULT
    | LOG_OR
    | LOG_AND
    | NEQ
    )
    ;
