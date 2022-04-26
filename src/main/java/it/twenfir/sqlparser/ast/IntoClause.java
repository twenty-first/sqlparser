package it.twenfir.sqlparser.ast;

import org.antlr.v4.runtime.ParserRuleContext;

public class IntoClause extends AstNode {

	public IntoClause(ParserRuleContext context) {
		super(context);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
