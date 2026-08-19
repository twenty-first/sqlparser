package it.twenfir.sqlparser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
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
	public void commentTest() throws ParseException {
		helper.parse("select c -- COMMENT\nfrom t");
	}

	@Test
	public void dollarInIdentifierTest() throws ParseException {
		helper.parse("select c$ from $t");
	}

	@Test
	public void simpleParameterNameTest() throws ParseException {
		helper.parse("select c from t where d = :e");
	}

	@Test
	public void qualifiedParameterNameTest() throws ParseException {
		helper.parse("select c from t where d = :e.f");
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
        helper.parse("declare c cursor for select * from t where f = 'A' and g = :h");
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
    public void testSelectIntoWithIndicator() throws ParseException
    {
        helper.parse("select c1 into :p :i from s.t where c2 = 'v'");
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
    @Disabled
    public void testSimpleSubstring() throws ParseException {
    	helper.parse("select substr(field, 1, 4) into dnaz from p99tabe where fld01 = 'naz' and soci = '**' and ctab = rqanag.qarstat");
    }

    @Test
    public void testSubstring() throws ParseException {
    	helper.parse("select a, b, substr((a)::text, 1, 6) as c, substr((a)::text, 7, 14) as d, e " +
                "from t where ( substr((a)::text, 1, 6), substr((a)::text, 7, 14) ) >= ( 'A', '' ) order by c, d, r");
    }

    @Test
    public void testDollarInParamName() throws ParseException
    {
        helper.parse("select c1 into :$p from s.t where c2 = 'v'");
    }

    @Test
    public void testSetOutputParameter() throws ParseException
    {
        helper.parse("set :op = ( select min(c) from t where d = :ip group by d )");
    }

    @Test
    public void testParameterNamedTemp() throws ParseException
    {
        helper.parse("select count(f) into :op from t where g = :ip and " + 
                "f like '' concat rtrim(:temp) concat '%' concat '' and h <> 'ok' and i <> 0");
    }
    
    @Test
    public void testDropTable() {
    	helper.parse("drop table qtemp/cau_bol");
    }
    
    @Test
    public void testComplexExpression() {
    	helper.parse("select count(*) into :sql_conta from priugg0f where ((((uganno*100)+ugmese)*100)+uggior)" + 
    			">=:sql_data and ugfond=:sql_fondo and ugfl02 in('p','g')");
    }
    
    @Test
    public void testFetchFor() {
    	helper.parse("fetch c1 for :nrrows rows into :dsmotra");
    }
    
    @Test
    public void testCreateTableAsSelect() {
    	helper.parse("create table qtemp.wani0102 as (select ecrap, a.* from fuscon00f a join mosot00f on ffprog=eprog ) definition only rcdfmt wa0102");
    }
    
    @Test
    public void testCreateTableWithColumns() {
    	helper.parse("create table qtemp/stprz00f ( stdpro numeric(8) default 0 not null, row_rrn bigint not null default nextval('qtemp/stprz00f_rrn_seq') )");
    }
    
    @Test
    public void testInsert() {
    	helper.parse("insert into p01vrap00 values :dsp01vrap");
    }
    
    @Test
    public void testComplexDeclareCursor() {
    	helper.parse(
    			"declare c1 cursor for " +
				"select ifnull(char(substring(field, 31, 20), 20), 'NODESC') as DESC , t.*, (" +
				"	select count(distinct sccrap) from salday0f " +
				") as NRAPTOT from (" +
				"	select scfond , decimal(sum(rap), 10 , 0) as NRAP , decimal(sum(patrim), 15 , 2) as VPAT , decimal(ravall, 10 , 3) as VQUO, decimal(quote, 18 , 6) as NQUO from (" +
				"		select scfond, quote, quote * ravall patrim, ravall, rap from (" +
				"			select scfond, sum(scquoc+scquof) quote, count(distinct sccrap) rap from salday0f group by scfond " +
				"		) as a " +
				"		inner join (" +
				"			select b.rafond, b.ravall, b.radatv from  vlqfon0f as b inner join (" +
				"				select rafond, radatv dt from vlqfon0f where radatv = :w_data group by rafond , radatv " +
				"			) as c on b.rafond=c.rafond and b.radatv=dt " +
				"		) as d on a.scfond=d.rafond " +
				"	) as e group by scfond , ravall , quote " +
				") as t left join atabell on keyfl = 'TABSOCA1' order by scfond" +
    			""
		);
    }
    
    @Test
    public void testPostgresqlSubstring() {
    	helper.parse("select substring(value from ? for ?) from dtaara where name = ?");
    }
    
    @Test
    public void testValueAsIdentifier() {
    	helper.parse("update dtaara set value = ?, decvalue = null where name = ?");
	}
    
    @Test
    public void testParagraphSignInFieldName() {
    	helper.parse("select deimd§ from nav.divest0f");
    }
	
	@Test
	public void testFetchFirstParameter() {
		helper.parse("            Declare C3 Cursor For                                               \n"
				+ "             select a.dercor_tit, dec(a.dercor_bet, 6, 3),                      \n"
				+ "                    divadi, divaut                                              \n"
				+ "               from dercor0f a,                    // valori beta               \n"
				+ "                    derdi00f b                     // titoli correlabili        \n"
				+ "              where a.dercor_tit = b.ditito        // codice titolo uguale      \n"
				+ "                and a.dercor_tip = '0'             // record di testata         \n"
				+ "                and b.difond = : in_codFon         // solo il fondo richiesto   \n"
				+ "                and a.dercor_ind = : in_codInd     // indice richiesto          \n"
				+ "                and a.dercor_bet <> 0              // beta valido               \n"
				+ "                and a.dercor_bet >= : in_valBetDa  // beta nei limiti da        \n"
				+ "                and a.dercor_bet <= : in_valBetA   // beta nei limiti a         \n"
				+ "                and a.dercor_bet <= : in_valBetLs  // beta <= ultimo letto      \n"
				+ "                and a.dercor_tit <> : in_codTitLs  // titolo <> ultimo letto    \n"
				+ "                and b.divadi > b.divaut            // valore disponibile <> 0   \n"
				+ "                and a.dercor_dtr =                 // correlazione piu' recente \n"
				+ "                         (select max(c.dercor_dtr) // rispetto alla data NAV    \n"
				+ "                            from dercor0f c                                     \n"
				+ "                           where a.dercor_ind = c.dercor_ind                    \n"
				+ "                             and a.dercor_tit = c.dercor_tit                    \n"
				+ "                             and c.dercor_dtr <= : in_datLim                    \n"
				+ "                             and c.dercor_tip = '0')                            \n"
				+ "              order by dec(a.dercor_bet, 6, 3) desc,                            \n"
				+ "                       a.dercor_tit                                             \n"
				+ "              fetch first : x_nRows rows only");
	}
    
    @Test
    public void testCreateSequence() {
    	helper.parse("create sequence qtemp/stprz00f_rrn_seq");
    }

}
