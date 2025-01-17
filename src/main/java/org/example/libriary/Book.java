package org.example.libriary;

public class Book {
    private String id;
    private String name;
    private String author;
    private String genre;
    private int size;
    private String shelf;

    public Book(String id, String name, String author, String genre, int size, String shelf) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.size = size;
        this.shelf = shelf;
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }
}
