package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class CompositeInputParameter extends AstNode {

    public CompositeInputParameter(Location location) {
        super(location);
    }
    
}
