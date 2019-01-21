package org.mcvly.refactoring.first_example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class StatementTest {

    @Test
    public void testIntermediateData() {
        List<Map> invoices = getInvoices();
        StatementData statementData = new StatementData(getPlays(), invoices.get(0));

        assertEquals("BigCo", statementData.getCustomer());

        assertEquals(3, statementData.getPerformances().size());
        assertEquals("Hamlet", statementData.getPerformances().get(0).getPlayName());
        assertEquals(55, statementData.getPerformances().get(0).getAudience());
        assertEquals(65000, statementData.getPerformances().get(0).getAmount(), 0.01);
        assertEquals(25, statementData.getPerformances().get(0).getVolumeCredits());

        assertEquals("As you like it", statementData.getPerformances().get(1).getPlayName());
        assertEquals(35, statementData.getPerformances().get(1).getAudience());
        assertEquals(58000, statementData.getPerformances().get(1).getAmount(), 0.01);
        assertEquals(12, statementData.getPerformances().get(1).getVolumeCredits());

        assertEquals("Othello", statementData.getPerformances().get(2).getPlayName());
        assertEquals(40, statementData.getPerformances().get(2).getAudience());
        assertEquals(50000, statementData.getPerformances().get(2).getAmount(), 0.01);
        assertEquals(10, statementData.getPerformances().get(2).getVolumeCredits());

        assertEquals(173000, statementData.getTotalAmount(), 0.001);
        assertEquals(47, statementData.getTotalVolumeCredits());
    }

    @Test
    public void sampleDataTextFormat() {
        List<String> strings = getInvoices().stream()
                .map(invoice -> new Statement().textStatement(getPlays(), invoice))
                .flatMap(s -> Arrays.stream(s.split("\n")))
                .collect(toList());
        assertEquals(6, strings.size());
        assertEquals("Statement for BigCo", strings.get(0));
        assertEquals("  Hamlet: $650.00 (55 seats)", strings.get(1));
        assertEquals("  As you like it: $580.00 (35 seats)", strings.get(2));
        assertEquals("  Othello: $500.00 (40 seats)", strings.get(3));
        assertEquals("Amount owed is $1730.00", strings.get(4));
        assertEquals("You earned 47 credits", strings.get(5));
    }

    @Test
    public void sampleDataHtmlFormat() {
        List<String> strings = getInvoices().stream()
                .map(invoice -> new Statement().htmlStatement(getPlays(), invoice))
                .flatMap(s -> Arrays.stream(s.split("\n")))
                .collect(toList());
        assertEquals(9, strings.size());
        assertEquals("<h1>Statement for BigCo</h1>", strings.get(0));
        assertEquals("<table>", strings.get(1));
        assertEquals("<tr><th>play</th><th>seats</th><th>cost</th></tr>", strings.get(2));
        assertEquals("  <tr><td>Hamlet</td><td>55</td><td>$650.00</td></tr>", strings.get(3));
        assertEquals("  <tr><td>As you like it</td><td>35</td><td>$580.00</td></tr>", strings.get(4));
        assertEquals("  <tr><td>Othello</td><td>40</td><td>$500.00</td></tr>", strings.get(5));
        assertEquals("</table>", strings.get(6));
        assertEquals("<p>Amount owed is <em>$1730.00</em></p>", strings.get(7));
        assertEquals("<p>You earned <em>47</em> credits</p>", strings.get(8));
    }

    private Map getPlays() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(readFile("plays.json"), Map.class);
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }

    private List<Map> getInvoices() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(readFile("invoices.json"), List.class);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private InputStream readFile(String s) {
        return StatementTest.class.getClassLoader().getResourceAsStream(s);
    }
}