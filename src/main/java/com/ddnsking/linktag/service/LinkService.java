package com.ddnsking.linktag.service;

import com.ddnsking.linktag.domain.Link;
import com.ddnsking.linktag.dto.CreateLinkRequest;
import com.ddnsking.linktag.dto.LinkResponse;
import com.ddnsking.linktag.dto.UpdateLinkRequest;
import com.ddnsking.linktag.repository.LinkRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;

    public void createLink(CreateLinkRequest createLinkRequest) {
        linkRepository.save(createLinkRequest.toEntity());
    }

    public LinkResponse findLinkById(Long id) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));
        return new LinkResponse(link.getUrl(), link.getTitle(), link.getDescription());
    }

    public List<LinkResponse> findAllLinks() {
        return linkRepository
                .findAll()
                .stream()
                .map(link -> new LinkResponse(link.getUrl(), link.getTitle(), link.getDescription()))
                .toList();
    }

    @Transactional
    public void updateLink(Long id, UpdateLinkRequest updateLinkRequest) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Link not found"));
        link.update(updateLinkRequest);
    }

    public void deleteLinkById(Long id) {
        linkRepository.deleteById(id);
    }
}
