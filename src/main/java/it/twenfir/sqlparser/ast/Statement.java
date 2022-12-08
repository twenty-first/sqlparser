package it.twenfir.sqlparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class Statement extends AstNode {

    protected Statement(Location location) {
        super(location);
    }
    
}
