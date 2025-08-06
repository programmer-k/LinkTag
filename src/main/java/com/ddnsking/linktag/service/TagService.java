package com.ddnsking.linktag.service;

import com.ddnsking.linktag.domain.Tag;
import com.ddnsking.linktag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    Tag findOrCreate(String tagName) {
        return tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
    }

    @Transactional
    List<Tag> findOrCreateAll(List<String> tagNames) {
        return tagNames.stream()
                .map(this::findOrCreate)
                .toList();
    }
}
