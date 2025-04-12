package io.cdap.directives.column;


import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.annotations.Categories;

import io.cdap.wrangler.api.annotations.PublicEvolving;
import io.cdap.wrangler.api.annotations.Usage;
import io.cdap.wrangler.api.parser.*;

import java.util.ArrayList;
import java.util.List;

@Plugin(type = Directive.TYPE)
@Name("aggregate-stats")
@Categories(categories = { "column" })
@Description("Aggregates byte size and time duration columns across rows and outputs totals.")
@PublicEvolving
public class AggregateStats implements Directive {
    private String sizeCol;
    private String timeCol;
    private String outSizeCol;
    private String outTimeCol;

    private long totalBytes = 0L;
    private long totalMillis = 0L;
    private int count = 0;

    @Override
    public UsageDefinition define() {
        UsageDefinition.Builder builder = UsageDefinition.builder("aggregate-stats");
        builder.define("size-column", TokenType.COLUMN_NAME);
        builder.define("time-column", TokenType.COLUMN_NAME);
        builder.define("output-size-column", TokenType.COLUMN_NAME);
        builder.define("output-time-column", TokenType.COLUMN_NAME);
        return builder.build();
    }

    @Override
    public void initialize( Arguments args) throws DirectiveParseException {
        this.sizeCol = ((ColumnName) args.value("size-column")).value();
        this.timeCol = ((ColumnName) args.value("time-column")).value();
        this.outSizeCol = ((ColumnName) args.value("output-size-column")).value();
        this.outTimeCol = ((ColumnName) args.value("output-time-column")).value();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context)
            throws DirectiveExecutionException, ErrorRowException {
        for (Row row : rows) {
            String sizeStr = row.getValue(sizeCol).toString();
            String timeStr = row.getValue(timeCol).toString();

            ByteSize byteSize = new ByteSize(sizeStr);
            TimeDuration timeDuration = new TimeDuration(timeStr);

            totalBytes += byteSize.getBytes();
            totalMillis += timeDuration.getMilliseconds();
            count++;
        }

        // Create output row
        List<Row> results = new ArrayList<>();
        Row output = new Row();
        output.add(outSizeCol, totalBytes / (1024.0 * 1024.0)); // Convert to MB
        output.add(outTimeCol, totalMillis / 1000.0); // Convert to seconds
        results.add(output);
        return results;
    }

    @Override
    public void destroy() {
    }
}
