package com.ddnsking.linktag.repository;

import com.ddnsking.linktag.domain.Link;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    @EntityGraph(attributePaths = {"tags", "createdBy"})
    List<Link> findAll();

    @EntityGraph(attributePaths = {"tags", "createdBy"})
    List<Link> findByTags_Name(String tagName);
}
