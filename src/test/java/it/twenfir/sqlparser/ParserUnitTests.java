package it.twenfir.sqlparser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.twenfir.antlr.exception.ParseException;

public class ParserUnitTests extends TestBase {

	private static final Logger log = LoggerFactory.getLogger(ParserUnitTests.class);
	
	public ParserUnitTests() {
		super(log);
	}

	@Test
	public void smokeTest() throws ParseException {
		helper.parse("select c from t");
	}

	@Test
	public void errorTest() {
		assertThrows(ParseException.class, () -> helper.parse("select from t"));
	}
	
    @Test
    public void testConcat() throws ParseException
    {
        helper.parse("select * from t where c like concat(:c, '%')");
    }

    @Test
    public void testQuestionMarkParameterMarkers() throws ParseException
    {
        helper.parse("select * from t where c like ?");
    }

    @Test
    public void testFetchWithoutNumber() throws ParseException
    {
        helper.parse("select * from t fetch first row only");
    }

    @Test
    public void testDeclareCursor() throws ParseException
    {
        helper.parse("declare cursor c for s");
        helper.parse("declare c cursor for s");
    }

    @Test
    public void testDeclareGlobalTemporary() throws ParseException
    {
        helper.parse("declare global temporary table session.t2 like t1");
    }

    @Test
    public void testDeclareGlobalTemporaryExplicit() throws ParseException
    {
        helper.parse("declare global temporary table session.t (c1 " +
                "decimal(12,0), c2 timestamp)");
    }

    @Test
    public void testDeclareGlobalTemporaryWithOptions() throws ParseException
    {
        helper.parse("declare global temporary table session/t1 like t2" + 
        		" on commit preserve rows with replace not logged");
    }
    
    @Test
    public void testInsertIntoTemporaryTable() throws ParseException
    {
        helper.parse("insert into session/t values ( 10, 'a' )");
    }
    
    @Test
    public void testDelete() throws ParseException
    {
        helper.parse("delete from t where f is null");
        helper.parse("delete t where f is null");
    }
    
    @Test
    public void testCreateIndex() throws ParseException
    {
        helper.parse("create unique index session.i on session.t " + 
        		"(c1 asc, c2 asc, c3 asc) " +
        		"using stogroup sysdeflt priqty 1000 secqty 1000");
    }
    
    @Test
    public void testWithRecurse() throws ParseException
    {
        helper.parse(
                "with recurse ( c1, c2, level ) as ( select " +
                "a.c1, a.c2, 1 from t a where a.c1 = 1 " +
                "union all select a.c1, a.c2, level + 1 from " +
                "t a , recurse b where b.c2 = a.c1 ) select " +
                "c1, c2, level from recurse d order by level desc " +
                "for read only");
    }
    
    @Test
    public void testValuesLn() throws ParseException
    {
        helper.parse("values ln(:f)");
    }
    
    @Test
    public void testQualifiedColumnsInUpdate() throws ParseException
    {
        helper.parse("update t as a set a.c1 = :t-c1 " +
                "where a.c2 in ( :t-c2, :d )");
    }
    
    @Test
    public void testSeparatedIntegerInOperator() throws ParseException
    {
        helper.parse("select c1 from t where c2 in (760, 763, 764)");
    }

    //@Test
    public void testContiguousIntegerInOperator() throws ParseException
    {
        helper.parse("select c1 from t where c2 in (760,763,764)");
    }
    
    //@Test
    public void testAllocateDescriptor() throws ParseException
    {
        helper.parse("allocate descriptor 'desc' with max 500");
    }
    
    //@Test
    public void testDeallocateDescriptor() throws ParseException
    {
        helper.parse("deallocate descriptor 'desc'");
    }

    @Test
    public void testSelectFromSelect() throws ParseException
    {
        helper.parse("select c1, c2 from ( select a1 as c1, a2 as c2 from t )");        
    }

    @Test
    public void testNextval() throws ParseException
    {
        helper.parse("select nextval for wsseq from sysibm.sysdummy1");
    }

    @Test
    public void testWithUr() throws ParseException
    {
        helper.parse("select c1,c2 from t where c2 in  (:c2) with ur");
    }

    @Test
    public void testCreateTemporaryIndex() throws ParseException
    {
        helper.parse("create unique index session.i on session.t (c1 asc, c2 asc, c3 asc)");
    }

    @Test
    public void testCharOfDate() throws ParseException
    {
        helper.parse("select char(date(c),iso) from t");
    }

    @Test
    public void testDb2SetOptions() throws ParseException
    {
        helper.parse("set option naming=*sys, commit=*none");
    }

    @Test
    public void testExecuteImmediate() throws ParseException
    {
        helper.parse("execute immediate :s");
    }

    @Test
    public void testSelectInto() throws ParseException
    {
        helper.parse("select c1 into :p from s.t where c2 = 'v'");
    }

    @Test
    public void testSetOption() throws ParseException
    {
        helper.parse("set random_page_cost = 0.1");
    }

    @Test
    public void testTableExistenceCheck() throws ParseException
    {
        helper.parse("select 1 as v from t where false");
    }

    @Test
    public void testRecordExistenceCheck() throws ParseException
    {
        helper.parse("select 1 as v from t where f = ?");
    }

    @Test
    public void testKeywordAsColumnName() throws ParseException
    {
        helper.parse("select value from t");
    }

    @Test
    public void testCreateTempTable() throws ParseException
    {
        helper.parse("create temporary table if not exists t1 as select * from t2 with no data");
    }

    @Test
    public void testAlterAddPrimaryKey() throws ParseException
    {
        helper.parse("alter table t add primary key ( c )");
    }

    @Test
    public void testLexicographicCondition() throws ParseException
    {
        helper.parse("select a, b, c from t where ( a, b ) >= ( ?, ? ) order by a, b, c");
    }

    @Test
    public void testCast() throws ParseException {
    	helper.parse("select cast (a as text) from t");
    }

    @Test
    public void testPostgresqlCast() throws ParseException {
    	helper.parse("select a::text from t");
    }

    @Test
    public void testSubstring() throws ParseException {
    	helper.parse("select a, b, substr((a)::text, 1, 6) as c, substr((a)::text, 7, 14) as d, e from t where ( substr((a)::text, 1, 6), substr((a)::text, 7, 14) ) >= ( 'A', '' ) order by c, d, r");
    }

    @Test
    public void testDollarInParamName() throws ParseException
    {
        helper.parse("select c1 into :$p from s.t where c2 = 'v'");
    }
}
