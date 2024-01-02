import java.util.*;

public class WordPuzzleSolver {
    private Set<String> words;
    private Set<String> prefixes;
    private static final int[][] DIRECTIONS = { 
        // {Row, Column} and each node has 8 directions
            { -1, -1 }, { -1, 0 }, { -1, 1 },
            { 0, -1 }, { 0, 1 },
            { 1, -1 }, { 1, 0 }, { 1, 1 }
    };

    public WordPuzzleSolver(Set<String> wordList) {
        words = new HashSet<>();
        prefixes = new HashSet<>();
        for (String word : wordList) {
            words.add(word);
            for (int i = 1; i <= word.length(); i++) {
                prefixes.add(word.substring(0, i));
            }
        }
    }

    public Set<String> findWords(char[][] grid) {
        Set<String> foundWords = new HashSet<>();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                searchForWords(grid, x, y, foundWords);
            }
        }
        return foundWords;
    }

    private void searchForWords(char[][] grid, int x, int y, Set<String> foundWords) {
        for (int[] direction : DIRECTIONS) {
            StringBuilder word = new StringBuilder();
            int j = x, k = y;
            while (j >= 0 && j < grid.length && k >= 0 && k < grid[j].length) {
                word.append(grid[j][k]);
                String currentWord = word.toString();
                if (!prefixes.contains(currentWord)) {
                    break;
                }
                if (words.contains(currentWord)) {
                    foundWords.add(currentWord);
                }
                j += direction[0];
                k += direction[1];
            }
        }
    }

    public static void main(String[] args) {
        Set<String> wordList = new HashSet<>(Arrays.asList("this", "two", "fat", "that"));
        WordPuzzleSolver solver = new WordPuzzleSolver(wordList);

        char[][] grid = {
                { 't', 'h', 'i', 's' },
                { 'w', 'a', 't', 's' },
                { 'o', 'a', 'h', 'g' },
                { 'f', 'g', 'd', 't' }
        };

        Set<String> foundWords = solver.findWords(grid);
        System.out.println("Found words: " + foundWords);
    }
}
