package com.example.url_shortener.repos;

import com.example.url_shortener.domain.LinkItem;
import com.example.url_shortener.domain.LogItem;
import org.springframework.data.repository.CrudRepository;

public interface LogItemRepo extends CrudRepository<LogItem, Long> {
    long countByLink(LinkItem linkItem);
}
