package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class ExprList extends AstNode {

	public ExprList(Location location) {
		super(location);
	}
	
	public Iterator<Expression> getExpressions() {
		return getChildren(Expression.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitExprList(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
