package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Hairdresser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HairdressersInterface implements Initializable {

    static List<Hairdresser> hairdressers;

    @FXML
    private TextField hairdresserName;

    @FXML
    private TextField hairdresserStars;

    @FXML
    private ListView<Hairdresser> hairdressersList;

     @FXML
     private Button hairdressersBack;

    @FXML
    private Button hairdressersUpdate;

    @FXML
    private Button hairdressersCancel;

    @FXML
    private Button hairdressersDelete;

    @FXML
    private ChoiceBox hairdresserOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadHairdressers();
        hairdressersList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            hairdresserName.setText(newValue.getName());
            hairdresserStars.setText(newValue.getStars()+"");
        });

        String[] orders = {"name", "stars"};
        hairdresserOrder.setItems(FXCollections.observableArrayList(Arrays.asList(orders)));
        hairdresserOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showOrderedBy(hairdresserOrder.getValue().toString());
        });
    }

    @FXML
    private void back(ActionEvent actionEvent) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/admin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("GlamourFX");
            stage.setScene(scene);

            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");

            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showOrderedBy(String order)
    {
        switch (order)
        {
            case "name":
        }
    }

    public void loadHairdressers()
    {
        hairdressers = Hairdresser.getHairdressers();
        hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
    }
}
