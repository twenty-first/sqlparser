package it.twenfir.sqlparser;

import org.slf4j.Logger;

public class TestBase {

	protected Helper helper;
	
	protected TestBase(Logger log) {
		this.helper = new Helper(log);
	}

}
