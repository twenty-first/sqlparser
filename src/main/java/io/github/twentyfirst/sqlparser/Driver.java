package io.github.twentyfirst.sqlparser;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.github.twentyfirst.sqlparser.SqlParser.StatementContext;

public class Driver {
	
	private CommonTokenStream tokenStream;
	private SqlParser parser;
	private StatementContext parseTree;
	
	public Driver(String statement) {
		this(statement, null);
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
        return parseTree;
    }
    
    public String translate(Translator translator) {
        ParseTreeWalker walker = new ParseTreeWalker();
        StatementContext tree = parse();
        walker.walk(translator, tree);
        return translator.getText();    	
    }
    
    public TokenStream getTokenStream() {
    	return tokenStream;
    }
}
