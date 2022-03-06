parser grammar SqlParser;

options
{
	tokenVocab = SqlLexer ;
}

statement :
    ( select_statement
    | insert_statement
    | update_statement
    | delete_statement
    | set_statement
    | values_statement
    | fetch_statement
    | open_statement
    | execute_statement
    | declare_statement
    | create_statement
    | commit_statement
//    | call_statement
    | set_option_statemment
    | catch_all
    )
    ;

select_statement :
    ( WITH local_table_definition ( COMMA local_table_definition )* )? 
    select_expression //( select_modifier )*
    ( WITH UR )?
    ;

select_expression :
    simple_select ( UNION ALL? simple_select )*
    ;

simple_select :
    SELECT ( ALL | DISTINCT )? select_column ( COMMA select_column )*
    into_clause?
    ( FROM join_source ( COMMA join_source )* )?
    where_clause?
    ( GROUP BY ordering_term ( COMMA ordering_term )* ( HAVING expression )? )?
    ( ORDER BY ordering_term ( COMMA ordering_term )* )?
    ( FOR ( FETCH | READ ) ONLY
    | OPTIMIZE FOR INTEGER ( ROW | ROWS )
    | FETCH FIRST INTEGER? ( ROW | ROWS ) ONLY 
    )*
	;
	    
insert_statement :
    INSERT INTO table ( LPAR IDENTIFIER ( COMMA IDENTIFIER )* RPAR )? insert_rows_clause?
    ( insert_values_clause
    | LPAR select_statement RPAR
    | select_statement
    )
    ;

insert_rows_clause :
    simple_input_parameter ROWS
    ;

insert_values_clause :
    VALUES LPAR 
    ( combined_input_parameter
    | expression 
    )
    ( COMMA expression )*
    RPAR
    ;

update_statement :
    UPDATE table SET IDENTIFIER ( POINT IDENTIFIER )? EQUALS expression 
    ( COMMA IDENTIFIER ( POINT IDENTIFIER )? EQUALS expression )*
    where_clause?
    ;

delete_statement :
    DELETE FROM table where_clause?
    ;

set_statement :
    SET combined_output_parameter EQUALS expression
    ;

values_statement :
	VALUES expression ( COMMA expression )* into_clause?
	;
	
fetch_statement :
    FETCH IDENTIFIER into_clause
    ;
        
into_clause :
    INTO combined_output_parameter ( COMMA combined_output_parameter )*
    ;

execute_statement :
    EXECUTE IMMEDIATE? ( IDENTIFIER | input_parameter ) using_clause?
    ;
        
open_statement :
    OPEN IDENTIFIER using_clause?
    ;

using_clause :
    USING 
    ( combined_input_parameter ( COMMA combined_input_parameter )* 
    | DESCRIPTOR parameter parameter? 
    )
    ;

declare_statement :
	DECLARE GLOBAL TEMPORARY TABLE table
	( LIKE table 
	| table_definition
	)
	temporary_table_option*
	;

table_definition :
    LPAR IDENTIFIER expression ( COMMA IDENTIFIER expression )* RPAR
  ;

temporary_table_option :
	( ON COMMIT ( DELETE | PRESERVE ) ROWS
	| WITH REPLACE
	| NOT? LOGGED
	)
	;

create_statement :
	CREATE UNIQUE? INDEX index ON table LPAR ordering_term ( COMMA ordering_term )* RPAR 
	( USING catch_all )?
	;

commit_statement :
	COMMIT WORK?
	;
	
//call_statement :
//    CALL IDENTIFIER LPAR call_param ( COMMA call_param )* RPAR
//    ;

//call_param :
//    ( SP_IN { $statement::stmt.remove(new Position($start.getTokenIndex())); }
//      combined_input_parameter
//    | SP_OUT  { $statement::stmt.remove(new Position($start.getTokenIndex())); }
//      combined_output_parameter
//    | SP_INOUT  { $statement::stmt.remove(new Position($start.getTokenIndex())); }
//      combined_inout_parameter 
//    )
//    ;

