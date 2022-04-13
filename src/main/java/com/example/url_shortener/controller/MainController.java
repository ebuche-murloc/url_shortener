package com.example.url_shortener.controller;

import com.example.url_shortener.domain.LinkItem;
import com.example.url_shortener.domain.LogItem;
import com.example.url_shortener.domain.User;
import com.example.url_shortener.repos.LinkItemRepo;
import com.example.url_shortener.repos.LogItemRepo;
import com.example.url_shortener.repos.UserRepo;
import com.example.url_shortener.service.RequestService;
import com.example.url_shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private LinkItemRepo linkItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LogItemRepo logItemRepo;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UrlService urlService;

    @GetMapping("/")
    public String greeting(@RequestParam(name="message", required=false, defaultValue="World") String message, Model model) {
        model.addAttribute("message", message);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Model model
    ) {
        Iterable<LinkItem> linkItems = linkItemRepo.findAllByAuthor(user);

        List<TempLinkItem> tempLinkItems = new ArrayList<>();
        for (LinkItem lItem : linkItems ) {
            var id = lItem.getId();
            var clickCount = logItemRepo.countByLink(lItem);
            tempLinkItems.add(new TempLinkItem(id, lItem.getLongURL(), urlService.convertToShortUrl(id), clickCount));
        }
        model.addAttribute("links", tempLinkItems);

        return "main";
    }

    @PostMapping("delete")
    public String delete(
            @AuthenticationPrincipal User user,
            @RequestParam int id, Model model
    ) {
        linkItemRepo.deleteById(id);

        Iterable<LinkItem> linkItems = linkItemRepo.findAllByAuthor(user);
        List<TempLinkItem> tempLinkItems = new ArrayList<>();
        for (LinkItem lItem : linkItems ) {
            var idd = lItem.getId();
            var clickCount = logItemRepo.countByLink(lItem);
            tempLinkItems.add(new TempLinkItem(idd, lItem.getLongURL(), urlService.convertToShortUrl(idd), clickCount));
        }
        model.addAttribute("links", tempLinkItems);

        return "main";
    }

    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String longURL, Model model
    ) {
        LinkItem linkItem = new LinkItem(longURL, user);

        linkItemRepo.save(linkItem);

        Iterable<LinkItem> linkItems = linkItemRepo.findAllByAuthor(user);
        List<TempLinkItem> tempLinkItems = new ArrayList<>();
        for (LinkItem lItem : linkItems ) {
            var id = lItem.getId();
            var clickCount = logItemRepo.countByLink(lItem);
            tempLinkItems.add(new TempLinkItem(id, lItem.getLongURL(), urlService.convertToShortUrl(id), clickCount));
        }
        model.addAttribute("links", tempLinkItems);

        return "main";


    }

    private class TempLinkItem {
        public int id;
        public String longUrl;
        public String shortUrl;
        public long linkClickCount;


        public TempLinkItem(int id, String longUrl, String shortUrl, long linkClickCount) {
            this.id = id;
            this.longUrl = longUrl;
            this.shortUrl = shortUrl;
            this.linkClickCount = linkClickCount;
        }

        public long getLinkClickCount() {
            return linkClickCount;
        }

        public void setLinkClickCount(long linkClickCount) {
            this.linkClickCount = linkClickCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLongUrl() {
            return longUrl;
        }

        public void setLongUrl(String longUrl) {
            this.longUrl = longUrl;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }
    }
//    @GetMapping("/{hash}")
//    public String redirect(HttpServletRequest req, @RequestHeader(value = "User-Agent") String userAgent, @PathVariable("hash") String hash, Model model) {
//        LinkItem linkItem = linkItemRepo.findFirstLinkItemByShortURL(hash);
//        if (linkItem == null)
//        {
//            return "redirect:http://localhost:8000/?message=this%20link%20doesnt%20exist";
//        }
//        LogItem logItem = new LogItem(requestService.getClientIp(req), userAgent, linkItem);
//
//
//
//        logItemRepo.save(logItem);
//
//        return "redirect:" + linkItem.getLongURL();
//    }

    private String MakeShortUrl(String longURL){
        return "http://localhost:8000/" + longURL.substring(longURL.length() - 5);
    }
}
