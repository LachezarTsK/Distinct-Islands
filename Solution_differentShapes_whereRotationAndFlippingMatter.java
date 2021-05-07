import java.util.Set;
import java.util.HashSet;

public class Solution_differentShapes_whereRotationAndFlippingMatter {

  public int[][] matrix;
  public boolean[][] visited;

  /*
  By the problem design on binarysearch.com, we have to work
  around the given method 'public int solve(int[][] matrix)'
  so that the code can be run on the website. Even though the name 'solve'
  does not make a lot of sense, it is left as it is, so that the code can
  be run directly on the website, without any modifications.
  */
  public int solve(int[][] matrix) {
    if (matrix.length == 0) {
      return 0;
    }
    this.matrix = matrix;
    return extractNumberOfDifferentShapes();
  }

  public int extractNumberOfDifferentShapes() {
    visited = new boolean[matrix.length][matrix[0].length];
    Set<Long> numberOfDifferentShapes = new HashSet<Long>();

    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[r].length; c++) {

        if (matrix[r][c] == 1 && !visited[r][c]) {
          numberOfDifferentShapes.add(dfs_calculateHashShape(r, c));
        }
      }
    }
    return numberOfDifferentShapes.size();
  }
  /*
  Depth First Search: calculate a hash code for different shapes.
  @return A long integer, representing the hash code.
  */
  public long dfs_calculateHashShape(int r, int c) {
    visited[r][c] = true;
    long hash = 1;

    if (r - 1 >= 0 && matrix[r - 1][c] == 1 && !visited[r - 1][c]) {
      hash += 5 * dfs_calculateHashShape(r - 1, c);
    }

    if (r + 1 < matrix.length && matrix[r + 1][c] == 1 && !visited[r + 1][c]) {
      hash += 7 * dfs_calculateHashShape(r + 1, c);
    }

    if (c - 1 >= 0 && matrix[r][c - 1] == 1 && !visited[r][c - 1]) {
      hash += 11 * dfs_calculateHashShape(r, c - 1);
    }

    if (c + 1 < matrix[r].length && matrix[r][c + 1] == 1 && !visited[r][c + 1]) {
      hash += - 13 * dfs_calculateHashShape(r, c + 1);
    }
    return hash;
  }
}
