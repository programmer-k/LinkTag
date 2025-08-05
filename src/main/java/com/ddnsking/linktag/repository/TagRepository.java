package com.ddnsking.linktag.repository;

import com.ddnsking.linktag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
