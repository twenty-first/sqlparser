package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class ForClause extends AstNode {

	private Integer count;
	
	public ForClause(Location location, Integer count) {
		super(location);
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}
	
	public SimpleInputParameter getInputParameter() {
		return getChild(SimpleInputParameter.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitForClause(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
