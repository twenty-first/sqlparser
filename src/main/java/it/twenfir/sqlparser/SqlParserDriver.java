package it.twenfir.sqlparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
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
	
	public SqlParserDriver(String statement) {
        super("sqlparser", log);
        ANTLRInputStream inputStream = new ANTLRInputStream(statement);
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
    
    public String translate(Translator translator) {
        ParseTreeWalker walker = new ParseTreeWalker();
        StatementContext tree = parse();
        walker.walk(translator, tree);
        return translator.getText();    	
    }
    
    public Statement makeAst() {
        ParseTreeWalker walker = new ParseTreeWalker();
        StatementContext tree = parse();
        AstBuilder builder = new AstBuilder();
        walker.walk(builder, tree);
        return builder.getStatement();
    }
    
    public TokenStream getTokenStream() {
    	return tokenStream;
    }
}
