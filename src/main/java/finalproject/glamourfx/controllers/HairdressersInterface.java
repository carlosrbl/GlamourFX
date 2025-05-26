/**
 * @author Adrían
 * This class contains the user interface controller for the hairdressers section
 */

package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Hairdresser;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HairdressersInterface implements Initializable, ButtonCursor {

    static List<Hairdresser> hairdressers;

    @FXML
    private TextField hairdresserName;

    @FXML
    private TextField hairdresserStars;

    @FXML
    private ListView<Hairdresser> hairdressersList;

    @FXML
    private ChoiceBox<String> hairdresserOrder;

    @FXML
    private Label errorLabel;

    @FXML
    private Label errorLabelFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadHairdressers();
        applyStyle();
        setupMouseEvents();

        hairdressersList.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            hairdresserName.setText(newValue.getName());
            hairdresserStars.setText(newValue.getStars()+"");
        });

        String[] orders = {"Name", "Stars", "Name (inverted)", "Stars (inverted)"};
        hairdresserOrder.setItems(FXCollections.observableArrayList(Arrays.asList(orders)));
        hairdresserOrder.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> showOrderedBy(hairdresserOrder.getValue()));
    }

    public void applyStyle()
    {
        hairdressersList.setCellFactory(new Callback<>()
        {
            @Override
            public ListCell<Hairdresser> call(ListView<Hairdresser> param)
            {
                return new ListCell<>()
                {
                    @Override
                    protected void updateItem(Hairdresser item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (empty || item == null)
                        {
                            setText(null);
                        }
                        else
                        {
                            setText(item.toString());
                        }
                        setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 20px;");
                    }
                };
            }
        });
    }

    private void setupMouseEvents() {
        hairdressersList.setOnMouseClicked(this::CursorToHand);
        hairdressersList.setOnMouseExited(this::CursorToDefault);
    }

    private void CursorToHand(MouseEvent event) {
        hairdressersList.setCursor(Cursor.HAND);
    }

    private void CursorToDefault(MouseEvent event)
    {
        hairdressersList.setCursor(Cursor.DEFAULT);
    }

    @FXML
    public void back(ActionEvent event) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/admin.fxml"));
            Parent root = loader.load();

            root.setRotationAxis( Rotate.Y_AXIS);
            root.setRotate(-90);

            AdminInterface controller = loader.getController();
            controller.setClienteName("Admin");

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("GlamourFX");
            stage.setScene(scene);

            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");

            RotateTransition rotate = new RotateTransition(Duration.millis(700), root);
            rotate.setFromAngle(-90);
            rotate.setToAngle(0);

            stage.show();
            rotate.play();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void showOrderedBy(String order)
    {
        switch (order)
        {
            case "Name":
                hairdressers.sort(Comparator.comparing(h -> h.getName().toLowerCase()));
                break;
            case "Stars":
                hairdressers.sort(Comparator.comparingInt(Hairdresser::getStars));
                break;
            case "Name (inverted)":
                hairdressers.sort((h1, h2) -> h2.getName().toLowerCase().compareTo(h1.getName().toLowerCase()));
                break;
            case "Stars (inverted)":
                hairdressers.sort((h1, h2) -> Integer.compare(h2.getStars(), h1.getStars()));
                break;
        }
        hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
    }

    public boolean emptyField()
    {
        return !hairdresserName.getText().isEmpty() && !hairdresserStars.getText().trim().isEmpty();
    }

    public void addHairdresser() {
        if (emptyField()) {
            if (isValidNumber(hairdresserStars.getText()))
            {
                if (!hairdressers.contains(new Hairdresser(hairdresserName.getText(),
                        Integer.parseInt(hairdresserStars.getText()))))
                {
                    if (!(Integer.parseInt(hairdresserStars.getText()) > 5 || Integer.parseInt(hairdresserStars.getText()) < 1))
                    {
                        hairdressers.add(new Hairdresser(hairdresserName.getText(),
                                Integer.parseInt(hairdresserStars.getText())));
                        hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
                        Hairdresser.storeInFile(hairdressers);
                    }
                    else
                    {
                        setErrorStars("The stars are from 1 to 5");
                    }
                }
                else {
                    setErrorFields("This hairdresser already exists.");
                }
            }
            else
            {
                setErrorStars("The number of stars only accept digits.");
            }
        }
        else
        {
            setErrorFields("You must fill all the fields.");
        }
    }

    public void updateHairdresser()
    {
        if (emptyField()) {

                if (isValidNumber(hairdresserStars.getText()))
                {
                    if (!(Integer.parseInt(hairdresserStars.getText()) > 5 || Integer.parseInt(hairdresserStars.getText()) < 1))
                    {
                        hairdressersList.getSelectionModel().getSelectedItem()
                                .setName(hairdresserName.getText());
                        hairdressersList.getSelectionModel().getSelectedItem()
                                .setStars(Integer.parseInt(hairdresserStars.getText()));
                        Hairdresser.storeInFile(hairdressers);
                    }
                    else
                    {
                        setErrorStars("The stars are from 1 to 5");
                    }
            }
            else
            {
                setErrorStars("The number of stars only accept digits.");
            }
        }
        else
        {
            setErrorFields("You must fill all the fields.");
        }

    }

    public void deleteHairdresser ()
    {
        if (hairdressers.contains(hairdressersList.getSelectionModel().getSelectedItem()))
        {
            hairdressers.remove(hairdressersList.getSelectionModel().getSelectedItem());
            hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
            Hairdresser.storeInFile(hairdressers);
        }
        else {
            setErrorFields("This users doesn't exist.");
        }
    }

    public boolean isValidNumber(String number)
    {
        String expression = "^[0-9]+$";
        Pattern p = Pattern.compile(expression);
        Matcher m = p.matcher(number);
        return m.matches();
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
        delay.setOnFinished(_ -> errorLabel.setText(""));
        delay.play();
    }

    public void setErrorFields(String nombre)
    {
        errorLabelFields.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(_ -> errorLabelFields.setText(""));
        delay.play();
    }
    @Override
    public void changeCursorToHand(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.HAND);
    }
    @Override
    public void changeCursorToDefault(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.DEFAULT);
    }
}
