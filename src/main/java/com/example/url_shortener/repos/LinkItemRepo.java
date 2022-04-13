package com.example.url_shortener.repos;

import com.example.url_shortener.domain.LinkItem;
import org.springframework.data.repository.CrudRepository;

public interface LinkItemRepo extends CrudRepository<LinkItem, Long> {
    LinkItem findLinkItemById(int shortURL);
}
