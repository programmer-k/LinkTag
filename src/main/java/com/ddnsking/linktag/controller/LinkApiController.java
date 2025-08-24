package com.ddnsking.linktag.controller;

import com.ddnsking.linktag.dto.CreateLinkRequest;
import com.ddnsking.linktag.dto.LinkResponse;
import com.ddnsking.linktag.dto.UpdateLinkRequest;
import com.ddnsking.linktag.security.CustomUserDetails;
import com.ddnsking.linktag.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
@Tag(name = "Links", description = "Endpoints for managing user links")
public class LinkApiController {
    private final LinkService linkService;

    @Operation(summary = "Create a new link", description = "Creates a new link for the user.")
    @PostMapping
    public ResponseEntity<LinkResponse> createLink(@RequestBody CreateLinkRequest createLinkRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LinkResponse link = linkService.createLink(createLinkRequest, customUserDetails.getUserId());
        return ResponseEntity
                .created(URI.create(String.format("/api/links/%d", link.id())))
                .body(link);
    }

    @Operation(summary = "Find link by ID", description = "Retrieves a link by its ID for the user.")
    @GetMapping("/{id}")
    public ResponseEntity<LinkResponse> findLinkById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LinkResponse linkResponse = linkService.findLinkById(id, customUserDetails.getUserId());
        return ResponseEntity.ok(linkResponse);
    }

    @Operation(summary = "Find all links", description = "Retrieves all links for the user.")
    @GetMapping
    public ResponseEntity<List<LinkResponse>> findAllLinks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String tag,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<LinkResponse> links = linkService.findAllLinks(customUserDetails.getUserId(), title, username, tag);
        return ResponseEntity.ok(links);
    }

    @Operation(summary = "Update link", description = "Updates the link by ID for the user.")
    @PutMapping("/{id}")
    public ResponseEntity<LinkResponse> updateLink(@PathVariable Long id, @RequestBody UpdateLinkRequest updateLinkRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        linkService.updateLink(id, updateLinkRequest, customUserDetails.getUserId());
        return ResponseEntity.ok(linkService.findLinkById(id, customUserDetails.getUserId()));
    }

    @Operation(summary = "Delete link", description = "Deletes the link by ID for the user.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLinkById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        linkService.deleteLinkById(id, customUserDetails.getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Export all links", description = "Exports all links for the user to a HTML bookmark file.")
    @GetMapping("/export")
    public ResponseEntity<Resource> exportAllLinks(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Resource links = linkService.exportAllLinks(customUserDetails.getUserId());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=links.html")
                .contentType(MediaType.TEXT_HTML)
                .body(links);
    }

    @Operation(summary = "Import all links", description = "Imports all links from a HTML bookmark file for the user.")
    @PostMapping("/import")
    public ResponseEntity<Void> importAllLinks(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        linkService.importAllLinks(new String(file.getBytes(), StandardCharsets.UTF_8), customUserDetails.getUserId());
        return ResponseEntity.ok().build();
    }
}
