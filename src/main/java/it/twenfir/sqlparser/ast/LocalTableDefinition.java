package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class LocalTableDefinition extends AstNode {

	public LocalTableDefinition(Location location) {
		super(location);
	}
	
	public SelectExpression getSelectExpression() {
		return getChild(SelectExpression.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitLocalTableDefinition(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
