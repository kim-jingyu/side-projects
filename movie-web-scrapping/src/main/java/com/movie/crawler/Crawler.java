package com.movie.crawler;

import com.movie.domain.Movie;
import com.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class Crawler {
    private final MovieRepository movieRepository;

    public List<Movie> getMovies() throws IOException {
        Document document = Jsoup.connect("https://movie.daum.net/ranking/boxoffice/yearly").get();

        List<Movie> movies = new ArrayList<>();

        Elements liElements = document.select("#mainContent > div > div.box_boxoffice > ol > li");

        for (Element liElement : liElements) {
            String title = liElement.select("a[class='link_txt']").text();

            String openDate = liElement.select(".txt_info > .info_txt:nth-child(1) > .txt_num").text();
            String[] openDates = openDate.split("\\.");
            int openYear = 2000 + Integer.parseInt(openDates[0]);
            int openMonth = Integer.parseInt(openDates[1]);
            int openDay = Integer.parseInt(openDates[2]);

            Elements elements = liElement.select(".txt_info > .info_txt:nth-child(2)");
            elements.select(".screen_out").remove();
            char[] values = elements.text().toCharArray();
            String temp = "";
            for (char value : values) {
                if (Character.isDigit(value)) {
                    temp += value;
                }
            }
            int audience = Integer.parseInt(temp);

            String posterUrl = liElement.select(".poster_movie > img").attr("src");

            String posterInfo = "https://movie.daum.net" + liElement.select(".poster_info > a").attr("href");

            double random = Math.random();
            int likes = (int) (random * 100);

            boolean trashed = false;

            Movie movie = new Movie(title, openYear, openMonth, openDay, audience, posterUrl, posterInfo, likes, trashed);
            movieRepository.insert(movie);
            movies.add(movie);
        }

        return movies;
    }
}
