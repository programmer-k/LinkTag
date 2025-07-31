package com.ddnsking.linktag.dto;

import com.ddnsking.linktag.domain.Link;
import com.ddnsking.linktag.domain.User;

public record CreateLinkRequest(String title, String url, String description) {
    public Link toEntity(User createdBy) {
        return Link.builder()
                .title(title)
                .url(url)
                .description(description)
                .createdBy(createdBy)
                .build();
    }
}
