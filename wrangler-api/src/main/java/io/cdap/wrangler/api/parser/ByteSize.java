package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.cdap.wrangler.api.annotations.PublicEvolving;
import java.util.HashMap;
import java.util.Map;

@PublicEvolving
public class ByteSize implements Token {
    private final String original;
    private final long bytes;

    private static final Map<String, Long> UNIT_MULTIPLIERS = new HashMap<>();
    static {
        UNIT_MULTIPLIERS.put("B", 1L);
        UNIT_MULTIPLIERS.put("KB", 1024L);
        UNIT_MULTIPLIERS.put("MB", 1024L * 1024);
        UNIT_MULTIPLIERS.put("GB", 1024L * 1024 * 1024);
        UNIT_MULTIPLIERS.put("TB", 1024L * 1024 * 1024 * 1024);
    }

    public ByteSize(String value) {
        this.original = value;
        this.bytes = parse(value);
    }

    private long parse(String value) {
        value = value.trim().toUpperCase();
        for (String unit : UNIT_MULTIPLIERS.keySet()) {
            if (value.endsWith(unit)) {
                String number = value.substring(0, value.length() - unit.length());
                return (long)(Double.parseDouble(number.trim()) * UNIT_MULTIPLIERS.get(unit));
            }
        }
        throw new IllegalArgumentException("Unsupported byte size format: " + value);
    }

    public long getBytes() {
        return bytes;
    }

    @Override
    public Object value() {
        return bytes;
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(bytes);
    }

    @Override
    public String toString() {
        return original;
    }

}
