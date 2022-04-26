package it.twenfir.sqlparser;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.twenfir.sqlparser.SqlParser.StatementContext;
import it.twenfir.sqlparser.ast.Statement;

public class Driver {

	private static Logger log = LoggerFactory.getLogger(Driver.class);
	
	private CommonTokenStream tokenStream;
	private SqlParser parser;
	private StatementContext parseTree;
	
	public Driver(String statement) {
		this(statement, new DefaultErrorListener(log));
	}
		
	public Driver(String statement, ANTLRErrorListener errorListener) {
        ANTLRInputStream inputStream = new ANTLRInputStream(statement);
        SqlLexer lexer = new SqlLexer(inputStream);
        if ( errorListener != null ) {
        	lexer.removeErrorListeners();
        	lexer.addErrorListener(errorListener);
        }
        SqlTokenSource source = new SqlTokenSource(lexer);
        tokenStream = new CommonTokenStream(source);
        parser = new SqlParser(tokenStream);
        if ( errorListener != null ) {
        	parser.removeErrorListeners();
        	parser.addErrorListener(errorListener);
        }
	}
	
    public StatementContext parse() {
    	if ( parseTree == null ) {
            parseTree = parser.statement();
    	}
    	if ( parser.getNumberOfSyntaxErrors() > 0 ) {
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
