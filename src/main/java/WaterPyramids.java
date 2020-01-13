/*
 *
 *Question link:  Please find problem description in the working directory named WaterPyramids.pdf
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

class CupElement implements Comparable<CupElement>{

  double s, p, f;

  @Override
  public int compareTo(CupElement e) {
    return (int)(this.s - e.s);
  }

}

public class WaterPyramids {
  public static void main(String args[]) {
    long t = System.currentTimeMillis();
    int row = 50;
    int seq = 25;
    Map<Integer, List<CupElement>> elements = new HashMap<>();
    CupElement root = new CupElement();
    root.s = 0;
    root.p = 1;
    root.f = 10;
    List<CupElement> el = new ArrayList<>();
    el.add(root);
    elements.put(0, el);
    System.out.println(" | " + root.f + " | ");
    int lp = 1;
    int rp = lp + 1;
    int id = 1;
    for(int i = 2; i <= row; i++){
      System.out.println("Row:" + i);
      for(int j = 1; j <= i; j++){
        List<Integer> parents = new ArrayList<>();
        if(j == 1){
          parents.add(id - lp);
        } else if(j == i){
          parents.add(id - rp);
        } else {
          parents.add(id - lp);
          parents.add(id - rp);
        }

        Map<Double, CupElement> elMap = new HashMap<>();
        for(int parent : parents){
          for(CupElement c: elements.get(parent)){
            double key = c.f;
            if(elMap.containsKey(key)){
              CupElement ce = elMap.get(key);
              ce.p+=(c.p/2);
              elMap.put(key, ce);
            } else {
              CupElement ce = new CupElement();
              ce.s = c.f;
              ce.p=(c.p/2);
              elMap.put(key, ce);
            }
          }
        }

        Queue<CupElement> elq = new PriorityQueue<>();
        Queue<CupElement> bq = new PriorityQueue<>();
        for(Map.Entry<Double, CupElement> entry : elMap.entrySet()){
          bq.add(entry.getValue());
        }
        List<Double> durations = new ArrayList<>();
        CupElement prev = null;

        while(!bq.isEmpty()){
          CupElement cur = bq.remove();
          if(prev != null){
            durations.add(cur.s - prev.s);
          }
          prev = cur;
          elq.add(cur);
        }

        if(durations.isEmpty()){
          CupElement cur = elq.remove();
          cur.f = cur.s + 10 / cur.p;
          el = new ArrayList<>();
          el.add(cur);
          elements.put(id, el);
          System.out.print(" | " + cur.f);
        }else{
          int idx = 0;
          double sum = 0;
          double remaining = 10;
          double pow = 0;
          double minS = Double.MAX_VALUE;
          while(idx < durations.size()){

            CupElement cur = elq.remove();
            bq.add(cur);
            pow+=cur.p;
            if(minS > cur.s) minS = cur.s;

            double duration = durations.get(idx);

            if(remaining > duration * pow){
              sum += duration;
              remaining -= duration * pow;
            } else {
              sum += remaining / pow;
              remaining = 0;
              break;
            }
            idx++;
          }

          if(remaining > 0){
            while(!elq.isEmpty()){
              CupElement cur = elq.remove();
              bq.add(cur);
              pow+=cur.p;
            }
            sum += remaining / pow;
          }


          el = new ArrayList<>();
          double minF = 0;
          while(!bq.isEmpty()){
            CupElement cur = bq.remove();
            cur.f = minS + sum;
            minF = cur.f;
            el.add(cur);
          }

          System.out.print(" | " + minF);

          while(!elq.isEmpty()){
            CupElement cur = elq.remove();
            cur.f = cur.s;
            el.add(cur);
          }

          elements.put(id, el);
        }

        id++;
      }
      lp++;
      rp++;
      if (i % 100 == 0) {
        System.out.println(i);
      }
      System.out.println(" |");
    }

    double minF = Double.MAX_VALUE;

    int key = (row*(row-1))/2+seq-1;

    System.out.println("Key: " + key);

    for(CupElement e : elements.get(key)) if(minF>e.f) minF = e.f;

    System.out.println("This cup (row: " + row + ", seq: "+seq+")will be fuly filled after " + minF + " seconds.");

    System.out.println(System.currentTimeMillis() - t);
  }

}