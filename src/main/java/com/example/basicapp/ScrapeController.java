package com.example.basicapp;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.jsoup.select.Elements;

@Controller
public class ScrapeController {
    
    @GetMapping("/scrape")
    public String scrape(Model model) {
        String result = "";
        String url = "https://www.rtp.pt/";

        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect(url).get();
     
            // With the document fetched, we use JSoup's title() method to fetch the title
            result = doc.title().toString();
     
          // In case of any IO errors, we want the messages written to the console
          } catch (IOException e) {
            e.printStackTrace();
          }

        model.addAttribute("url", url);
        model.addAttribute("title", result);
        
        return "scrape";
    }

    @PostMapping("/scrape")
    public String scrapeSubmit(@ModelAttribute("url") String url, Model model) {

      String result = "GG LOL";


      try {
          // Here we create a document object and use JSoup to fetch the website
          Document doc = Jsoup.connect(url).get();
    
          // With the document fetched, we use JSoup's title() method to fetch the title
          result = doc.title().toString();
    
        // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
          e.printStackTrace();
        }

        model.addAttribute("title", result);
        model.addAttribute("url", url);

      return "scrape";
    }

}