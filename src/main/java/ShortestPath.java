/*
*
*
* https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
*
*
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class QItem {
  int row;
  int col;
  int dist;
  int k;
  Map<String, Integer[]> visited;
  QItem(int x, int y, int w, int k, Map<String, Integer[]> map){
    row = x;
    col = y;
    dist = w;
    this.k = k;
    visited = map;
    visited.put(String.valueOf(row).concat(",").concat(String.valueOf(col)), new Integer[]{dist, k});
  }
}

class ShortestPath {
  public int shortestPath(int[][] grid, int k) {
    QItem source = new  QItem(0, 0, 0, k, new HashMap<>());
    int M = grid[0].length;
    int N = grid.length;

    boolean[][] obstacle = new boolean[N][M];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        if (grid[i][j] == 0)
          obstacle[i][j] = false;
        else
          obstacle[i][j] = true;
      }
    }

    // applying BFS on matrix cells starting from source
    Queue<QItem> q = new LinkedList<>();
    List<Integer> distList = new ArrayList<>();

    if(obstacle[source.row][source.col] == true){
      k--;
      if(k<0) return -1;
    }
    source.k = k;
    q.add(source);
    while (!q.isEmpty()) {
      QItem p = q.remove();

      // Destination found;
      if (p.row == N-1 && p.col == M-1) {
        distList.add(p.dist);
        continue;
      }


      // moving up
      if (p.row - 1 >= 0 && notExistInMap(p, p.row - 1, p.col)) {
        if(obstacle[p.row - 1][p.col] == false) {
          q.add(new QItem(p.row - 1, p.col, p.dist + 1, p.k, p.visited));
        } else if(p.k > 0) {
          q.add(new QItem(p.row - 1, p.col, p.dist + 1, p.k - 1, p.visited));
        }
      }

      // moving down
      if (p.row + 1 < N && notExistInMap(p, p.row + 1, p.col)) {
        if(obstacle[p.row + 1][p.col] == false) {
          q.add(new QItem(p.row + 1, p.col, p.dist + 1, p.k, p.visited));
        } else if(p.k > 0) {
          q.add(new QItem(p.row + 1, p.col, p.dist + 1, p.k - 1, p.visited));
        }
      }

      // moving left
      if (p.col - 1 >= 0 && notExistInMap(p, p.row, p.col - 1)) {
        if(obstacle[p.row][p.col - 1] == false) {
          q.add(new QItem(p.row, p.col - 1, p.dist + 1, p.k, p.visited));
        } else if(p.k > 0) {
          q.add(new QItem(p.row, p.col - 1, p.dist + 1, p.k - 1, p.visited));
        }
      }

      // moving right
      if (p.col + 1 < M && notExistInMap(p, p.row, p.col + 1)) {
        if(obstacle[p.row][p.col + 1] == false) {
          q.add(new QItem(p.row, p.col + 1, p.dist + 1, p.k, p.visited));
        } else if(p.k > 0) {
          q.add(new QItem(p.row, p.col + 1, p.dist + 1, p.k - 1, p.visited));
        }
      }
    }

    int min = -1;

    for(int i : distList){
      if(min == -1) min = i;
      else if(i < min) min = i;
    }

    return min;
  }

  private boolean notExistInMap(QItem p, int row, int col) {
    String key = String.valueOf(row).concat(",").concat(String.valueOf(col));
    if(p.visited.containsKey(key))
    {
      Integer[] vals = p.visited.get(key);
      if(p.k <= vals[1]) return false;
      else return true;
    }
    return true;
  }

  public static void main(String[] args) {
    ShortestPath sp = new ShortestPath();
    System.out.println(sp.shortestPath(new int[][]
        {   {0,0,1,0,0,0,0,1,0,1,1,0,0,1,1},
            {0,0,0,1,1,0,0,1,1,0,1,0,0,0,1},
            {1,1,0,0,0,0,0,1,0,1,0,0,1,0,0},
            {1,0,1,1,1,1,0,0,1,1,0,1,0,0,1},
            {1,0,0,0,1,1,0,1,1,0,0,1,1,1,1},
            {0,0,0,1,1,1,0,1,1,0,0,1,1,1,1},
            {0,0,0,1,0,1,0,0,0,0,1,1,0,1,1},
            {1,0,0,1,1,1,1,1,1,0,0,0,1,1,0},
            {0,0,1,0,0,1,1,1,1,1,0,1,0,0,0},
            {0,0,0,1,1,0,0,1,1,1,1,1,1,0,0},
            {0,0,0,0,1,1,1,0,0,1,1,1,0,1,0}}, 27));
  }
}