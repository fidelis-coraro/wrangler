package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.cdap.wrangler.api.annotations.PublicEvolving;

import java.util.HashMap;
import java.util.Map;

@PublicEvolving
public class TimeDuration implements Token {
    private final String original;
    private final long milliseconds;

    private static final Map<String, Long> TIME_MULTIPLIERS = new HashMap<>();
    static {
        TIME_MULTIPLIERS.put("MS", 1L);
        TIME_MULTIPLIERS.put("S", 1000L);
        TIME_MULTIPLIERS.put("SEC", 1000L);
        TIME_MULTIPLIERS.put("SECONDS", 1000L);
        TIME_MULTIPLIERS.put("M", 60000L);
        TIME_MULTIPLIERS.put("MIN", 60000L);
        TIME_MULTIPLIERS.put("MINUTES", 60000L);
    }

    public TimeDuration(String value) {
        this.original = value;
        this.milliseconds = parse(value);
    }

    private long parse(String value) {
        value = value.trim().toUpperCase();
        for (String unit : TIME_MULTIPLIERS.keySet()) {
            if (value.endsWith(unit)) {
                String number = value.substring(0, value.length() - unit.length());
                return (long)(Double.parseDouble(number.trim()) * TIME_MULTIPLIERS.get(unit));
            }
        }
        throw new IllegalArgumentException("Unsupported time format: " + value);
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    @Override
    public Object value() {
        return milliseconds;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(milliseconds);
    }

    @Override
    public String toString() {
        return original;
    }
}
