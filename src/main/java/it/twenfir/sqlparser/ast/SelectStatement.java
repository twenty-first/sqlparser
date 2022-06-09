package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class SelectStatement extends AstNode {

	public SelectStatement(Location location) {
		super(location);
	}
	
	public Iterator<LocalTableDefinition> getLocalTableDefinitions() {
		return getChildren(LocalTableDefinition.class);
	}
	
	public SelectExpression getSelectExpression() {
		return getChild(SelectExpression.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitSelectStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
