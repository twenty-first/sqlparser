package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class DeclareCursorStatement extends AstNode {

	private String name;
	private String statement;
	
	public DeclareCursorStatement(Location location, String name, String statement) {
		super(location);
		this.name = name;
		this.statement = statement;
	}

	public String getName() {
		return name;
	}

	public String getStatement() {
		return statement;
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitDeclareCursorStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
