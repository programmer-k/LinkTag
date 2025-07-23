package com.ddnsking.linktag.domain;

import com.ddnsking.linktag.dto.UpdateLinkRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Link(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public void update(UpdateLinkRequest updateLinkRequest) {
        this.url = updateLinkRequest.url();
        this.title = updateLinkRequest.title();
        this.description = updateLinkRequest.description();
    }
}
