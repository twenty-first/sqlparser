package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SelectStatement extends AstNode {

	public SelectStatement(Location location) {
		super(location);
	}
	
	public SelectExpression getSelectExpression() {
		return getChild(SelectExpression.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