set_option_statemment :
    SET OPTION option_clause
    ;

option_clause :
    option_name EQUALS option_value
    ;

option_name :
    IDENTIFIER
    | COMMIT
    ;

option_value :
    RPG_CONSTANT
    ;

where_clause :
    WHERE expression
    ;
        
select_column : 
    ( NEXTVAL FOR sequence
    | expression ( AS? IDENTIFIER )?
    )
    ;
      
join_source :
    table_or_select ( ( INNER | ( LEFT | RIGHT ) OUTER? )? JOIN table_or_select ON expression )* 
    ;

table_or_select :
    ( table
    | LPAR select_expression RPAR ( AS? IDENTIFIER )?
    )
    ;

table :
	( IDENTIFIER								
	| IDENTIFIER schema_separator IDENTIFIER	
	)
	( AS? IDENTIFIER )?
	;

sequence :
    IDENTIFIER
    ;

index :
	( IDENTIFIER 
	| IDENTIFIER schema_separator IDENTIFIER
	)
	;

schema_separator :
	( POINT 
	| SLASH
	)
	;

local_table_definition :
  local_table ( AS LPAR select_expression RPAR )? //( select_modifier )
  ;

local_table :
	IDENTIFIER ( LPAR IDENTIFIER ( COMMA IDENTIFIER )* RPAR )?
	;

simple_output_parameter :
    ( output_parameter
    | QUESTION
    )
    ;

combined_output_parameter :
    ( output_parameter indicator?
    | QUESTION
    )
    ;
		
output_parameter :
    parameter
	;

simple_input_parameter :
    ( input_parameter
    | QUESTION
    )
    ;
    
combined_input_parameter :
    ( input_parameter indicator?
    | QUESTION
    )
    ;

input_parameter :
    parameter
    ;

simple_inout_parameter :
    ( inout_parameter
    | QUESTION
    )
    ;
    
combined_inout_parameter :
    ( inout_parameter indicator?
    | QUESTION
    )
    ;

inout_parameter :
    parameter
    ;

ordering_term :
    expression ( ASC | DESC )?
    ;
    
expression : 
	term ( binary_op term )* postfix_op?
	;
	
term :
    current_timestamp
    | prefix_op?
    ( factor
    | decimal_call
    | date_call
    | timestamp_call
    | function expr_list?
    | IDENTIFIER expr_list?
    | expr_list 
    | LPAR select_expression RPAR
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
	
expr_list :
    LPAR expression ( COMMA expression )* RPAR
    ;

decimal_call :
	decimal_function
	( LPAR 
	  ( combined_input_parameter	
	  | expression 
	  ) 
	  RPAR
	)?
	;

decimal_function :
	( EXP
	| LN
	)
	;

date_call :
	DATE
	( LPAR 
	  ( combined_input_parameter	
	  | expression 
	  ) 
	  RPAR
	)?
	;
	
timestamp_call :
	TIMESTAMP 
	( LPAR 
	  ( combined_input_parameter	
	  | expression 
	  ) 
	  RPAR
	)?
	;
	
current_timestamp :
    CURRENT ( USCORE )? TIMESTAMP
    ;

factor :
    ( STRING
    | number
    | combined_input_parameter
    | NULL
    | MULT
    | IDENTIFIER POINT MULT
    | IDENTIFIER POINT IDENTIFIER
    )
    ;
            
binary_op :
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

prefix_op :
	( ALL
	| CURRENT
	| DISTINCT
	| NOT? EXISTS
	| NOT
	| PLUS
	| MINUS
	)
	;
	
postfix_op :
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
	    
catch_all :
    sql_word+ ( sql_separator+ sql_word* )*
    ;
	
sql_word :
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
    | combined_input_parameter
    )
    ;

sql_separator :
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
