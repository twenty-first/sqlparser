package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class OpenStatement extends Statement {

	private String name;

	public OpenStatement(Location location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public UsingClause getUsingClause() {
		return getChild(UsingClause.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitOpenStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
