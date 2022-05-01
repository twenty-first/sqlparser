parser grammar SqlParser;

options
{
	tokenVocab = SqlLexer ;
}

statement :
    ( selectStatement
    | insertStatement
    | updateStatement
    | deleteStatement
    | setStatement
    | valuesStatement
    | fetchStatement
    | openStatement
    | executeStatement
    | declareStatement
    | createStatement
    | commitStatement
//    | callStatement
    | setOptionStatemment
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
    INSERT INTO table ( LPAR IDENTIFIER ( COMMA IDENTIFIER )* RPAR )? insertRowsClause?
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
    UPDATE table SET IDENTIFIER ( POINT IDENTIFIER )? EQUALS expression 
    ( COMMA IDENTIFIER ( POINT IDENTIFIER )? EQUALS expression )*
    whereClause?
    ;

deleteStatement :
    DELETE FROM table whereClause?
    ;

setStatement :
    SET combinedOutputParameter EQUALS expression
    ;

valuesStatement :
	VALUES expression ( COMMA expression )* intoClause?
	;
	
fetchStatement :
    FETCH IDENTIFIER intoClause
    ;
        
intoClause :
    INTO combinedOutputParameter ( COMMA combinedOutputParameter )*
    ;

executeStatement :
    EXECUTE IMMEDIATE? ( IDENTIFIER | inputParameter ) usingClause?
    ;
        
openStatement :
    OPEN IDENTIFIER usingClause?
    ;

usingClause :
    USING 
    ( combinedInputParameter ( COMMA combinedInputParameter )* 
    | DESCRIPTOR parameter parameter? 
    )
    ;

declareStatement :
	DECLARE GLOBAL TEMPORARY TABLE table
	( LIKE table 
	| tableDefinition
	)
	temporaryTableOption*
	;

tableDefinition :
    LPAR IDENTIFIER expression ( COMMA IDENTIFIER expression )* RPAR
  ;

temporaryTableOption :
	( ON COMMIT ( DELETE | PRESERVE ) ROWS
	| WITH REPLACE
	| NOT? LOGGED
	)
	;

createStatement :
	CREATE UNIQUE? INDEX index ON table LPAR orderingTerm ( COMMA orderingTerm )* RPAR 
	( USING catchAll )?
	;

commitStatement :
	COMMIT WORK?
	;
	
//callStatement :
//    CALL IDENTIFIER LPAR callParam ( COMMA callParam )* RPAR
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

setOptionStatemment :
    SET OPTION optionClause
    ;

optionClause :
    optionName EQUALS optionValue
    ;

optionName :
    IDENTIFIER
    | COMMIT
    ;

optionValue :
    RPG_CONSTANT
    ;

whereClause :
    WHERE expression
    ;
        
selectColumn : 
    ( NEXTVAL FOR sequence
    | expression ( AS? IDENTIFIER )?
    )
    ;
      
joinSource :
    tableOrSelect ( ( INNER | ( LEFT | RIGHT ) OUTER? )? JOIN tableOrSelect ON expression )* 
    ;

tableOrSelect :
    ( table
    | LPAR selectExpression RPAR ( AS? IDENTIFIER )?
    )
    ;

table :
	( IDENTIFIER								
	| IDENTIFIER schemaSeparator IDENTIFIER	
	)
	( AS? IDENTIFIER )?
	;

sequence :
    IDENTIFIER
    ;

index :
	( IDENTIFIER 
	| IDENTIFIER schemaSeparator IDENTIFIER
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
	IDENTIFIER ( LPAR IDENTIFIER ( COMMA IDENTIFIER )* RPAR )?
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
    | function exprList?
    | IDENTIFIER exprList?
    | exprList 
    | LPAR selectExpression RPAR
    | CASE expression? ( WHEN expression THEN expression )+ ( ELSE expression )? END
    )
	;

function :
	( CONCAT
	| DAY
	| DAYS
	| MAX
	| MONTH
	| VALUE
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
    | IDENTIFIER POINT MULT
    | IDENTIFIER POINT IDENTIFIER
    )
    ;
            
binaryOp :
    ( AND
    | BETWEEN
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
    COLON IDENTIFIER ( LPAR INTEGER RPAR )?
    ;

number :
    INTEGER | floating
    ;

floating :
	INTEGER DEC_PART
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
    | VALUE
    | WHEN
    | IDENTIFIER
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
