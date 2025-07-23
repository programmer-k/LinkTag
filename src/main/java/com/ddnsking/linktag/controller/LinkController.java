package com.ddnsking.linktag.controller;

import com.ddnsking.linktag.dto.CreateLinkRequest;
import com.ddnsking.linktag.dto.LinkResponse;
import com.ddnsking.linktag.dto.UpdateLinkRequest;
import com.ddnsking.linktag.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @PostMapping
    public String createLink(@RequestBody CreateLinkRequest createLinkRequest) {
        linkService.createLink(createLinkRequest);
        return "redirect:/links";   // TODO
    }

    @GetMapping("/{id}")
    public String findLinkById(@PathVariable Long id) {
        LinkResponse linkResponse = linkService.findLinkById(id);
        return "redirect:/links";   // TODO
    }

    @GetMapping
    public String findAllLinks() {
        List<LinkResponse> links = linkService.findAllLinks();
        return "links";
    }

    @PutMapping("/{id}")
    public String updateLink(@PathVariable Long id, @RequestBody UpdateLinkRequest updateLinkRequest) {
        linkService.updateLink(id, updateLinkRequest);
        return "redirect:/links";   // TODO
    }

    @DeleteMapping("/{id}")
    public String deleteLinkById(@PathVariable Long id) {
        linkService.deleteLinkById(id);
        return "redirect:/links";   // TODO
    }
}
