package it.twenfir.sqlparser;

import static org.junit.Assert.assertThrows;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ParserTest extends TestBase {

	static final Logger log = LoggerFactory.getLogger(ParserTest.class);

	public ParserTest() {
		super(log);
	}

	@Test
	public void smokeTest() throws RecognitionException {
		helper.parse("select c from t");
		Assert.assertFalse(helper.isFailed());
	}

	@Test
	public void errorTest() {
		assertThrows(ParseException.class, () -> helper.parse("select from t"));
	}
	
    @Test
    public void testConcat() throws RecognitionException
    {
        helper.parse("select * from t where c like concat(:c, '%')");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testQuestionMarkParameterMarkers() throws RecognitionException
    {
        helper.parse("select * from t where c like ?");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testFetchWithoutNumber() throws RecognitionException
    {
        helper.parse("select * from t fetch first row only");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testDeclareGlobalTemporary() throws RecognitionException
    {
        helper.parse("declare global temporary table session.t2 like t1");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testDeclareGlobalTemporaryExplicit() throws RecognitionException
    {
        helper.parse("declare global temporary table session.t (c1 " +
                "decimal(12,0), c2 timestamp)");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testDeclareGlobalTemporaryWithOptions() throws RecognitionException
    {
        helper.parse("declare global temporary table session/t1 like t2" + 
        		" on commit preserve rows with replace not logged");
		    Assert.assertFalse(helper.isFailed());
    }
    
    @Test
    public void testInsertIntoTemporaryTable() throws RecognitionException
    {
        helper.parse("insert into session/t values ( 10, 'a' )");
		    Assert.assertFalse(helper.isFailed());
    }
    
    @Test
    public void testCreateIndex() throws RecognitionException
    {
        helper.parse("create unique index session.i on session.t " + 
        		"(c1 asc, c2 asc, c3 asc) " +
        		"using stogroup sysdeflt priqty 1000 secqty 1000");
		    Assert.assertFalse(helper.isFailed());
    }
    
    @Test
    public void testWithRecurse() throws RecognitionException
    {
        helper.parse(
                "with recurse ( c1, c2, level ) as ( select " +
                "a.c1, a.c2, 1 from t a where a.c1 = 1 " +
                "union all select a.c1, a.c2, level + 1 from " +
                "t a , recurse b where b.c2 = a.c1 ) select " +
                "c1, c2, level from recurse d order by level desc " +
                "for read only");
		    Assert.assertFalse(helper.isFailed());
    }
    
    @Test
    public void testValuesLn() throws RecognitionException
    {
        helper.parse("values ln(:f)");
		    Assert.assertFalse(helper.isFailed());
    }
    
    @Test
    public void testQualifiedColumnsInUpdate() throws RecognitionException
    {
        helper.parse("update t as a set a.c1 = :t-c1 " +
                "where a.c2 in ( :t-c2, :d )");
		    Assert.assertFalse(helper.isFailed());
    }
    
    @Test
    public void testSeparatedIntegerInOperator() throws RecognitionException
    {
        helper.parse("select c1 from t where c2 in (760, 763, 764)");
		    Assert.assertFalse(helper.isFailed());
    }

    //@Test
    public void testContiguousIntegerInOperator() throws RecognitionException
    {
        helper.parse("select c1 from t where c2 in (760,763,764)");
		    Assert.assertFalse(helper.isFailed());
    }
    
    //@Test
    public void testAllocateDescriptor() throws RecognitionException
    {
        helper.parse("allocate descriptor 'desc' with max 500");
		    Assert.assertFalse(helper.isFailed());
    }
    
    //@Test
    public void testDeallocateDescriptor() throws RecognitionException
    {
        helper.parse("deallocate descriptor 'desc'");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testSelectFromSelect() throws RecognitionException
    {
        helper.parse("select c1, c2 from ( select a1 as c1, a2 as c2 from t )");        
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testNextval() throws RecognitionException
    {
        helper.parse("select nextval for wsseq from sysibm.sysdummy1");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testWithUr() throws RecognitionException
    {
        helper.parse("select c1,c2 from t where c2 in  (:c2) with ur");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testCreateTemporaryIndex() throws RecognitionException
    {
        helper.parse("create unique index session.i on session.t (c1 asc, c2 asc, c3 asc)");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testCharOfDate() throws RecognitionException
    {
        helper.parse("select char(date(c),iso) from t");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testRpgSetOptions() throws RecognitionException
    {
        helper.parse("set option naming=*sys, commit=*none");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testExecuteImmediate() throws RecognitionException
    {
        helper.parse("execute immediate :s");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testSelectInto() throws RecognitionException
    {
        helper.parse("select c1 into :p from s.t where c2 = 'v'");
		    Assert.assertFalse(helper.isFailed());
    }

    @Test
    public void testSetOption() throws RecognitionException
    {
        helper.parse("set random_page_cost = 0.1");
		    Assert.assertFalse(helper.isFailed());
    }
}
