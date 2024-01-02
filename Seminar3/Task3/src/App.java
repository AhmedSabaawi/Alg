import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        Set<String> wordList = new HashSet<>(Arrays.asList("this", "two", "fat", "that"));
        WordPuzzleSolver solver = new WordPuzzleSolver(wordList);

        char[][] puzzle = {
                { 't', 'h', 'i', 's' },
                { 'w', 'a', 't', 's' },
                { 'o', 'a', 'h', 'g' },
                { 'f', 'g', 'd', 't' }
        };

        solver.findWords(puzzle);
    }

}
