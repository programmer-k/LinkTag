package com.ddnsking.linktag.service;

import com.ddnsking.linktag.domain.Link;
import com.ddnsking.linktag.domain.Tag;
import com.ddnsking.linktag.domain.User;
import com.ddnsking.linktag.dto.CreateLinkRequest;
import com.ddnsking.linktag.dto.LinkResponse;
import com.ddnsking.linktag.dto.UpdateLinkRequest;
import com.ddnsking.linktag.repository.LinkRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final TagService tagService;
    private final UserService userService;
    private final LinkRepository linkRepository;
    private final TemplateEngine templateEngine;

    @Transactional
    public LinkResponse createLink(CreateLinkRequest createLinkRequest, Long userId) {
        User user = userService.findUserByIdOrThrow(userId);

        List<Tag> tags = tagService.findOrCreateAll(createLinkRequest.parseTags());
        Link link = createLinkRequest.toEntity(user, tags);
        tags.forEach(tag -> tag.getLinks().add(link));

        Link savedLink = linkRepository.save(link);
        return new LinkResponse(savedLink.getId(),
                savedLink.getTitle(),
                savedLink.getUrl(),
                savedLink.getDescription(),
                savedLink.getTags().stream().map(Tag::getName).toList(),
                savedLink.getIsPublic()
        );
    }

    @Transactional(readOnly = true)
    public LinkResponse findLinkById(Long id, Long userId) {
        userService.findUserByIdOrThrow(userId);
        Link link = linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));

        if (!(link.getIsPublic() || link.getCreatedBy().getId().equals(userId))) {
            throw new AccessDeniedException("Access denied");
        }

        return new LinkResponse(link.getId(),
                link.getTitle(),
                link.getUrl(),
                link.getDescription(),
                link.getTags().stream().map(Tag::getName).toList(),
                link.getIsPublic()
        );
    }

    @Transactional(readOnly = true)
    public List<LinkResponse> findAllLinks(Long userId, String title, String username, String tag) {
        userService.findUserByIdOrThrow(userId);

        return linkRepository
                .findAll()
                .stream()
                .filter(link -> link.getIsPublic() || link.getCreatedBy().getId().equals(userId))
                .filter(link -> title == null || title.isEmpty() || link.getTitle().contains(title))
                .filter(link -> username == null || username.isEmpty() || link.getCreatedBy().getUsername().equals(username))
                .filter(link -> tag == null || tag.isEmpty() || link.getTags().stream().map(Tag::getName).anyMatch(name -> name.equals(tag)))
                .map(link -> new LinkResponse(link.getId(),
                        link.getTitle(),
                        link.getUrl(),
                        link.getDescription(),
                        link.getTags().stream().map(Tag::getName).toList(),
                        link.getIsPublic()))
                .toList();
    }

    @Transactional
    public void updateLink(Long id, UpdateLinkRequest updateLinkRequest, Long userId) {
        userService.findUserByIdOrThrow(userId);
        Link link = linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));

        if (!link.getCreatedBy().getId().equals(userId))
            throw new AccessDeniedException("User is not the owner of this link.");

        List<Tag> tags = tagService.findOrCreateAll(updateLinkRequest.parseTags());
        List<Tag> oldTags = link.update(updateLinkRequest, tags);

        for (Tag oldTag : oldTags) {
            tagService.deleteIfOrphan(oldTag);
        }
    }

    @Transactional
    public void deleteLinkById(Long id, Long userId) {
        userService.findUserByIdOrThrow(userId);
        Link link = linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));

        if (!link.getCreatedBy().getId().equals(userId))
            throw new AccessDeniedException("User is not the owner of this link.");

        List<Tag> tags = link.getTags();
        tags.forEach(tag -> tag.getLinks().remove(link));
        linkRepository.deleteById(id);

        for (Tag tag : tags) {
            tagService.deleteIfOrphan(tag);
        }
    }

    @Transactional(readOnly = true)
    public ByteArrayResource exportAllLinks(Long userId) {
        userService.findUserByIdOrThrow(userId);

        List<Link> links = linkRepository.findAll()
                .stream()
                .filter(link -> link.getIsPublic() || link.getCreatedBy().getId().equals(userId))
                .toList();

        Context context = new Context();
        context.setVariable("links", links);

        String html = templateEngine.process("links-export", context);
        return new ByteArrayResource(html.getBytes(StandardCharsets.UTF_8));
    }

    @Transactional
    public void importAllLinks(String html, Long userId) {
        userService.findUserByIdOrThrow(userId);

        Document document = Jsoup.parse(html);
        Elements links = document.select("a");

        for (Element link : links) {
            String title = link.text();
            String url = link.attr("href");

            createLink(new CreateLinkRequest(title, url, "", "", false), userId);
        }
    }
}
