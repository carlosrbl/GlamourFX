package finalproject.glamourfx.controllers;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
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
import javafx.util.Duration;
import java.time.LocalDate;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Reservations implements Initializable, ButtonCursor
{
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void cancelReservation()
    {
        selectHairdresser.getSelectionModel().clearSelection();
        selectService.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        setCancelStars("Reservation cancelled");
    }

    @FXML
    private void confirmReservation()
    {
        String hairdresser = selectHairdresser.getValue();
        String service = selectService.getValue();
        LocalDate datePicker2 = datePicker.getValue();

        if (hairdresser == null || service == null || datePicker2 == null) {
            setErrorStars("Por favor, completa todos los campos.");
            return;
        }
        String reservationData = hairdresser + ";" + service + ";" + datePicker2 + ";" + totalLabel.getText().replace(" €","").replace(",",".") + ";" + SessionManager.getCurrentCustomer().getName();

        try (PrintWriter pw = new PrintWriter(new FileWriter("reservations.txt",true))) {
            pw.println(reservationData);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        setConfirmStars("Reserva guardada: " + reservationData);
        selectHairdresser.setValue("");
        selectService.setValue("");
    }

    /**
     * Navigates back to the main menu.
     * @param event The action event triggered by clicking the back button.
     */
    @FXML
    private void backToMainMenu(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/customer.fxml"));
            Parent root = loader.load();

            root.setRotationAxis( Rotate.Y_AXIS);
            root.setRotate(-90);

            CustomerInterface controller = loader.getController();
            controller.setClienteName(SessionManager.getCurrentCustomer().getName());

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

    /**
     * Retrieves the price of a selected service.
     * @param serviceName The name of the service.
     * @return The price of the service.
     */
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
            System.out.println(e.getMessage());
        }
        return 0.0;
    }

    /**
     * Retrieves the extra charge for a selected hairdresser.
     * @param hairdresserName The name of the hairdresser.
     * @return The extra charge of the hairdresser.
     */
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
            System.out.println(e.getMessage());
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

    /**
     * Sets the error message label and clears it after a delay.
     * @param nombre The error message to display.
     */
    public void setErrorStars(String nombre)
    {
        errorLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(_ -> errorLabel.setText(""));
        delay.play();
    }

    /**
     * Sets the confirmation message label and clears it after a delay.
     * @param nombre The confirmation message to display.
     */
    public void setConfirmStars(String nombre)
    {
        confirmLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(_ -> confirmLabel.setText(""));
        delay.play();
    }

    /**
     * Sets the cancellation message label and clears it after a delay.
     * @param nombre The cancellation message to display.
     */
    public void setCancelStars(String nombre)
    {
        cancelLabel.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(_ -> cancelLabel.setText(""));
        delay.play();
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadHairdressers();
        loadServices();
        selectService.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> updateTotalPrice());
        selectHairdresser.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> updateTotalPrice());
    }

    /**
     * Changes the cursor to a hand cursor when hovering over a button.
     * @param event The mouse event.
     */
    @Override
    public void changeCursorToHand(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.HAND);
    }

    /**
     * Changes the cursor back to the default cursor when exiting a button.
     * @param event The mouse event.
     */
    @Override
    public void changeCursorToDefault(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.DEFAULT);
    }

}
