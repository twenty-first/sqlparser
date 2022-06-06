package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Term extends AstNode {

	public Term(Location location) {
		super(location);
	}
	
	public Iterator<Factor> getFactors() {
		return getChildren(Factor.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
