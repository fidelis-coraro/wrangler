package io.cdap.directives.parser;

import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

public class UnitParserTest {
    @Test
    public void testByteSizeParsing() {
        ByteSize b1 = new ByteSize("10KB");
        ByteSize b2 = new ByteSize("1.5MB");
        ByteSize b3 = new ByteSize("2GB");

        Assert.assertEquals(10 * 1024, b1.getBytes());
        Assert.assertEquals((long)(1.5 * 1024 * 1024), b2.getBytes());
        Assert.assertEquals(2L * 1024 * 1024 * 1024, b3.getBytes());
    }

    @Test
    public void testTimeDurationParsing() {
        TimeDuration t1 = new TimeDuration("500ms");
        TimeDuration t2 = new TimeDuration("2s");
        TimeDuration t3 = new TimeDuration("1.5min");

        Assert.assertEquals(500, t1.getMilliseconds());
        Assert.assertEquals(2000, t2.getMilliseconds());
        Assert.assertEquals((long)(1.5 * 60 * 1000), t3.getMilliseconds());
    }
}
