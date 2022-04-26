package it.twenfir.sqlparser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface Translator extends ParseTreeListener {
	String getText();
}
