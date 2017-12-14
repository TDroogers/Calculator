package calculator;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Calculator extends Application
{
  public static void main(String[] args)
  {
    launch(args);
  }

  // first scene
  Label lblResult;
  Label lblTime;

  TextField text1;

  Button calculate;
  Button empty;
  Button add;
  Button minus;
  Button times;
  Button divided;
  Button hOpen;
  Button hClose;
  Button point;

  Scene scene1;
  // class field for stage
  static Stage stage;

  @Override public void start(Stage primaryStage) throws Exception
  {
    stage = primaryStage;

    // Create the label
    lblResult = new Label();
    lblResult.setText("0");
    ObjList objList = new ObjList();
    objList.setLbl(lblResult);

    lblTime = new Label();
    lblTime.setText("0000-00-00 00:00:00");

    // create TextField
    text1 = new TextField();
    text1.requestFocus();
    text1.setOnKeyTyped(e ->
    {
      int c = (e.getCharacter().toCharArray())[0];
      int pos = text1.getCaretPosition();
      objList.keyPressed(c, text1.getCaretPosition());
      // System.out.println(e.getCharacter() + ": " + c + " : " + pos);
      // objList.getMap();
      objList.getString(text1);
      text1.positionCaret(pos);
    });

    // create Buttons;
    InsertButton[] cijfers = new InsertButton[10];
    for (int i = 0; i < 10; i++)
    {
      cijfers[i] = new InsertButton(Integer.toString(i), text1, objList);
    }
    setButtons(objList);
    GridPane grid = ordenGrid(cijfers);

    // exit
    primaryStage.setOnCloseRequest(e ->
    {
      e.consume();
      System.out.println("clean exit :)");
      CloseClick(stage);
    });

    // Add the layout pane to a scene
    scene1 = new Scene(grid, 200, 280);

    primaryStage.setResizable(false);
    primaryStage.setFullScreen(false);
    primaryStage.initStyle(StageStyle.UTILITY);
    primaryStage.setScene(scene1);
    primaryStage.setTitle("Calculator");
    primaryStage.show();

    initiateTimer();
  }

  private void empty(ObjList objList)
  {
    text1.setText("");
    objList.empty();
  }

  public void CloseClick(Stage stage)
  {
    stage.close();
  }

  private void setButtons(ObjList objList)
  {
    add = new InsertButton("+", text1, objList);
    minus = new InsertButton("-", text1, objList);
    times = new InsertButton("*", text1, objList);
    divided = new InsertButton("/", text1, objList);
    hOpen = new InsertButton("(", text1, objList);
    hClose = new InsertButton(")", text1, objList);
    point = new InsertButton(",", text1, objList);
    empty = new Button("em");
    calculate = new InsertButton("=", text1, objList);
    empty.setOnAction(e -> empty(objList));
  }

  private GridPane ordenGrid(InsertButton[] cijfers)
  {
    Region spacer = new Region();
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10));
    grid.setHgap(10);
    grid.setVgap(10);
    grid.addRow(0, lblResult);
    grid.addRow(1, text1);
    for (int i = 0; i < 3; i++)
    {
      grid.addRow(i + 2, cijfers[i * 3 + 1], cijfers[i * 3 + 2], cijfers[i * 3 + 3]);
    }
    grid.addRow(5, point, cijfers[0]);
    grid.addRow(6, hOpen, spacer, hClose);
    // grid.addRow(7, add,minus);
    grid.add(calculate, 4, 2);
    grid.add(empty, 5, 2);
    grid.add(add, 4, 3);
    grid.add(minus, 5, 3);
    grid.add(times, 4, 4);
    grid.add(divided, 5, 4);
    grid.addRow(7, lblTime);

    // alignments and spanning
    GridPane.setColumnSpan(lblResult, 6);
    GridPane.setColumnSpan(text1, 6);
    GridPane.setColumnSpan(lblTime, 6);
    GridPane.setHalignment(lblResult, HPos.CENTER);
    return grid;
  }

  private void initiateTimer()
  {
    Task<Void> task = new Task<Void>()
    {
      @Override protected Void call() throws Exception
      {
        timer();
        return null;
      }
    };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
  }

  private void timer()
  {
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    while (true)
    {
      String timeStamp = sdf.format(new Date());
      // System.out.println("Hello from a thread! " + timeStamp);
      Platform.runLater(new Runnable()
      {
        @Override public void run()
        {
          lblTime.setText(timeStamp);
        }
      });
      try
      {
        Thread.sleep(200); // 1000 milliseconds is one second.
      }
      catch (InterruptedException ex)
      {
        System.out.println("Time thread error: " + ex);
        Thread.currentThread().interrupt();
      }
    }
  }
}
