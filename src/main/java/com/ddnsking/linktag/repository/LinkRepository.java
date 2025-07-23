package com.ddnsking.linktag.repository;

import com.ddnsking.linktag.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
