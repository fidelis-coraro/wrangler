package io.cdap.directives.column;

import io.cdap.wrangler.TestingRig;
import io.cdap.wrangler.api.Row;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {
    @Test
    public void testAggregateStatsTotal() throws Exception {
        List<Row> input = Arrays.asList(
                new Row("data_transfer_size", "10MB").add("response_time", "1000ms"),
                new Row("data_transfer_size", "5MB").add("response_time", "3000ms")
        );

        String[] recipe = new String[] {
                "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        List<Row> output = TestingRig.execute(recipe, input);

        Assert.assertEquals(1, output.size());

        Row result = output.get(0);
        double expectedSize = 15.0; // MB
        double expectedTime = 4.0;  // seconds

        Assert.assertEquals(expectedSize, (Double) result.getValue("total_size_mb"), 0.001);
        Assert.assertEquals(expectedTime, (Double) result.getValue("total_time_sec"), 0.001);
    }

    @Test
    public void testAggregateStatsAverage() throws Exception {
        List<Row> input = Arrays.asList(
                new Row("data_transfer_size", "1MB").add("response_time", "2000ms"),
                new Row("data_transfer_size", "3MB").add("response_time", "4000ms")
        );

        String[] recipe = new String[] {
                "aggregate-stats :data_transfer_size :response_time avg_size_mb avg_time_sec average"
        };

        List<Row> output = TestingRig.execute(recipe, input);

        Assert.assertEquals(1, output.size());

        Row result = output.get(0);
        double expectedAvgSize = 2.0; // MB
        double expectedAvgTime = 3.0; // sec

        Assert.assertEquals(expectedAvgSize, (Double) result.getValue("avg_size_mb"), 0.001);
        Assert.assertEquals(expectedAvgTime, (Double) result.getValue("avg_time_sec"), 0.001);
    }
}
