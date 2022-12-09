package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class OutputParameter extends AstNode {

    public OutputParameter(Location location) {
        super(location);
    }
    
}
