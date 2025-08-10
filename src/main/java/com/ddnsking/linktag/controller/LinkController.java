package com.ddnsking.linktag.controller;

import com.ddnsking.linktag.dto.CreateLinkRequest;
import com.ddnsking.linktag.dto.LinkResponse;
import com.ddnsking.linktag.dto.UpdateLinkRequest;
import com.ddnsking.linktag.security.CustomUserDetails;
import com.ddnsking.linktag.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @PostMapping
    public String createLink(@ModelAttribute CreateLinkRequest createLinkRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LinkResponse link = linkService.createLink(createLinkRequest, customUserDetails.getUserId());
        return String.format("redirect:/links/%d", link.id());
    }

    @GetMapping("/{id}")
    public String findLinkById(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LinkResponse linkResponse = linkService.findLinkById(id, customUserDetails.getUserId());
        model.addAttribute("link", linkResponse);
        return "link";
    }

    @GetMapping
    public String findAllLinks(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<LinkResponse> links = linkService.findAllLinks(customUserDetails.getUserId());
        model.addAttribute("links", links);
        return "links";
    }

    @PutMapping("/{id}")
    public String updateLink(@PathVariable Long id, @ModelAttribute UpdateLinkRequest updateLinkRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        linkService.updateLink(id, updateLinkRequest, customUserDetails.getUserId());
        return String.format("redirect:/links/%d", id);
    }

    @DeleteMapping("/{id}")
    public String deleteLinkById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        linkService.deleteLinkById(id, customUserDetails.getUserId());
        return "redirect:/links";
    }

    @GetMapping("/new")
    public String showCreateLinkForm() {
        return "link-new";
    }

    @GetMapping("/{id}/edit")
    public String showEditLinkForm(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LinkResponse link = linkService.findLinkById(id, customUserDetails.getUserId());
        model.addAttribute("link", link);
        return "link-edit";
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportAllLinks(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Resource links = linkService.exportAllLinks(customUserDetails.getUserId());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=links.html")
                .contentType(MediaType.TEXT_HTML)
                .body(links);
    }
}
