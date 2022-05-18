package agh.ii.jtp.fp;

import agh.ii.jtp.fp.dal.ImdbTop250;
import agh.ii.jtp.fp.model.Movie;
import agh.ii.jtp.fp.utils.Utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface PlayWithMovies {

    List<Movie> movies = ImdbTop250.movies()
            .orElseThrow(() -> new RuntimeException("movies not found"));
    /**
     * Returns the movies (only titles) directed (or co-directed) by a given director
     */
    static Set<String> ex01(String director) {

        return movies
                .stream()
                .filter(movie -> movie.directors().contains(director))
                .map(Movie::title)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the movies (only titles) in which an actor played
     */
    static Set<String> ex02(String actor) {

        return movies
                .stream()
                .filter(movie -> movie.actors().contains(actor))
                .map(Movie::title)
                .collect(Collectors.toSet());
    }

    /**
     * Returns the number of movies per director (as a map)
     */
    static Map<String, Long> ex03() {


        return movies.stream()
                .map(Utils::oneToManyByDirector)
                .flatMap(Collection::stream)
                .map(m -> m.directors().get(0))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        
    }

    /**
     * Returns the 10 directors with the most films on the list
     */
    static Map<String, Long> ex04() {

        return ex03().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    /**
     * Returns the movies (only titles) made by each of the 10 directors found in {@link PlayWithMovies#ex04 ex04}
     */
    static Map<String, Set<String>> ex05() {

        return ex04().keySet()
                .stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> movies.stream()
                                .filter(m -> m.directors().contains(v))
                                .map(Movie::title)
                                .collect(Collectors.toSet())
                ));


    }

    /**
     * Returns the number of movies per actor (as a map)
     */
    static Map<String, Long> ex06() {

        return movies.stream()
                .map(Utils::oneToManyByActor)
                .flatMap(Collection::stream)
                .map(m -> m.actors().get(0))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));


    }

    /**
     * Returns the 9 actors with the most films on the list
     */
    static Map<String, Long> ex07() {

        return ex06().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(9)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

    }

    /**
     * Returns the movies (only titles) of each of the 9 actors from {@link PlayWithMovies#ex07 ex07}
     */
    static Map<String, Set<String>> ex08() {
        return ex07().keySet()
                .stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> movies.stream()
                                .filter(m -> m.actors().contains(v))
                                .map(Movie::title)
                                .collect(Collectors.toSet())
                ));
    }

    /**
     * Returns the 5 most frequent actor partnerships (i.e., appearing together most often)
     */
    static Map<String, Long> ex09() {

        return movies.stream()
                .map(Utils::oneToManyByActorDuo)
                .flatMap(Collection::stream)
                .map(m -> m.actors().get(0))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

    /**
     * Returns the movies (only titles) of each of the 5 most frequent actor partnerships
     */
    static Map<String, Set<String>> ex10() {

        return ex09().keySet()
                .stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> movies.stream()
                                .filter(m -> m.actors().contains(v.split(", ")[0])
                                        && m.actors().contains(v.split(", ")[1]))
                                .map(Movie::title)
                                .collect(Collectors.toSet())

                ));
    }
}


