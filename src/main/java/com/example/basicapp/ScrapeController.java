package com.example.basicapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


@Controller
public class ScrapeController {

    @Autowired
  	private SiteRepository siteRepository; // variable responsible for creating the queries with the DB
    
    // Function that loops the listGlobal variable and returns a string tha has the html tags
    private String getListString() {

      List<Site> sites; // Stores all the sites that the DB has
      
      String finalList = ""; // variable that concats all the info showed in the list

      // Loops the sites variable and constructs a string with all the html tags to export to the view page
      if(siteRepository.findAll() != null) {
        sites = siteRepository.findAll();
        for (Site site : sites) {
          finalList += "<form th:action=\"@{/scrapesite}\" method=\"post\"><li><p> SITE: " + site.getUrl() +"</p><p> TÍTULO: " + site.getTitulo() + "</p><input type=\"hidden\" name=\"url\" value=" + site.getUrl() +"/><input name=\"cancel\" type=\"submit\" value=\"Remover\"/></li></form>";
        }
      }

      return finalList;
    }

    // Route for the scrape page
    @GetMapping("/scrape")
    public String scrape(Model model) {
        String result = ""; // variable that will store the fetched title
        String defaultUrl = "https://www.rtp.pt/"; // default url

        // Starts the fecth for the given url, in this page there is a default url
        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect(defaultUrl).get();
     
            // With the document fetched, we use JSoup's title() method to fetch the title
            result = doc.title().toString();
     
          // In case of any IO errors, we want the messages written to the console
          } catch (IOException e) {
            e.printStackTrace();
          }

          
        // Gets the final string to show in the html page
        String finalList = getListString();
          
        
        model.addAttribute("url", "URL: " + defaultUrl); // binds the url to the variable url in the html file
        model.addAttribute("title", "Título: " + result); // binds the result to the variable title in the html file
        model.addAttribute("list", finalList); // binds the final string to the variable list in the html file
        
        return "scrape"; // return the scrape html file as the view
    }

    // Post request reponsible to fetch the title from the give url and stores the data in the global variable listGlobal
    @PostMapping(value = "/scrapesite", params = "submit")
    public String scrapeSubmit(@ModelAttribute("url") String url, Model model) {

      String result = ""; // variable that will store the fetched title
      String finalList = ""; // variable that concats all the info showed in the list

      // if the url is empty returns to the scrape page
      if(url.isEmpty())
      {
        return "scrape";
      }

      // Starts the fetch for the given url, in this page there is a default url
      try {
          // Here we create a document object and use JSoup to fetch the website
          Document doc = Jsoup.connect(url).get();
    
          // With the document fetched, we use JSoup's title() method to fetch the title
          result = doc.title().toString();
    
        // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
          e.printStackTrace();
        }

        // Checks if the url is already stored, if not adds it
        Site s = siteRepository.findByUrl(url);
        if(s == null) {
          siteRepository.insert(new Site(url, result));
        }

        // Gets the final string to show in the html page
        finalList = getListString();

        model.addAttribute("title", ""); // binds the url to the variable url in the html file, in this case I want an empty string 
        model.addAttribute("url", ""); // binds the result to the variable title in the html file, in this case I want an empty string 
        model.addAttribute("list", finalList); // binds the final string to the variable list in the html file 

      return "scrape"; // return the scrape html file as the view
    }

    // Deletes the given url from the listGlobal variable
    @PostMapping(value = "/scrapesite", params = "cancel")
    public String deleteItem(@ModelAttribute("url") String url, Model model) {

      String finalList = ""; // variable that concats all the info showed in the list

      // Sending the url via value param from the input tag adds a symbol and it needs to be removed before the operation
      String urlTrimmed = url.toString().replaceAll(".$", "");

      Site siteToRemove = siteRepository.findByUrl(urlTrimmed);
      siteRepository.delete(siteToRemove);

      // Gets the final string to show in the html page
      finalList = getListString();

      model.addAttribute("title", ""); // binds the url to the variable url in the html file, in this case I want an empty string 
      model.addAttribute("url", ""); // binds the result to the variable title in the html file, in this case I want an empty string 
      model.addAttribute("list", finalList); // binds the final string to the variable list in the html file 

      return "scrape"; // return the scrape html file as the view
    }

}