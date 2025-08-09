package com.ddnsking.linktag.dto;

import com.ddnsking.linktag.domain.Link;
import com.ddnsking.linktag.domain.Tag;
import com.ddnsking.linktag.domain.User;

import java.util.Arrays;
import java.util.List;

public record CreateLinkRequest(String title, String url, String description, String tags) {
    public Link toEntity(User createdBy, List<Tag> tags) {
        Link link = Link.builder()
                .title(title)
                .url(url)
                .description(description)
                .createdBy(createdBy)
                .build();
        link.getTags().addAll(tags);
        return link;
    }

    public List<String> parseTags() {
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
