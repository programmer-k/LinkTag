package com.ddnsking.linktag.dto;

import java.util.List;

public record LinkResponse(Long id, String title, String url, String description, List<String> tags, Boolean isPublic) {
}
