package it.twenfir.sqlparser.ast;

import org.antlr.v4.runtime.ParserRuleContext;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;

public class IntoClause extends AstNode {

	public IntoClause(ParserRuleContext context) {
		super(context);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
