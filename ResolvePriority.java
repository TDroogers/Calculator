package calculator;

import java.util.LinkedList;
import java.util.Map;

public class ResolvePriority extends ActualMath
{
  private  int hookmax, hook, i, num, previous, prevType;
  private  boolean addEnt;
  private  Map<String, Double> l;
  private LinkedList<Map<String, Double>> list;

  protected  int getHookmax()
  {
    return hookmax;
  }

  protected  boolean priorityLoop(LinkedList<Map<String, Double>> list, int hookm)
  {
    this.list = list;
    hookmax = hookm;
    hook = 0;
    for (i = 9; i > 2; --i)
    {
      previous = 0;
      prevType = 0;
      addEnt = false;
      for (num = 0; num < list.size(); num++)
      {
        if (!forListLoop())
          return false;
      }
      if (addEnt == true)
      {
        list.add(Node.Nodee(41, 10));
        hook--;
      }
    }
    System.out.println("hookmax: " + hookmax);
    if (hook == 0)
    {
      return true;
    }
    else
    {
      System.out.println("Hook count not 0: " + hook);
      return false;
    }
  }

  private  boolean forListLoop()
  {
    l = list.get(num);
    Double type = l.get("type");
    double value = l.get("value");
    if (value == 40)
    {
      if (!hookOpen())
        return false;
    }
    else if (value == 41)
    {
      if (!hookClose())
        return false;
    }
    else if (hook <= 0 && type < i && type > 2)
    {
      endHookSet();
    }
    if (type.intValue() == i)
    {
      startHookSet();
    }

    if (hook < 0)
    {
      System.out.println("Hook count below 0: " + hook);
      return false;
    }

    if (hook > hookmax)
    {
      hookmax = hook;
    }
    prevType = type.intValue();
    return true;
  }

  private  boolean hookOpen()
  {
    Double next;
    try
    {
      next = (Double) list.get(num + 1).get("type");
    }
    catch (IndexOutOfBoundsException E)
    {
      return false;
    }
    if (next != 1 && next != 2 && next != 10)
    {
      System.out.println("type !=1 && != 2 && !=10: " + next);
      return false;
    }
    hook++;
    return true;
  }

  private  boolean hookClose()
  {
    Double next;
    if (prevType != 1 && prevType != 2 && prevType != 10)
    {
      System.out.println("prevType !=1 && != 2 && !=10: " + prevType);
      return false;
    }
    try
    {
      next = (Double) list.get(num + 1).get("type");
      if (next == 1 || next == 2 || next == 10)
      {
        System.out.println("next: " + next);
        list.add(num + 1, Node.Nodee(42, 4)); // *
      }
    }
    catch (IndexOutOfBoundsException E)
    {
      // No need
    }
    
    
    hook--;
    return true;
  }

  private  void endHookSet()
  {
    if (addEnt == true)
    {
      addEnt = false;
      list.add(num, Node.Nodee(41, 10));
      hook--;
    }
    previous = num + 1;
  }

  private  void startHookSet()
  {
    // System.out.println("map: " + l);
    if (addEnt == false)
    {
      // insert ( after previeus
      list.add(previous, Node.Nodee(40, 10));
      hook++;
      addEnt = true;
    }
  }
}
