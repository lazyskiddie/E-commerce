package com.ecom.OrderService.events;

import tools.jackson.databind.json.JsonMapper;

public final class EventJson {

    private static final JsonMapper MAPPER = JsonMapper.builder().build();

    private EventJson() {
    }

    public static String toJson(Object event) {
        return MAPPER.writeValueAsString(event);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return MAPPER.readValue(json, type);
    }
}
