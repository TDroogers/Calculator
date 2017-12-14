package calculator;

import java.util.HashMap;
import java.util.Map;

public class ResolveBase
{

  protected static Map<Integer, Integer> pri;
  // protected static Map<String, Integer> prio;

  protected static void priorityRules()
  {
    pri = new HashMap<>();
    for (Integer x = 0; x < 10; x++)
    {
      pri.put(x + 48, 1); // 48 = 0
    }
    pri.put(44, 2);// ,
    pri.put(46, 2);// .
    pri.put(43, 3);// +
    pri.put(45, 3);// -
    pri.put(47, 4);// /
    pri.put(42, 4);// *
    pri.put(40, 10);// (
    pri.put(41, 10);// )
    // pri.put(61, 10);// =
  }

  protected static class Node
  {
    static Map<String, Double> nodeMap;

    protected static Map<String, Double> Nodee(double value, double type)
    {
      nodeMap = new HashMap<>();
      nodeMap.put("value", value);
      nodeMap.put("type", type);
      return nodeMap;
    }
  }
}
