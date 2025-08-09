package com.ddnsking.linktag.dto;

import java.util.Arrays;
import java.util.List;

public record UpdateLinkRequest(String title, String url, String description, String tags, Boolean isPublic) {
    public List<String> parseTags() {
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
