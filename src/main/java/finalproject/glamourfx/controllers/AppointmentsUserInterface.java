package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Appointment;
import finalproject.glamourfx.data.Hairdresser;
import finalproject.glamourfx.data.Service;
import javafx.animation.PauseTransition;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppointmentsUserInterface implements Initializable {

    static List<Appointment> appointments;

    @FXML
    private ListView<Appointment> appointmentsList;

    @FXML
    private Button datesDelete;

    @FXML
    private ChoiceBox datesOrder;

    @FXML
    private Label datesHairdresser;

    @FXML
    private Label datesService;

    @FXML
    private Label datesPrice;

    @FXML
    private Label datesAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadAppointments();
        applyStyle();
        setupMouseEvents();

        appointmentsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            datesHairdresser.setText(newValue.getHairdresser());
            datesService.setText(newValue.getService());
            datesAppointment.setText(newValue.getTime().toString());
            datesPrice.setText(newValue.getTotalPrice() + "");
        });

        String[] orders = {"Hairdresser", "Service", "Date", "Price"};
        datesOrder.setItems(FXCollections.observableArrayList(Arrays.asList(orders)));
        datesOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showOrderedBy(datesOrder.getValue().toString());
        });
    }

    public void applyStyle()
    {
        appointmentsList.setCellFactory(new Callback<ListView<Appointment>, ListCell<Appointment>>()
        {
            @Override
            public ListCell<Appointment> call(ListView<Appointment> param)
            {
                return new ListCell<Appointment>()
                {
                    @Override
                    protected void updateItem(Appointment item, boolean empty)
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
                        setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                    }
                };
            }
        });
    }

    private void setupMouseEvents() {
        appointmentsList.setOnMouseClicked(this::CursorToHand);
        appointmentsList.setOnMouseExited(this::CursorToDefault);
    }

    private void CursorToHand(MouseEvent event) {
        appointmentsList.setCursor(Cursor.HAND);
    }

    private void CursorToDefault(MouseEvent event) {
        appointmentsList.setCursor(Cursor.DEFAULT);
    }

//    @FXML
//    private void back(ActionEvent actionEvent) {
//        try
//        {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/admin.fxml"));
//            Parent root = loader.load();
//
//            AdminInterface controller = loader.getController();
//            controller.setClienteName("Admin");
//
//            Scene scene = new Scene(root);
//
//            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            stage.setTitle("GlamourFX");
//            stage.setScene(scene);
//
//
//            stage.setFullScreen(true);
//            stage.setFullScreenExitHint("");
//
//            stage.show();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }

    public void showOrderedBy(String order)
    {
        switch (order)
        {
            case "Hairdresser":
                appointments.sort((a1, a2) -> a1.getHairdresser().toLowerCase().compareTo(a2.getHairdresser().toLowerCase()));
                break;
            case "Service":
                appointments.sort((a1, a2) -> a1.getService().toLowerCase().compareTo(a2.getService().toLowerCase()));
                break;
            case "Date":
                appointments.sort((a1, a2) -> a1.getTime().compareTo(a2.getTime()));
                break;
            case "Price":
                appointments.sort((a1, a2) -> Double.compare(a1.getTotalPrice(), a2.getTotalPrice()));
                break;
        }
        appointmentsList.setItems(FXCollections.observableArrayList(appointments));
    }

//    public void deleteHairdresser (ActionEvent actionEvent)
//    {
//        if (hairdressers.contains(hairdressersList.getSelectionModel().getSelectedItem()))
//        {
//            hairdressers.remove(hairdressersList.getSelectionModel().getSelectedItem());
//            hairdressersList.setItems(FXCollections.observableArrayList(hairdressers));
//            Hairdresser.storeInFile((ArrayList<Hairdresser>) hairdressers);
//        }
//        else {
//            setErrorFields("This users doesn't exist.");
//        }
//    }

    public void loadAppointments()
    {
        appointments = Appointment.getAppointments();
        appointmentsList.setItems(FXCollections.observableArrayList(appointments));
    }
}
