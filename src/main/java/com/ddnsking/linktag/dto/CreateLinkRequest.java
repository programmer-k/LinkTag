package com.ddnsking.linktag.dto;

import com.ddnsking.linktag.domain.Link;

public record CreateLinkRequest(String title, String url, String description) {
    public Link toEntity() {
        return Link.builder()
                .title(title)
                .url(url)
                .description(description)
                .build();
    }
}
