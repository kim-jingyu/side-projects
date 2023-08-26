package com.movie.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
@Getter @Setter
public class Movie {
    @Id
    private String _id;
    private String title;
    private int openYear;
    private int openMonth;
    private int openDay;
    private int audience;
    private String posterUrl;
    private String posterInfo;
    private int likes;

    public Movie(String title, int openYear, int openMonth, int openDay, int audience, String posterUrl, String posterInfo, int likes) {
        this.title = title;
        this.openYear = openYear;
        this.openMonth = openMonth;
        this.openDay = openDay;
        this.audience = audience;
        this.posterUrl = posterUrl;
        this.posterInfo = posterInfo;
        this.likes = likes;
    }
}
