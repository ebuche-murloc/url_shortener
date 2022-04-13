package com.example.url_shortener.repos;

import com.example.url_shortener.domain.LinkItem;
import com.example.url_shortener.domain.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface LinkItemRepo extends CrudRepository<LinkItem, Long> {
    LinkItem findLinkItemById(int shortURL);
    @Transactional
    long deleteById(int id);
    Iterable<LinkItem> findAllByAuthor(User user);
}
