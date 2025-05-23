package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Reservations implements Initializable
{
    private static Reservations instance;

    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> selectHairdresser;
    @FXML
    private ChoiceBox<String> selectService;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label errorLabel;
    @FXML
    private Label cancelLabel;
    @FXML
    private Label confirmLabel;
    @FXML
    private Label totalLabel;

    private void loadHairdressers()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("hairdressers.txt")))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(";");
                if (parts.length >= 1)
                {
                    String name = parts[0].trim();
                    selectHairdresser.getItems().add(name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadServices()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("services.txt")))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(";");
                if (parts.length >= 1)
                {
                    String name = parts[0].trim();
                    selectService.getItems().add(name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelReservation(ActionEvent event)
    {
        selectHairdresser.getSelectionModel().clearSelection();
        selectService.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        setCancelStars("Reservation cancelled");
    }

    @FXML
    private void confirmReservation(ActionEvent event)
    {
        String hairdresser = selectHairdresser.getValue();
        String service = selectService.getValue();
        LocalDate datePicker2 = datePicker.getValue();

        if (hairdresser == null || service == null || datePicker2 == null) {
            setErrorStars("Por favor, completa todos los campos.");
            return;
        }
        String reservationData = hairdresser + ";" + service + ";" + datePicker2.toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt", true))) {
            writer.write(reservationData);
            writer.newLine();
            setConfirmStars("Reserva guardada: " + reservationData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToMainMenu(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/finalproject/glamourfx/customer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private double getServicePrice(String serviceName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("services.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2 && parts[0].trim().equals(serviceName)) {
                    return Double.parseDouble(parts[1].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    private double getHairdresserExtra(String hairdresserName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("hairdressers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3 && parts[0].trim().equals(hairdresserName)) {
                    return Double.parseDouble(parts[2].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    private void updateTotalPrice() {
        String selectedService = selectService.getValue();
        String selectedHairdresser = selectHairdresser.getValue();

        if (selectedService != null && selectedHairdresser != null) {
            double servicePrice = getServicePrice(selectedService);
            double hairdresserExtra = getHairdresserExtra(selectedHairdresser);
            double total = servicePrice + hairdresserExtra;

            totalLabel.setText(String.format("%.2f €", total));
        } else {
            totalLabel.setText("");
        }
    }



    //Mensajes de confirmación, error y cancelación
    public void setErrorStars(String nombre)
    {
        errorLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorLabel.setText(""));
        delay.play();
    }
    public void setConfirmStars(String nombre)
    {
       confirmLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> confirmLabel.setText(""));
        delay.play();
    }
    public void setCancelStars(String nombre)
    {
        cancelLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> cancelLabel.setText(""));
        delay.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadHairdressers();
        loadServices();
        selectService.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());
        selectHairdresser.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());
    }
}
