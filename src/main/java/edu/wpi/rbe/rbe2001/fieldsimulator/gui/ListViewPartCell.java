package edu.wpi.rbe.rbe2001.fieldsimulator.gui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ListViewPartCell extends ListCell<ListViewPart>{

    @FXML
    private Label name;

    @FXML
    private Label numAvailable;

    @FXML
    private HBox box;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(ListViewPart part, boolean empty) {
        super.updateItem(part, empty);
        if(empty || part == null) {

            setText(null);
            setGraphic(null);
        }
        else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxml/partListCell.fxml"));
                mLLoader.setController(this);


                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                name.setText(String.valueOf(part.getName()));
                numAvailable.setText(String.valueOf(part.getNumAvailable()));
                setText(null);
                setGraphic(box);
            }
        }
        }
}
