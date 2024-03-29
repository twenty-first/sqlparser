package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class WhereClause extends AstNode {

	public WhereClause(Location location) {
		super(location);
	}
	
	public Expression getExpression() {
		return getChild(Expression.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitWhereClause(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
