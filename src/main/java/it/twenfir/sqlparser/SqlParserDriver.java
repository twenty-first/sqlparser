package it.twenfir.sqlparser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.twenfir.antlr.exception.ParseException;
import it.twenfir.antlr.parser.LoggingTokenSource;
import it.twenfir.antlr.parser.ParserDriverBase;
import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.ast.Statement;

public class SqlParserDriver extends ParserDriverBase {

	private static Logger log = LoggerFactory.getLogger(SqlParserDriver.class);
	
	private CommonTokenStream tokenStream;
	private SqlParser parser;
	private StatementContext parseTree;
	private Statement statement;
	
	public SqlParserDriver(String statement) {
        super("sqlparser", log);
        CodePointCharStream inputStream = CharStreams.fromString(statement);
        SqlLexer lexer = new SqlLexer(inputStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(this);
        LoggingTokenSource source = new LoggingTokenSource(lexer);
        tokenStream = new CommonTokenStream(source);
        parser = new SqlParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(this);
	}
	
    public StatementContext parse() {
    	if ( parseTree == null ) {
            parseTree = parser.statement();
    	}
    	if ( isErrors() ) {
    		throw new ParseException("Parse failed");
    	}
        return parseTree;
    }
    
    public Statement makeAst() {
    	if ( statement == null ) {
            StatementContext tree = parse();
            AstBuilder builder = new AstBuilder();
            statement = builder.visitStatement(tree);
    	}
        return statement;
    }
    
    public TokenStream getTokenStream() {
    	return tokenStream;
    }
}
