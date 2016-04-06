package com.afrozaar.wordpress.wpapi.v2.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class ClientUserUnitTest {

    String json = "{\n"
            + "    \"filesize\": 3205,\n"
            + "    \"mime_type\": \"video/quicktime\",\n"
            + "    \"length\": 1,\n"
            + "    \"length_formatted\": \"0:01\",\n"
            + "    \"width\": 1280,\n"
            + "    \"height\": 720,\n"
            + "    \"fileformat\": \"mp4\",\n"
            + "    \"dataformat\": \"quicktime\",\n"
            + "    \"audio\": {\n"
            + "        \"dataformat\": \"mp4\",\n"
            + "        \"codec\": \"ISO/IEC 14496-3 AAC\",\n"
            + "        \"sample_rate\": 44100,\n"
            + "        \"channels\": 2,\n"
            + "        \"bits_per_sample\": 16,\n"
            + "        \"lossless\": false,\n"
            + "        \"channelmode\": \"stereo\"\n"
            + "    },\n"
            + "    \"sizes\": {}\n"
            + "}";

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void mapJsonToHashmap() throws IOException {
        Map map = mapper.readValue(json, Map.class);
    }
}
