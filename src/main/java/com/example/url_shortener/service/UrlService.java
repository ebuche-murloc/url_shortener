package com.example.url_shortener.service;

import com.example.url_shortener.domain.LinkItem;
import com.example.url_shortener.repos.LinkItemRepo;
import com.example.url_shortener.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    @Autowired
    private  LinkItemRepo linkItemRepo;

    @Autowired
    private  BaseConversion conversion;




    public String convertToShortUrl(int linkItemId) {
        return "http://localhost:8000/your_link/" +  conversion.encode(linkItemId);
    }

    public LinkItem getLinkItem(String shortUrl) {
        var id = conversion.decode(shortUrl);

        return linkItemRepo.findLinkItemById(id);
    }
}
