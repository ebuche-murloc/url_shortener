package com.example.url_shortener.controller;

import com.example.url_shortener.domain.LinkItem;
import com.example.url_shortener.domain.LogItem;
import com.example.url_shortener.repos.LinkItemRepo;
import com.example.url_shortener.repos.LogItemRepo;
import com.example.url_shortener.service.RequestService;
import com.example.url_shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UrlController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private LogItemRepo logItemRepo;

    @Autowired
    private UrlService urlService;

    @GetMapping("/your_link/{hash}")
    public String main(HttpServletRequest req, @RequestHeader(value = "User-Agent") String userAgent, @PathVariable("hash") String hash, Model model) {
        LinkItem linkItem = urlService.getLinkItem(hash);
        if (linkItem == null)
        {
            return "redirect:http://localhost:8000/?message=this%20link%20doesnt%20exist";
        }
        LogItem logItem = new LogItem(requestService.getClientIp(req), userAgent, linkItem);

        logItemRepo.save(logItem);

        return "redirect:" + linkItem.getLongURL();
    }

    private String MakeShortUrl(String longURL){
        return "http://localhost:8000/" + longURL.substring(longURL.length() - 5);
    }
}
