package nl.drogecode.calculator;

import java.util.LinkedList;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ObjList extends ResolveTemp
{
  // class variable
  private  LinkedList<Map<String, Double>> names = new LinkedList<>();
   String result = "0";
   Label lblRes;

  public  void setLbl(Label lblResult)
  {
    lblRes = lblResult;
  }

  public  void keyPressed(int value, int position)
  {
    System.out.println("");
    if (value == 8)
    {
      removePositon(position);
    }
    else
    {
      position--;
      offerPosition(value, position);
    }
    tempResolve(value);
  }

  public  void addKey(int value)
  {
    try
    {
      keyPressed(value, names.size() + 1);
    }
    catch (Exception e)
    {
      System.out.println("add key failed: " + e);
    }
  }

  public  void getMap()
  {
    System.out.println("content: " + names);
  }

  public  void getString(TextField text1)
  {
    String newText = "";
    for (@SuppressWarnings("rawtypes")
    Map a : names)
    {
      char b = (char) (int) (double) a.get("value");
      newText += b;
    }
    System.out.println(newText);
    text1.setText(newText);
  }

  public  void empty()
  {
    names.clear();
  }

  protected  boolean offerPosition(int value, int position)
  {
    int type = getType(value);
    if (type == 0)
    {
      return false;
    }
    try
    {
      names.add(position, Node.Nodee((double) value, (double) type));
      // System.out.println("content: " + names);
      return true;
    }
    catch (IndexOutOfBoundsException e)
    {
      System.out.println("error: " + e);
      return false;
    }
  }

  protected  boolean removePositon(int position)
  {
    try
    {
      if (names.size() > 0)
      {
        names.remove(position);
      }
      return true;
    }
    catch (Exception e) // IndexOutOfBoundsException
    {
      System.out.println("error: " + e);
      return false;
    }
  }

  private  int getType(int value)
  {
    priorityRules();
    if (pri.containsKey(value))
    {
      return pri.get(value);
    }
    return 0;
  }

  private  void tempResolve(int value)
  {
    setRes(names);
    result = getResult(value);
    if (!result.equals("false"))
    {
      lblRes.setText(result);
    }
    else
    {
      String old = lblRes.getText();
      if (!old.startsWith("old"))
      {
        lblRes.setText("old: " + old);
      }
    }
  }
}
