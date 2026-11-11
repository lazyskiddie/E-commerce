package com.ecom.CartService.events;

import tools.jackson.databind.json.JsonMapper;

public class EventJson {
    private static final JsonMapper MAPPER = JsonMapper.builder().build();

    private EventJson() {
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return MAPPER.readValue(json, type);
    }
}
