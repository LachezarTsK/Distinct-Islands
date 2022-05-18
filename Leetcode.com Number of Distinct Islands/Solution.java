package numberOfDistinctIslands;

import java.util.HashSet;
import java.util.Set;

public class Solution {

    private static final int WATER = 0;
    private static final int LAND = 1;
    Set<Long> distinctIslands;
    boolean[][] visited;
    int rows;
    int columns;

    public int numDistinctIslands(int[][] grid) {
        rows = grid.length;
        columns = grid[0].length;
        distinctIslands = new HashSet<>();
        visited = new boolean[rows][columns];
        
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                if (!visited[row][column] && grid[row][column] == LAND) {
                    distinctIslands.add(mapIslandWithHashCode(grid, row, column));
                }
            }
        }
        return distinctIslands.size();
    }

    private long mapIslandWithHashCode(int[][] grid, int row, int column) {
        visited[row][column] = true;
        long hashCode = 1;

        if (row - 1 >= 0 && !visited[row - 1][column] && grid[row - 1][column] == LAND) {
            hashCode += 13 * mapIslandWithHashCode(grid, row - 1, column);
        }
        if (row + 1 < rows && !visited[row + 1][column] && grid[row + 1][column] == LAND) {
            hashCode -= 29 * mapIslandWithHashCode(grid, row + 1, column);
        }
        if (column - 1 >= 0 && !visited[row][column - 1] && grid[row][column - 1] == LAND) {
            hashCode += 11 * mapIslandWithHashCode(grid, row, column - 1);
        }
        if (column + 1 < columns && !visited[row][column + 1] && grid[row][column + 1] == LAND) {
            hashCode -= 5 * mapIslandWithHashCode(grid, row, column + 1);
        }
        return hashCode;
    }
}
