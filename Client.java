import java.util.*;

public class Client {
    static ArrayList<ArrayList<Integer>> A = new ArrayList<>(Arrays.asList(
        //INPUT MAZE HERE
        new ArrayList<>(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0 )),
        new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0, 0, 1, 0 )),
        new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0, 0, 0, 0 )),
        new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0, 0, 0, 0 )),
        new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0, 0, 0, 0 )),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0 )),
        new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 0, 0, 0 )),
        new ArrayList<>(Arrays.asList(0, 0, 7, 1, 1, 1, 1, 1 ))
    ));

    public static void main(String[] args) {
        ArrayList<String> path = findPath(A);
        System.out.println("Path Coordinates:");
        System.out.println(path);
        System.out.println("\nPath Map (only 1s on the path):");
        printPathOnly(path, A);
    }

    static ArrayList<String> findPath(ArrayList<ArrayList<Integer>> map) {
        int R = map.size(), C = map.get(0).size();
        boolean[][] visited = new boolean[R][C];
        ArrayList<String> path = new ArrayList<>();
        
        // Find a valid starting point (a 1) on the top or left wall
        for (int j = 0; j < C; j++) {
            if (map.get(0).get(j) == 1) {
                if (dfs(0, j, map, visited, path, null, 0, j)) {
                    // After finding the initial path, continue through all 1s in the bottom row
                    int lastRow = R - 1;
                    for (int k = 0; k < C; k++) {
                        if (map.get(lastRow).get(k) == 1) {
                            String coord = "A[" + lastRow + "][" + k + "]";
                            if (!path.contains(coord)) {
                                path.add(coord);
                            }
                        }
                    }
                    return path;
                }
            }
        }
        
        // If no valid path found from top wall, try left wall
        for (int i = 1; i < R; i++) {
            if (map.get(i).get(0) == 1) {
                if (dfs(i, 0, map, visited, path, null, i, 0)) {
                    // After finding the initial path, continue through all 1s in the bottom row
                    int lastRow = R - 1;
                    for (int k = 0; k < C; k++) {
                        if (map.get(lastRow).get(k) == 1) {
                            String coord = "A[" + lastRow + "][" + k + "]";
                            if (!path.contains(coord)) {
                                path.add(coord);
                            }
                        }
                    }
                    return path;
                }
            }
        }
        
        return new ArrayList<>();
    }

    static boolean dfs(int i, int j, ArrayList<ArrayList<Integer>> map, boolean[][] visited, 
                      ArrayList<String> path, Integer prevDir, int startI, int startJ) {
        int R = map.size(), C = map.get(0).size();
        if (i < 0 || i >= R || j < 0 || j >= C) return false;
        if (visited[i][j] || map.get(i).get(j) != 1) return false;

        visited[i][j] = true;
        path.add("A[" + i + "][" + j + "]");

        // If we're on the bottom row and we've made a turn, we're done with the initial path
        if (i == R-1 && hasTurn(path)) {
            return true;
        }

        // Try all four directions
        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for (int d = 0; d < 4; d++) {
            int ni = i + dirs[d][0], nj = j + dirs[d][1];
            if (dfs(ni, nj, map, visited, path, d, startI, startJ)) {
                return true;
            }
        }

        // Backtrack
        visited[i][j] = false;
        path.remove(path.size() - 1);
        return false;
    }

    static boolean hasTurn(ArrayList<String> path) {
        if (path.size() < 3) return false;
        int[] prev = parseCoord(path.get(0));
        int[] cur = parseCoord(path.get(1));
        int prevDir = getDir(prev, cur);
        
        for (int k = 2; k < path.size(); k++) {
            int[] next = parseCoord(path.get(k));
            int dir = getDir(cur, next);
            if (dir != prevDir) return true;
            prev = cur;
            cur = next;
            prevDir = dir;
        }
        return false;
    }

    static int getDir(int[] a, int[] b) {
        if (b[0] == a[0] - 1) return 0; // up
        if (b[0] == a[0] + 1) return 1; // down
        if (b[1] == a[1] - 1) return 2; // left
        if (b[1] == a[1] + 1) return 3; // right
        return -1;
    }

    static int[] parseCoord(String s) {
        int i1 = s.indexOf('['), i2 = s.indexOf(']', i1);
        int j1 = s.indexOf('[', i2), j2 = s.indexOf(']', j1);
        int i = Integer.parseInt(s.substring(i1 + 1, i2));
        int j = Integer.parseInt(s.substring(j1 + 1, j2));
        return new int[]{i, j};
    }

    static void printPathOnly(ArrayList<String> path, ArrayList<ArrayList<Integer>> map) {
        int R = map.size(), C = map.get(0).size();
        Set<String> onPath = new HashSet<>(path);
        for (int i = 0; i < R; i++) {
            System.out.print("[ ");
            for (int j = 0; j < C; j++) {
                String key = "A[" + i + "][" + j + "]";
                System.out.print(onPath.contains(key) ? "1" : " ");
                if (j < C-1) System.out.print(" , ");
            }
            System.out.println(" ]");
        }
    }
}