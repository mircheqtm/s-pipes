package cz.cvut.spipes.function.date;

import cz.cvut.spipes.exception.ParseException;
import cz.cvut.spipes.function.spif.ParseDate;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.sparql.expr.NodeValue;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.apache.jena.graph.NodeFactory.createLiteral;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParseDateTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void execReturnsDate_ItalianLocale() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("02/11/21");
        Node pattern = createLiteral("dd/MM/yy");
        Node patternLanguage = createLiteral("it");

        NodeValue returnedDate = parseDate.exec(text, pattern, patternLanguage, null);
        NodeValue expectedDate = getDateNode("2021-11-02");
        assertEquals(expectedDate, returnedDate);
    }

    @Test
    public void execReturnsDate_EnglishLocale() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("9/21/10");
        Node pattern = createLiteral("M/dd/yy");
        Node patternLanguage = createLiteral("en");

        NodeValue returnedDate = parseDate.exec(text, pattern, patternLanguage, null);
        NodeValue expectedDate = getDateNode("2010-09-21");
        assertEquals(expectedDate, returnedDate);
    }

    @Test
    public void execReturnsDate_FrenchLocale() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("19/12/2016");
        Node pattern = createLiteral("dd/MM/yyyy");
        Node patternLanguage = createLiteral("fr");

        NodeValue returnedDate = parseDate.exec(text, pattern, patternLanguage, null);
        NodeValue expectedDate = getDateNode("2016-12-19");
        assertEquals(expectedDate, returnedDate);
    }

    @Test
    public void execReturnsTime_WithSeconds() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("09:10:10");
        Node pattern = createLiteral("k:m:s");
        NodeValue returnedTime = parseDate.exec(text, pattern, null, null);

        NodeValue expectedTime = getTimeNode("09:10:10");
        assertEquals(expectedTime, returnedTime);
    }

    @Test
    public void execReturnsTime_WithoutSeconds() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("23:59");
        Node pattern = createLiteral("k:m");

        NodeValue returnedTime = parseDate.exec(text, pattern, null, null);
        NodeValue expectedTime = getTimeNode("23:59:00");

        assertEquals(expectedTime, returnedTime);
    }

    @Test
    public void execReturnsTime_OnlyHours() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("15");
        Node pattern = createLiteral("k");

        NodeValue returnedTime = parseDate.exec(text, pattern, null, null);
        NodeValue expectedTime = getTimeNode("15:00:00");

        assertEquals(expectedTime, returnedTime);
    }


    @Test
    public void execReturnsDateTime_FrenchLocale() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("19/12/2016 12:08:56");
        Node pattern = createLiteral("dd/MM/yyyy HH:mm:ss");
        Node patternLanguage = createLiteral("fr");

        NodeValue returnedDateTime = parseDate.exec(text, pattern, patternLanguage, null);
        NodeValue expectedDateTime = getDateTimeNode("2016-12-19T12:08:56");

        assertEquals(expectedDateTime, returnedDateTime);
    }

    @Test
    public void execReturnsDateTime_complexPattern() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("2001.07.04 at 12:08:56 PDT");
        Node pattern = createLiteral("yyyy.MM.dd 'at' HH:mm:ss z");

        NodeValue returnedDateTime = parseDate.exec(text, pattern, null, null);
        NodeValue expectedDateTime = getDateTimeNode("2001-07-04T12:08:56");

        assertEquals(expectedDateTime, returnedDateTime);
    }

    @Test
    public void execReturnsDateTime_nullPatternLanguage() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("2022.01.01 23:59:59");
        Node pattern = createLiteral("yyyy.MM.dd HH:mm:ss");

        NodeValue returnedDateTime = parseDate.exec(text, pattern, null, null);
        NodeValue expectedDateTime = getDateTimeNode("2022-01-01T23:59:59");

        assertEquals(expectedDateTime, returnedDateTime);
    }


    @Test
    public void execThrowsException_badInput() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("Lorem Ipsum");
        Node pattern = createLiteral("yyyy.MM.dd");

        assertThrows(ParseException.class, () -> parseDate.exec(text, pattern, null, null));
    }

    @Test
    public void execThrowsException_nullInputText() {
        ParseDate parseDate = new ParseDate();
        Node pattern = createLiteral("yyyy.MM.dd");

        assertThrows(IllegalArgumentException.class, () -> parseDate.exec(null, pattern, null, null));
    }

    @Test
    public void execThrowsException_nullPattern() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("21/10/2013");

        assertThrows(IllegalArgumentException.class, () -> parseDate.exec(text, null, null, null));
    }

    @Test
    public void execThrowsException_badPatternLanguage() {
        ParseDate parseDate = new ParseDate();
        Node text = createLiteral("19/12/2016");
        Node pattern = createLiteral("dd/MM/yyyy");
        Node patternLanguage = createLiteral("en");

        assertThrows(IllegalArgumentException.class, () -> parseDate.exec(text, pattern, patternLanguage, null));
    }


    private NodeValue getDateNode(String date){
        return getNode(date, XSDDatatype.XSDdate);
    }
    private NodeValue getTimeNode(String time){
        return getNode(time, XSDDatatype.XSDtime);
    }
    private NodeValue getDateTimeNode(String dateTime){
        return getNode(dateTime, XSDDatatype.XSDdateTime);
    }

    private NodeValue getNode(String text, XSDDatatype type) {
        return NodeValue.makeNode(
                text,
                null,
                ((RDFDatatype) type).getURI()
        );
    }
}
