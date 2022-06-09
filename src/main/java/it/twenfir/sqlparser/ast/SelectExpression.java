package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SelectExpression extends AstNode {

	public SelectExpression(Location location) {
		super(location);
	}
	
	public Iterator<SimpleSelect> getSimpleSelects() {
		return getChildren(SimpleSelect.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSelectExpression(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
