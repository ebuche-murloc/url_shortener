package com.example.url_shortener.controller;

import com.example.url_shortener.domain.LinkItem;
import com.example.url_shortener.domain.LogItem;
import com.example.url_shortener.domain.Role;
import com.example.url_shortener.domain.User;
import com.example.url_shortener.repos.LinkItemRepo;
import com.example.url_shortener.repos.LogItemRepo;
import com.example.url_shortener.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class LogsController {
    @Autowired
    private LogItemRepo logItemRepo;

    @Autowired
    private LinkItemRepo linkItemRepo;

    @GetMapping("checkLinkLogs")
    public String showLogs(@RequestParam int linkId, Model model) {
        LinkItem linkItem = linkItemRepo.findLinkItemById(linkId);
        Iterable<LogItem> logItems = logItemRepo.findAllByLink(linkItem);

        model.addAttribute("logs", logItems);
        return "clickLogs";
    }

}
