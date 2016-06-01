package com.afrozaar.wordpress.wpapi.v2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public  class StringBooleanMapDeserializer extends JsonDeserializer<Map<String, Boolean>> {

    private static final Logger LOG = LoggerFactory.getLogger(StringBooleanMapDeserializer.class);

    public StringBooleanMapDeserializer() {
    }

    @Override
    public Map<String, Boolean> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        Map<String, Boolean> data = new LinkedHashMap<>();

        String key = null;
        Boolean value = null;

        while (!p.nextToken().equals(JsonToken.END_OBJECT)) {

            if (p.getCurrentToken().equals(JsonToken.FIELD_NAME)) {
                key = p.getCurrentName();
            }

            if (p.getCurrentToken().equals(JsonToken.VALUE_STRING)) {

                final String valueAsString = p.getValueAsString() != null && !p.getValueAsString().trim().isEmpty() ? p.getValueAsString() : "";
                if (Arrays.asList("0", "no", "false").contains(valueAsString)) {
                    value = false;
                } else if (Arrays.asList("1", "yes", "true").contains(valueAsString)) {
                    value = true;
                } else {
                    LOG.warn("Could not parse boolean value from [{}]. Supported values are: Truthy(1, yes, true) and Falsy(0, no, false).", valueAsString);
                }
            }

            if (p.getCurrentToken().equals(JsonToken.VALUE_TRUE)) {
                value = Boolean.TRUE;
            }
            if (p.getCurrentToken().equals(JsonToken.VALUE_FALSE)) {
                value = Boolean.FALSE;
            }

            if (key != null && value != null) {
                data.put(key, value);
                key = null;
                value = null;
            }
        }

        //          this code:
        //                    System.out.printf("token<%s>, name<%s>, value<%s>\n", p.getCurrentToken(), p.getCurrentName(), p.getCurrentValue());
        //                    p.nextToken();
        //          produced:
        //                    token<START_OBJECT>, name<capabilities>, value<null>
        //                    token<FIELD_NAME>, name<some_string>, value<null>
        //                    token<VALUE_STRING>, name<some_string>, value<null>
        //                    token<FIELD_NAME>, name<some_true>, value<null>
        //                    token<VALUE_TRUE>, name<some_true>, value<null>
        //                    token<FIELD_NAME>, name<some_false>, value<null>
        //                    token<VALUE_FALSE>, name<some_false>, value<null>
        //                    token<END_OBJECT>, name<capabilities>, value<com.afrozaar.wordpress.wpapi.v2.model.User@7c37508a>
        return data;
    }
}
