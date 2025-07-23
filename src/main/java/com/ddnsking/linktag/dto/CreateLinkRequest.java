package com.ddnsking.linktag.dto;

import com.ddnsking.linktag.domain.Link;

public record CreateLinkRequest(String url, String title, String description) {
    public Link toEntity() {
        return Link.builder()
                .url(url)
                .title(title)
                .description(description)
                .build();
    }
}
