package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class IntoClause extends AstNode {

	public IntoClause(Location location) {
		super(location);
	}
	
	public Iterator<OutputParameter> getOutputParameters() {
		return getChildren(OutputParameter.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	if ( visitor instanceof SqlVisitor ) {
    		return ((SqlVisitor<? extends ValueT>) visitor).visitIntoClause(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
