package io.github.twentyfirst.sqlparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import io.github.twentyfirst.sqlparser.SqlParser.StatementContext;

public class Driver {
    public StatementContext parse(String statement) {
        ANTLRInputStream inputStream = new ANTLRInputStream(statement);
        SqlLexer lexer = new SqlLexer(inputStream);
        SqlTokenSource source = new SqlTokenSource(lexer);
        TokenStream tokenStream = new CommonTokenStream(source);
        SqlParser parser = new SqlParser(tokenStream);
        return parser.statement();
    }
}
