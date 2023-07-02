package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class CreateTableStatement extends Statement {

    public CreateTableStatement(Location location) {
        super(location);
    }
	
    public boolean isReplace() {
    	return getChild(OrReplaceClause.class) != null;
    }
    
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof SqlVisitor ) {
			return ((SqlVisitor<? extends ValueT>) visitor).visitCreateTableStatement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
