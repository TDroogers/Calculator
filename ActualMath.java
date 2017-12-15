package nl.drogecode.calculator;

import javafx.scene.control.Alert;

public class ActualMath extends ResolveBase
{

  private static double tempi = 0, prev = 0;
  protected static double result = 0;
  private static boolean start = true;
  private static String temps = "";

  protected static double doMathLoop(String math)
  {
    tempi = 0;
    prev = 0;
    result = 0;
    int i;
    start = true;
    Boolean punc = false;
    String operator = "";
    temps = "";
    String[] ary = math.split("");
    for (i = 0; i < ary.length; i++)
    {
      if (ary[i].matches("\\d") || ary[i].equals(".") || ary[i].equals(","))
      {
        if (ary[i].equals(".") || ary[i].equals(","))
        {
          if (ary[i].equals(","))
          {
            ary[i] = ".";
          }
          if (ary[i].equals(".") && temps.isEmpty())
          {
            temps = "0";
          }
          if (punc.equals(true))
          {
            System.out.println("punc is: " + punc);
            continue;
          }
          punc = true;
        }
        temps = temps + ary[i];
        tempi = Double.parseDouble(temps);
        // System.out.println("tempi: " + tempi);
      }
      else
      {
        if (!pri.containsKey(ary[i].codePointAt(0)))
        {
          System.out.println("continue on: " + ary[i]);
          continue;
        }
        notNumber(operator);

        operator = CalcNext(ary[i]);

        prev = tempi;
        temps = "";
        tempi = 0;
        punc = false;
      }
    }
    notNumber(operator);
    if (start == true)
    {
      return Double.parseDouble(math);
    }
    else
    {
      return result;
    }
  }

  private static void notNumber(String operator)
  {
    if (!operator.equals(""))
    {
      if (start == true)
      {
        result = prev;
        start = false;
      }
      CalcDo(operator);
    }
  }

  private static void CalcDo(String operator)
  {
    prev = result;
    switch (operator)
    {
      case "+":
      {
        result = prev + tempi;
        System.out.println(prev + " + " + tempi + " = " + result);
        break;
      }

      case "-":
      {
        result = prev - tempi;
        System.out.println(prev + " - " + tempi + " = " + result);
        break;
      }

      case "*":
      {
        result = prev * tempi;
        System.out.println(prev + " * " + tempi + " = " + result);
        break;
      }

      case "/":
      {
        if (tempi == 0)
        {
          result = 0;
          System.out.println(prev + " / " + tempi + "= ERROR, niet delen door 0!");
        }
        else
        {
          result = prev / tempi;
          System.out.println(prev + " / " + tempi + " = " + result);
        }
        break;
      }
    }
    operator = "";
  }

  private static String CalcNext(String a)
  {
    String wat = null;
    switch (a)
    {
      case "+":
      {
        wat = "+";
        break;
      }
      case "-":
      {
        wat = "-";
        break;
      }
      case "*":
      {
        wat = "*";
        break;
      }
      case "/":
      {
        wat = "/";
        break;
      }
      default:
      {
        // This positions shoeldn't be reached if priorityRules() is configerued correct.
        Alert alert = new Alert(Alert.AlertType.ERROR, a + " Is not in priority");
        alert.showAndWait();
        break;
      }
    }
    return wat;
  }
}
