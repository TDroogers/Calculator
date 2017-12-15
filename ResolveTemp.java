package nl.drogecode.calculator;

import java.util.LinkedList;
import java.util.Map;

public class ResolveTemp extends ActualMath
{

  protected  LinkedList<Map<String, Double>> list;
  protected  int hookmax = 0;
   String ch = "";

  protected  void setRes(LinkedList<Map<String, Double>> names)
  {
    list = new LinkedList<Map<String, Double>> (names);
  }

  protected  String getResult(int value)
  {
    ResolvePriority res = new ResolvePriority();
    if (!res.priorityLoop(list, hookmax))
    {
      return prioFalse(value);
    }
    hookmax = res.getHookmax();

    getStringTemp();
    resolveLoop();

    return returnround(result);
  }

  private  void resolveLoop()
  {
    int hook = 0;
    double value = 0;
    System.out.println(hookmax);
    for (int i = hookmax; 0 <= i; i--)
    {
      String math = "";
      ch = "";
      for (int num = 0; num < list.size(); num++)
      {
        Map<String, Double> l = list.get(num);
        double type = l.get("type");
        value = getCh(type, l);
        if (value == 40)
        {
          hook++;
        }
        else if (value == 41)
        {
          if (hook == i && math.length() != 0)
          {
            double inbetween = doMathLoop(math);
            list.add(num + 1, Node.Nodee(inbetween, 0));
            result = inbetween;
            math = "";
            num++;
          }
          hook--;
        }
        else if (hook == i)
        {
          math += ch;
        }
      }
    }
  }

  private  String prioFalse(int value)
  {
    if (value == 61)
    {
      return "ERROR: imput incorrect";
    }
    else
    {
      getStringTemp();
      return "false";
    }
  }

  private  String returnround(double result)
  {
    System.out.println(Math.floor(result));
    if (Math.floor(result) == result)
    {
      return Integer.toString((int) (Math.floor(result)));
    }
    else
    {
      return Double.toString(result);
    }
  }

  private  double getCh(double type, Map<String, Double> l)
  {
    double value = 0;
    if ((int) type == 0)
    {
      double val = l.get("value");
      ch = Double.toString(val);
      value = 0;
    }
    else
    {
      value = l.get("value");
      ch = Character.toString((char) (int) value);
    }
    return value;
  }

  private  void getStringTemp()
  {
    String newText = "";
    for (Map<String, Double> a : list)
    {
      char b = (char) (int) (double) a.get("value");
      newText += b;
    }
    System.out.println(newText);
  }
}
