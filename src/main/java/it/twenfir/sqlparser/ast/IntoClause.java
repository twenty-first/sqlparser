package it.twenfir.sqlparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class IntoClause extends AstNode {

	public IntoClause(Location location) {
		super(location);
	}
	
	public Iterator<CombinedOutputParameter> getCombinedOutputParameters() {
		return getChildren(CombinedOutputParameter.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
    	return visitor.visit(this);
    }

}
