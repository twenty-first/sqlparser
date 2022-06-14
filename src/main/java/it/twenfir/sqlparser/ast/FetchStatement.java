package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class FetchStatement extends AstNode {

	private String name;

	public FetchStatement(Location location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public IntoClause getIntoClose() {
		return getChild(IntoClause.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitFetchStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
