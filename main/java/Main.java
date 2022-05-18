import agh.ii.jtp.fp.dal.ImdbTop250;
import agh.ii.jtp.fp.model.Movie;
import agh.ii.jtp.fp.utils.Utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final List<Movie> movies = ImdbTop250.movies()
            .orElseThrow(() -> new RuntimeException("movies not found"));

    public static void main(String[] args) {

        movies.stream()
                .map(Utils::oneToManyByActorDuo)
                .flatMap(Collection::stream)
                .map(Movie::actors)
                .map(l -> l.get(0) + "adasda ")
                .forEach(System.out::println);

    }

}
