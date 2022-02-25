package io.github.twentyfirst.sqlparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.github.twentyfirst.sqlparser.SqlParser.StatementContext;

public class Driver {
	
	private CommonTokenStream tokenStream;
	private SqlParser parser;
	private StatementContext parseTree;
	
	public Driver(String statement) {
        ANTLRInputStream inputStream = new ANTLRInputStream(statement);
        SqlLexer lexer = new SqlLexer(inputStream);
        SqlTokenSource source = new SqlTokenSource(lexer);
        tokenStream = new CommonTokenStream(source);
        parser = new SqlParser(tokenStream);
	}
	
    public StatementContext parse() {
    	if ( parseTree == null ) {
            parseTree = parser.statement();
    	}
        return parseTree;
    }
    
    public String translate(Translator translator) {
        ParseTreeWalker walker = new ParseTreeWalker();
        RuleContext tree = parse();
        walker.walk(translator, tree);
        return translator.getText();    	
    }
    
    public TokenStream getTokenStream() {
    	return tokenStream;
    }
}
