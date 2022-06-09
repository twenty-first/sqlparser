package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Expression extends AstNode {

	public Expression(Location location) {
		super(location);
	}
	
	public Iterator<Term> getTerms() {
		return getChildren(Term.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitExpression(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
