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
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Link(String title, String url, String description) {
        this.title = title;
        this.url = url;
        this.description = description;
    }

    public void update(UpdateLinkRequest updateLinkRequest) {
        this.title = updateLinkRequest.title();
        this.url = updateLinkRequest.url();
        this.description = updateLinkRequest.description();
    }
}
