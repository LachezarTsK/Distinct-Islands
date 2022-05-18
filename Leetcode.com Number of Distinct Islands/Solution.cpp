
#include <vector>
#include <unordered_set>
using namespace std;

class Solution {
    
    inline static const int WATER = 0;
    inline static const int LAND = 1;
    unordered_set<unsigned long long> distinctIslands;
    vector<vector<bool>> visited;
    size_t rows;
    size_t columns;

public:
    int numDistinctIslands(vector<vector<int>>& grid) {
        rows = grid.size();
        columns = grid[0].size();
        visited.resize(rows);
        visited.assign(rows, vector<bool>(columns));
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                if (!visited[row][column] && grid[row][column] == LAND) {
                    distinctIslands.insert(mapIslandWithHashCode(grid, row, column));
                }
            }
        }
        return distinctIslands.size();
    }

private:
    unsigned long long mapIslandWithHashCode(const vector<vector<int>>& grid, int row, int column) {
        visited[row][column] = true;
        unsigned long long hashCode = 1;

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
};