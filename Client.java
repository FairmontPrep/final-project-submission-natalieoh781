import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    public static ArrayList<ArrayList<Integer>> inputMap = new ArrayList<>(
        Arrays.asList(
            new ArrayList<>(Arrays.asList(1, 0, 0, 1, 0, 0, 0, 0)),
            new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)),
            new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 0)),
            new ArrayList<>(Arrays.asList(9, 0, 0, 1, 0, 0, 0, 0)),
            new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)),
            new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)),
            new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 0, 0, 0)),
            new ArrayList<>(Arrays.asList(1, 0, 0, 1, 1, 1, 1, 1))
        )
    );

    public static void main(String[] args) {
        PathFinder.solveAndPrint(inputMap);
    }
}

class PathFinder {
    public static void solveAndPrint(ArrayList<ArrayList<Integer>> map) {
        ArrayList<String> answerList = findPath(map);
        System.out.println(answerList);
        printPathOnMap(map, answerList);
    }

    public static ArrayList<String> findPath(ArrayList<ArrayList<Integer>> map) {
        int rows = map.size();
        int cols = map.get(0).size();
        boolean[][] visited = new boolean[rows][cols];
        ArrayList<String> path = new ArrayList<>();

        int[] start = findStart(map);
        if (start == null) return path;

        dfs(map, visited, start[0], start[1], path);
        return path;
    }

    private static void dfs(ArrayList<ArrayList<Integer>> map, boolean[][] visited, int r, int c, ArrayList<String> path) {
        int rows = map.size();
        int cols = map.get(0).size();
        if (r < 0 || r >= rows || c < 0 || c >= cols) return;
        if (visited[r][c]) return;
        if (!map.get(r).get(c).equals(1)) return;

        visited[r][c] = true;
        path.add("A[" + r + "][" + c + "]");

        dfs(map, visited, r - 1, c, path);
        dfs(map, visited, r + 1, c, path);
        dfs(map, visited, r, c - 1, path);
        dfs(map, visited, r, c + 1, path);
    }

    private static int[] findStart(ArrayList<ArrayList<Integer>> map) {
        int rows = map.size();
        int cols = map.get(0).size();
        for (int c = 0; c < cols; c++) {
            if (map.get(0).get(c) == 1) return new int[]{0, c};
            if (map.get(rows - 1).get(c) == 1) return new int[]{rows - 1, c};
        }
        for (int r = 0; r < rows; r++) {
            if (map.get(r).get(0) == 1) return new int[]{r, 0};
            if (map.get(r).get(cols - 1) == 1) return new int[]{r, cols - 1};
        }
        return null;
    }

    public static void printPathOnMap(ArrayList<ArrayList<Integer>> map, ArrayList<String> answerList) {
        int rows = map.size();
        int cols = map.get(0).size();
        boolean[][] isPath = new boolean[rows][cols];
        for (String coord : answerList) {
            int l1 = coord.indexOf('[');
            int l2 = coord.indexOf(']', l1);
            int r = Integer.parseInt(coord.substring(l1 + 1, l2));
            int c = Integer.parseInt(coord.substring(coord.indexOf('[', l2) + 1, coord.indexOf(']', l2 + 1)));
            isPath[r][c] = true;
        }
        for (int r = 0; r < rows; r++) {
            System.out.print("[ ");
            for (int c = 0; c < cols; c++) {
                if (isPath[r][c]) {
                    System.out.print("1 ");
                } else {
                    System.out.print("  ");
                }
                if (c < cols - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
}