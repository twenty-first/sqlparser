package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class CompositeOutputParameter extends AstNode {

    public CompositeOutputParameter(Location location) {
        super(location);
    }
    
}
