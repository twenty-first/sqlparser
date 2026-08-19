package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class GetDiagnosticsStatement extends Statement {

	public GetDiagnosticsStatement(Location location) {
		super(location);
	}
	
	public SimpleOutputParameter getOutputParameter() {
		return getChild(SimpleOutputParameter.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitGetDiagnosticsStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
