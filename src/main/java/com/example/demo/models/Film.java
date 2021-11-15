package com.example.demo.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "please fill the name film")
    @Length(max = 255, message = "please fill the name film")
    private String title;

    @NotBlank(message = "please fill the description film")
    @Length(max = 4096, message = "please fill the description film")
    private String description;

    @ElementCollection(targetClass = Genres.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "genre_name", joinColumns = @JoinColumn(name = "genre_id"))
    @Enumerated(EnumType.STRING)
    private Set<Genres> genres;

    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany
    private Set<Rating> rating;

    @OneToMany
    private Set<Comment> comments;

    private Integer view = 0;

    public String getFilename() {
        return filename;
    }


    public Set<Rating> getRating() {
        return rating;
    }

    public void setRating(Set<Rating> rating) {
        this.rating = rating;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Film(String title, String description, User user) {
        this.author = user;
        this.title = title;
        this.description = description;
    }

    public Set<Genres> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public Film() {
    }

    public String getAlias() {
        return "/poster" + '/' + getTitle();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
