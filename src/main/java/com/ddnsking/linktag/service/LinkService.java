package com.ddnsking.linktag.service;

import com.ddnsking.linktag.domain.Link;
import com.ddnsking.linktag.domain.Tag;
import com.ddnsking.linktag.domain.User;
import com.ddnsking.linktag.dto.CreateLinkRequest;
import com.ddnsking.linktag.dto.LinkResponse;
import com.ddnsking.linktag.dto.UpdateLinkRequest;
import com.ddnsking.linktag.repository.LinkRepository;
import com.ddnsking.linktag.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final TagService tagService;
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    @Transactional
    public LinkResponse createLink(CreateLinkRequest createLinkRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Tag> tags = tagService.findOrCreateAll(createLinkRequest.parseTags());
        Link link = createLinkRequest.toEntity(user, tags);
        tags.forEach(tag -> tag.getLinks().add(link));

        Link savedLink = linkRepository.save(link);
        return new LinkResponse(savedLink.getId(),
                savedLink.getTitle(),
                savedLink.getUrl(),
                savedLink.getDescription(),
                savedLink.getTags().stream().map(Tag::getName).toList()
        );
    }

    public LinkResponse findLinkById(Long id) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));
        return new LinkResponse(link.getId(),
                link.getTitle(),
                link.getUrl(),
                link.getDescription(),
                link.getTags().stream().map(Tag::getName).toList()
        );
    }

    public List<LinkResponse> findAllLinks() {
        return linkRepository
                .findAll()
                .stream()
                .map(link -> new LinkResponse(link.getId(),
                        link.getTitle(),
                        link.getUrl(),
                        link.getDescription(),
                        link.getTags().stream().map(Tag::getName).toList()))
                .toList();
    }

    @Transactional
    public void updateLink(Long id, UpdateLinkRequest updateLinkRequest, Long userId) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));

        if (!link.getCreatedBy().getId().equals(userId))
            throw new AccessDeniedException("User is not the owner of this link.");

        link.update(updateLinkRequest);
    }

    @Transactional
    public void deleteLinkById(Long id, Long userId) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));

        if (!link.getCreatedBy().getId().equals(userId))
            throw new AccessDeniedException("User is not the owner of this link.");

        linkRepository.deleteById(id);
    }
}
