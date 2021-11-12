package com.example.basicapp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

// Class for the sites
@Document
public class Site {
    
    @Id
    private String id;
    @Indexed(unique = true)
    private String url;
    private String titulo;

    public Site() {}

    public Site(String url, String titulo) {
        this.url = url;
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return String.format(
            "Site[id=%s, url='%s', titulo='%s']",
            id, url, titulo);
    }

}
