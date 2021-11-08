package com.example.basicapp;

import java.io.IOException;
import java.util.HashMap;

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
    
    public HashMap<String, String> listGlobal = new HashMap<>();

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
          

        model.addAttribute("url", "URL: " + url);
        model.addAttribute("title", "Título" + result);
        
        return "scrape";
    }

    @PostMapping(value = "/scrapesite", params = "submit")
    public String scrapeSubmit(@ModelAttribute("url") String url, Model model) {

      String result = "";
      String finalList = "";

      if(url.isEmpty())
      {
        return "scrape";
      }

      try {
          // Here we create a document object and use JSoup to fetch the website
          Document doc = Jsoup.connect(url).get();
    
          // With the document fetched, we use JSoup's title() method to fetch the title
          result = doc.title().toString();
    
        // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
          e.printStackTrace();
        }

        if(!listGlobal.containsKey(url))
        {
          listGlobal.put(url, result);
        }

        Object[] urls = listGlobal.keySet().toArray();

        for(int i = 0; i < urls.length; i++) {
          finalList += "<form th:action=\"@{/scrapesite}\" method=\"post\"><li><p> SITE: " + urls[i].toString() +"</p><p> TÍTULO: " + listGlobal.get(urls[i].toString()) + "</p><input type=\"hidden\" name=\"url\" value=" + urls[i].toString() +"/><input name=\"cancel\" type=\"submit\" value=\"Remover\"/></li></form>";
        }

        model.addAttribute("title", "");
        model.addAttribute("url", "");
        model.addAttribute("list", finalList);

      return "scrape";
    }

    @PostMapping(value = "/scrapesite", params = "cancel")
    public String deleteItem(@ModelAttribute("url") String url, Model model) {

      String finalList = "";

      //Sending the url via value param from the input tag, adds a symbol and needs to be removed before the operation
      String urlTrimmed = url.toString().replaceAll(".$", "");

      listGlobal.remove(urlTrimmed);

      Object[] urls = listGlobal.keySet().toArray();

      for(int i = 0; i < urls.length; i++) {
        finalList += "<form th:action=\"@{/scrapesite}\" method=\"post\"><li><p> SITE: " + urls[i].toString() +"</p><p> TÍTULO: " + listGlobal.get(urls[i].toString()) + "</p><input type=\"hidden\" name=\"url\" value=" + urls[i] +"/><input name=\"cancel\" type=\"submit\" value=\"Remover\"/></li></form>";
      }

      model.addAttribute("url", "");
      model.addAttribute("title", "");
      model.addAttribute("list", finalList);
      return "scrape";
    }

}