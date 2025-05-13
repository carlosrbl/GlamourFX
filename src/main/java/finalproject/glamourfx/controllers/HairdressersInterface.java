package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Hairdresser;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HairdressersInterface  implements Initializable {

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
    private Button hairdressersAdd;

    @FXML
    private Button hairdressersDelete;

    @FXML
    private ChoiceBox hairdresserOrder;

    @FXML
    private Label errorLabel;

    @FXML
    private Label errorLabelFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadHairdressers();
        hairdressersList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            hairdresserName.setText(newValue.getName());
            hairdresserStars.setText(newValue.getStars()+"");
        });

        String[] orders = {"Name", "Stars"};
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
            case "Name":
                hairdressers.sort((h1, h2) -> h1.getName().compareTo(h2.getName()));
                break;
            case "Stars":
                hairdressers.sort((h1, h2) -> Integer.compare(h1.getStars(), h2.getStars()));
                break;
        }
        hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
    }

    public boolean emptyField()
    {
        return hairdresserName.getText().isEmpty() || hairdresserStars.getText().trim().isEmpty();
    }

    public void addHairdresser(ActionEvent actionEvent) {
        if (!emptyField()) {
            if (!(Integer.parseInt(hairdresserStars.getText()) > 5 || Integer.parseInt(hairdresserStars.getText()) < 1))
            {
                hairdressers.add(new Hairdresser(hairdresserName.getText(),
                        Integer.parseInt(hairdresserStars.getText())));
                hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
                Hairdresser.storeInFile((ArrayList<Hairdresser>) hairdressers);
            }
            else
            {
                setErrorStars("The stars are from 1 to 5");
            }
        }
        else
        {
            setErrorFields("You must fill all the fields.");
        }

    }

    public void updateHairdresser(ActionEvent actionEvent)
    {
        if (!emptyField()) {
            if (!(Integer.parseInt(hairdresserStars.getText()) > 5 || Integer.parseInt(hairdresserStars.getText()) < 1))
            {

                hairdressersList.getSelectionModel().getSelectedItem()
                        .setName(hairdresserName.getText());
                hairdressersList.getSelectionModel().getSelectedItem()
                        .setStars(Integer.parseInt(hairdresserStars.getText()));
                Hairdresser.storeInFile((ArrayList<Hairdresser>) hairdressers);
            }
            else
            {
                setErrorStars("The stars are from 1 to 5");
            }
        }
        else
        {
            setErrorFields("You must fill all the fields.");
        }

    }

    public void deleteHairdresser (ActionEvent actionEvent)
    {
        hairdressers.remove(hairdressersList.getSelectionModel().getSelectedItem());
        hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
        Hairdresser.storeInFile((ArrayList<Hairdresser>) hairdressers);
    }



    public void loadHairdressers()
    {
        hairdressers = Hairdresser.getHairdressers();
        hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
    }

    public void setErrorStars(String nombre)
    {
        errorLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorLabel.setText(""));
        delay.play();
    }

    public void setErrorFields(String nombre)
    {
        errorLabelFields.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorLabelFields.setText(""));
        delay.play();
    }
}
