package com.ddnsking.linktag.domain;

import com.ddnsking.linktag.dto.UpdateLinkRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToMany
    @JoinTable(
            name = "link_tag",
            joinColumns = @JoinColumn(name = "link_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Link(String title, String url, String description, User createdBy) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.createdBy = createdBy;
    }

    public List<Tag> update(UpdateLinkRequest updateLinkRequest, List<Tag> tags) {
        this.title = updateLinkRequest.title();
        this.url = updateLinkRequest.url();
        this.description = updateLinkRequest.description();

        List<Tag> oldTags = new ArrayList<>(this.tags);
        for (Tag oldTag : oldTags) {
            oldTag.getLinks().remove(this);
        }

        this.tags.clear();
        this.tags.addAll(tags);
        tags.forEach(tag -> tag.getLinks().add(this));

        return oldTags;
    }
}
