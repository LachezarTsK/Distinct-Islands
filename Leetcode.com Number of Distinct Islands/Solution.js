
/**
 * @param {number[][]} grid
 * @return {number}
 */
var numDistinctIslands = function (grid) {
    this.WATER = 0;
    this.LAND = 1;
    this.distinctIslands = new Set();
    this.rows = grid.length;
    this.columns = grid[0].length;
    this.visited = Array.from(new Array(this.rows), () => new Array(this.column).fill(false));

    for (let row = 0; row < this.rows; ++row) {
        for (let column = 0; column < this.columns; ++column) {
            if (!this.visited[row][column] && grid[row][column] === this.LAND) {
                this.distinctIslands.add(mapIslandWithHashCode(grid, row, column));
            }
        }
    }
    return this.distinctIslands.size;
};

/**
 * @param {number[][]} grid
 * @param {number} row
 * @param {number} column  
 * @return {number}
 */
function mapIslandWithHashCode(grid, row, column) {
    this.visited[row][column] = true;
    let hashCode = 1;

    if (row - 1 >= 0 && !this.visited[row - 1][column] && grid[row - 1][column] === this.LAND) {
        hashCode += 13 * mapIslandWithHashCode(grid, row - 1, column);
    }
    if (row + 1 < this.rows && !visited[row + 1][column] && grid[row + 1][column] === this.LAND) {
        hashCode -= 29 * mapIslandWithHashCode(grid, row + 1, column);
    }
    if (column - 1 >= 0 && !visited[row][column - 1] && grid[row][column - 1] === this.LAND) {
        hashCode += 11 * mapIslandWithHashCode(grid, row, column - 1);
    }
    if (column + 1 < this.columns && !visited[row][column + 1] && grid[row][column + 1] === this.LAND) {
        hashCode -= 5 * mapIslandWithHashCode(grid, row, column + 1);
    }
    return hashCode;
}