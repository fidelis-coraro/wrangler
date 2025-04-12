package io.cdap.directives.parser;

import io.cdap.wrangler.TestingRig;
import io.cdap.wrangler.api.RecipeParser;
import io.cdap.wrangler.api.Row;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GrammarParsingTest {
    @Test
    public void testGrammarParsesByteAndTime() throws Exception {
        List<Row> rows = Arrays.asList(new Row());

        String[] recipe = new String[] {
                "set-column :col1 '10MB'",
                "set-column :col2 '5s'"
        };

        List<Row> output = TestingRig.execute(recipe, rows);
        Assert.assertEquals(1, output.size());
        Assert.assertEquals("10MB", output.get(0).getValue("col1"));
        Assert.assertEquals("5s", output.get(0).getValue("col2"));
    }
}
