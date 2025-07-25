package com.ddnsking.linktag.controller;

import com.ddnsking.linktag.dto.CreateLinkRequest;
import com.ddnsking.linktag.dto.LinkResponse;
import com.ddnsking.linktag.dto.UpdateLinkRequest;
import com.ddnsking.linktag.service.LinkService;
import lombok.RequiredArgsConstructor;
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
    public String createLink(@ModelAttribute CreateLinkRequest createLinkRequest) {
        LinkResponse link = linkService.createLink(createLinkRequest);
        return String.format("redirect:/links/%d", link.id());
    }

    @GetMapping("/{id}")
    public String findLinkById(@PathVariable Long id, Model model) {
        LinkResponse linkResponse = linkService.findLinkById(id);
        model.addAttribute("link", linkResponse);
        return "link";
    }

    @GetMapping
    public String findAllLinks(Model model) {
        List<LinkResponse> links = linkService.findAllLinks();
        model.addAttribute("links", links);
        return "links";
    }

    @PutMapping("/{id}")
    public String updateLink(@PathVariable Long id, @ModelAttribute UpdateLinkRequest updateLinkRequest) {
        linkService.updateLink(id, updateLinkRequest);
        return String.format("redirect:/links/%d", id);
    }

    @DeleteMapping("/{id}")
    public String deleteLinkById(@PathVariable Long id) {
        linkService.deleteLinkById(id);
        return "redirect:/links";
    }

    @GetMapping("/new")
    public String showCreateLinkForm() {
        return "link-new";
    }

    @GetMapping("/{id}/edit")
    public String showEditLinkForm(@PathVariable Long id, Model model) {
        LinkResponse link = linkService.findLinkById(id);
        model.addAttribute("link", link);
        return "link-edit";
    }
}
