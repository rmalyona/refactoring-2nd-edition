package org.mcvly.refactoring.first_example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mcvly.refactoring.first_example.dto.Invoice;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class StatementTest {
    @Test
    public void sampleDataTextFormat() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Invoice> invoices = getInvoices(objectMapper);
        Map<String, Map<String, String>> plays = getPlays(objectMapper);

        String format = invoices.stream()
                .map(invoice -> new Statement(plays, invoice))
                .map(Statement::statement)
                .collect(Collectors.joining());
        String[] split = format.split("\n");
        assertEquals(6, split.length);
        assertEquals("Statement for BigCo", split[0]);
        assertEquals("  Hamlet: $650.00 (55 seats)", split[1]);
        assertEquals("  As you like it: $580.00 (35 seats)", split[2]);
        assertEquals("  Othello: $500.00 (40 seats)", split[3]);
        assertEquals("Amount owed is $1730.00", split[4]);
        assertEquals("You earned 47 credits", split[5]);
    }

    private Map getPlays(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(readFile("plays.json"), Map.class);
    }

    private List<Invoice> getInvoices(ObjectMapper objectMapper) throws IOException {
        return Arrays.asList(objectMapper.readValue(readFile("invoices.json"), Invoice[].class));
    }

    private InputStream readFile(String s) {
        return StatementTest.class.getClassLoader().getResourceAsStream(s);
    }
}