import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Solution_differentShapes_whereRotationAndFlipping_doNotMatter {

  public static void main(String[] args) {

    Solution_differentShapes_whereRotationAndFlipping_doNotMatter sol = new Solution_differentShapes_whereRotationAndFlipping_doNotMatter();

    int[][] matrix_one = {
      {1, 0, 0, 0, 0},
      {0, 0, 1, 1, 0},
      {0, 1, 1, 0, 0},
      {0, 0, 0, 0, 0},
      {1, 1, 0, 1, 1},
      {1, 1, 0, 1, 1}
    };

    System.out.println("result for matrix_one: " + sol.solve(matrix_one) + " different shapes");
    System.out.println("matrix_one: ");
    sol.print();
    System.out.println("==================");

    int[][] matrix_two = {
      {1, 0, 0, 0, 0},
      {0, 0, 1, 1, 0},
      {0, 1, 1, 0, 0},
      {0, 0, 0, 0, 0},
      {1, 0, 0, 1, 1},
      {1, 1, 0, 1, 1},
      {1, 1, 0, 1, 0}
    };

    System.out.println("result for matrix_two: " + sol.solve(matrix_two) + " different shapes");
    System.out.println("matrix_two: ");
    sol.print();
    System.out.println("==================");

    int[][] matrix_three = {
      {1, 1, 0, 1},
      {1, 0, 1, 1}
    };

    System.out.println("result for matrix_three: " + sol.solve(matrix_three) + " different shapes");
    System.out.println("matrix_three: ");
    sol.print();
    System.out.println("==================");

    int[][] matrix_four = {
      {1, 1, 0, 1, 1},
      {1, 1, 0, 1, 1},
      {0, 1, 0, 1, 0}
    };

    System.out.println("result for matrix_four: " + sol.solve(matrix_four) + " different shapes");
    System.out.println("matrix_four: ");
    sol.print();
    System.out.println("==================");
  }

  /* These values store the four outermost positions(left, right, up, down) of the '1's
  contained in each shape, so that later we can recreate the smallest possible rectangular
  matrix that contains these shapes.
  */
  public int upMost, downMost, leftMost, rightMost;
  public int[][] matrix;
  public boolean[][] visited;
  public List<int[][]> recreatedShapes;
  public int[][] moves = {
    {-1, 0}, // up
    {1, 0}, // down
    {0, -1}, // left
    {0, 1} // right
  };

  public int solve(int[][] matrix) {
    if (matrix.length == 0) {
      return 0;
    }
    this.matrix = matrix;
    map_smallestPossibleRectangularMatrix_containingEachShape();
    return recreatedShapes.size() > 0 ? getNumberOfUniqueShapes() : 0;
  }

  public void map_smallestPossibleRectangularMatrix_containingEachShape() {
    visited = new boolean[matrix.length][matrix[0].length];
    recreatedShapes = new ArrayList<int[][]>();

    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        if (matrix[r][c] == 1 && !visited[r][c]) {
          bfs_extractOutermostCoordinates(r, c);
          recreate_smallestPossibleRectangularMatrix_thatContainsTheShape();
        }
      }
    }
  }

  public int getNumberOfUniqueShapes() {

    int uniqueShapes = 0;

    for (int i = 0; i < recreatedShapes.size() - 1; i++) {
      boolean unique = true;
      for (int j = i + 1; j < recreatedShapes.size(); j++) {
        if (!haveDifferentShapes(recreatedShapes.get(i), recreatedShapes.get(j))) {
          unique = false;
          break;
        }
      }

      if (unique) {
        uniqueShapes++;
      }
    }

    return uniqueShapes + 1;
  }

  //  Maximum 2x4 comparisons are made. Any time a match is found, the method returns 'false'.
  public boolean haveDifferentShapes(int[][] first, int[][] second) {

    // Two possible states are examined: one side facing towards us, then the other.
    int flipShapeOnTheOtherSide = 0;
    while (true) {

      // Four possible states are examined: rotation clockwise at 90 degrees.
      int rotateShapeClockwise_90degrees = 0;
      while (true) {

        boolean unique = true;
        if (first.length == second.length && first[0].length == second[0].length) {

          for (int r = 0; r < first.length; r++) {
            for (int c = 0; c < second[0].length; c++) {
              if (first[r][c] != second[r][c]) {
                unique = false;
              }
            }
          }
        } else {
          unique = false;
        }

        if (unique) {
          return false;
        }

        if (rotateShapeClockwise_90degrees++ == 4) {
          break;
        }
        second = rotateShapeClockwise_90degrees(second);
      }

      if (flipShapeOnTheOtherSide++ == 1) {
        break;
      }
      second = flipShapeOnTheOtherSide(second);
    }
    return true;
  }

  /*
  Instead of only the shapes, for easiness of handling the rotations/flippings,
  we extract the smallest possible rectangular matrix that contains the shapes.

  Example:       shape         extracted matrix
                  1 1 1          0 1 1 1
                1 1 1            1 1 1 0

   If the figures have the same shapes, they will have the same amount and position
   of '0's in the smallest possible rectangular matrix that contains them.
   Thus, we compare for a match, all '1's and '0's within these matrixes.
   (depending on the shape, the smallest possible rectangular matrix could contain only '1's)

   Extracting such a rectangular matrix does not add siginifcantly to the space and time complexity
   but it makes a lot more easy to handle the operations and makes the code easier to read, as well.
  */
  public void recreate_smallestPossibleRectangularMatrix_thatContainsTheShape() {
    int rows = downMost - upMost + 1;
    int columns = rightMost - leftMost + 1;
    int[][] smallestPossibleRectangularMatrix = new int[rows][columns];

    int new_r = 0;
    for (int r = upMost; r <= downMost; r++) {
      int new_c = 0;
      for (int c = leftMost; c <= rightMost; c++) {
        smallestPossibleRectangularMatrix[new_r][new_c] = matrix[r][c];
        new_c++;
      }
      new_r++;
    }

    recreatedShapes.add(smallestPossibleRectangularMatrix);
  }

  /*
  If after four rotations there is no match between the two shapes,
  we still can not be sure that they are different!

  Some shapes, when flipped on its other side, no matter how many times
  we rotate them, would not match a figure that has the very same shape,
  but is flipped on the opposite side.

  Example:  figure one        figure two
             1 1 1             1 1 1
             1 1 1             1 1 1
             1 1 1             1 1 1
             0 1 1             1 1 0
  */
  public int[][] flipShapeOnTheOtherSide(int[][] islandMatrix) {

    int[][] flippedShape = new int[islandMatrix.length][islandMatrix[0].length];
    int index = 0;

    for (int r = islandMatrix.length - 1; r >= 0; r--) {
      flippedShape[index++] = islandMatrix[r];
    }
    return flippedShape;
  }

  /*
  Breadth First Search: extracting the four outermost points (up, down, left, right) of the shape.
  */
  public void bfs_extractOutermostCoordinates(int row, int column) {

    upMost = Integer.MAX_VALUE;
    downMost = 0;
    leftMost = Integer.MAX_VALUE;
    rightMost = 0;

    LinkedList<Point> queue = new LinkedList<Point>();
    queue.add(new Point(row, column));
    visited[row][column] = true;

    while (!queue.isEmpty()) {
      Point current = queue.removeFirst();
      int r = current.row;
      int c = current.column;

      if (upMost > r) {
        upMost = r;
      }
      if (downMost < r) {
        downMost = r;
      }
      if (leftMost > c) {
        leftMost = c;
      }
      if (rightMost < c) {
        rightMost = c;
      }

      for (int i = 0; i < moves.length; i++) {
        int new_r = r + moves[i][0];
        int new_c = c + moves[i][1];

        if (isInMatrix(new_r, new_c) && !visited[new_r][new_c] && matrix[new_r][new_c] == 1) {
          queue.add(new Point(new_r, new_c));
          visited[new_r][new_c] = true;
        }
      }
    }
  }

  public int[][] rotateShapeClockwise_90degrees(int[][] island) {
    int height = island[0].length;
    int width = island.length;
    int[][] rotated = new int[height][width];

    for (int c = 0; c < island[0].length; c++) {
      int newRow = c;
      int newColumn = 0;
      for (int r = island.length - 1; r >= 0; r--) {
        rotated[newRow][newColumn++] = island[r][c];
      }
    }
    return rotated;
  }

  public boolean isInMatrix(int row, int column) {
    if (row < 0 || column < 0 || row > matrix.length - 1 || column > matrix[0].length - 1) {
      return false;
    }
    return true;
  }

  public void print() {
    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        System.out.print(matrix[r][c] + " ");
      }
      System.out.println();
    }
  }
}

class Point {
  int row;
  int column;

  public Point(int row, int column) {
    this.row = row;
    this.column = column;
  }
}
