package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class DropTableStatement extends Statement {

	private String name;
	
	public DropTableStatement(Location location, String name) {
		super(location);
		this.name = name;
	}
	
    public String getName() {
		return name;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitDropTableStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
