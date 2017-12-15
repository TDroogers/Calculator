package nl.drogecode.calculator;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InsertButton extends Button
{
  ObjList objList;
  public InsertButton(String inhoud, TextField text1,ObjList objList)
  {
    this.objList = objList;
    setText(inhoud);
    setOnAction(e-> klik(text1));
  }

  private void klik(TextField text1)
  {
    int c = (getText().toCharArray())[0];
    objList.addKey(c);
    objList.getString(text1);
  }
}
