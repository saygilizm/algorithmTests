/*
 *
 *Question link:  https://open.kattis.com/problems/xormax
 *
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class XorMaxGauss {

  private static long xormax(List<Long> values){
    Collections.sort(values, Collections.reverseOrder());

    long max = values.get(0);

    for(long val1 : values){
      long process = val1 ^ max; // this is where the Guass elemination is happening.

      if(process > max) max = process;

      for(long val2 : values){
        if (val1 == val2 ) continue;
        process = val1 ^ val2;

        if(process > max) max = process;
      }
    }

    return max;
  }


  static class Node {
    Node[] links = new Node[2];
    int ind;

    public Node() {
      this.ind = -1;
      links[0] = null;
      links[1] = null;
    }
  }

  static class Tree {
    Node root;
    Tree(){
      root = new Node();
    }

    void insert(int ind, long num){
      Node t = root;
      for(int i = 63; i >= 0; --i){
        int aux = ((num & (1<<i)) == 0 ? 0 : 1);

        if(t.links[aux] == null) t.links[aux] = new Node();
        t = t.links[aux];
        t.ind = ind;
      }
    }

    int findBest(long num){
      Node t = root;
      for(int i = 63; i >= 0; --i){
        int aux = ((num & (1<<i))==0? 1 : 0);

        if(t.links[aux] != null) t = t.links[aux];
        else t = t.links[aux^1];
      }

      return t.ind;
    }
  }

  private static long xorFromInternet(List<Long> values){
    List<Long> m = new ArrayList<>();
    m.add(0l);
    for(int i = 0; i < values.size(); i++){
      m.add(m.get(i) ^ values.get(i));
    }

    long ans = values.get(0);
    long s = 1;
    long e = 1;
    Tree myTree = new Tree();
    myTree.insert(0,0);

    for(int i = 1; i <= values.size(); ++i){
      int ret = myTree.findBest(m.get(i));

      long xor = m.get(i) ^ m.get(ret);
      if( xor > ans){
        ans = xor;
        s = ret + 1;
        e = i;
      }
      myTree.insert(i, m.get(i));
    }

    return ans;

  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    List<Long> values = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      values.add(sc.nextLong());
    }

    System.out.println(xormax(values));
    System.out.println(xorFromInternet(values));

  }

}
