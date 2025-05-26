package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Appointment;
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

import java.io.*;
import java.net.URL;
import java.util.*;

public class AppointmentsUserInterface implements Initializable {

    static List<Appointment> appointments;

    @FXML
    private ListView<Appointment> appointmentsList;

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

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadAppointments();
        applyStyle();
        setupMouseEvents();

        appointmentsList.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null)
            {
                datesHairdresser.setText(newValue.getHairdresser());
                datesService.setText(newValue.getService());
                datesAppointment.setText(newValue.getTime().toString());
                datesPrice.setText(newValue.getTotalPrice() + "");
            } else
            {
                datesHairdresser.setText("");
                datesService.setText("");
                datesAppointment.setText("");
                datesPrice.setText("");
            }
        });

        String[] orders = {"Hairdresser", "Service", "Date", "Price"};
        datesOrder.setItems(FXCollections.observableArrayList(Arrays.asList(orders)));
        datesOrder.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> showOrderedBy(datesOrder.getValue().toString()));
    }

    public void applyStyle()
    {
        appointmentsList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Appointment> call(ListView<Appointment> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Appointment item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.toString());
                        }
                        setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-size: 20px;");
                    }
                };
            }
        });
    }

    private void setupMouseEvents() {
        appointmentsList.setOnMouseClicked(this::CursorToHand);
        appointmentsList.setOnMouseExited(this::CursorToDefault);
    }

    /**
     * Changes the cursor to hand when the mouse is clicked.
     * @param event The mouse event.
     */
    private void CursorToHand(MouseEvent event) {
        appointmentsList.setCursor(Cursor.HAND);
    }

    /**
     * Changes the cursor back to default when the mouse exits.
     * @param event The mouse event.
     */
    private void CursorToDefault(MouseEvent event) {
        appointmentsList.setCursor(Cursor.DEFAULT);
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
     * Orders the appointments based on the selected criteria.
     * @param order The criteria to order by ("Hairdresser", "Service", "Date", "Price").
     */
    public void showOrderedBy(String order)
    {
        switch (order)
        {
            case "Hairdresser":
                appointments.sort(Comparator.comparing(a -> a.getHairdresser().toLowerCase()));
                break;
            case "Service":
                appointments.sort(Comparator.comparing(a -> a.getService().toLowerCase()));
                break;
            case "Date":
                appointments.sort(Comparator.comparing(Appointment::getTime));
                break;
            case "Price":
                appointments.sort(Comparator.comparingDouble(Appointment::getTotalPrice));
                break;
        }
        appointmentsList.setItems(FXCollections.observableArrayList(appointments));
    }

    public void loadAppointments()
    {
        appointments = Appointment.getAppointments();
        appointmentsList.setItems(FXCollections.observableArrayList(appointments));
    }

    @FXML
    private void deleteSelectedAppointment()
    {
        Appointment selected = appointmentsList.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            return;
        }

        List<String> lines = new ArrayList<>();
        String currentUser = SessionManager.getCurrentCustomer().getName();

        String selectedHairdresser = selected.getHairdresser().trim();
        String selectedService = selected.getService().trim();
        String selectedDate = selected.getTime().toString().trim();
        double selectedPrice = selected.getTotalPrice();
        boolean appointmentDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(";");
                if (parts.length >= 5)
                {
                    String hairdresser = parts[0].trim();
                    String service = parts[1].trim();
                    String date = parts[2].trim();
                    String priceStr = parts[3].trim();
                    String username = parts[4].trim();

                    double price;
                    try {
                        price = Double.parseDouble(priceStr.replace(",", "."));
                    } catch (NumberFormatException e) {
                        price = -1;
                        System.out.println(e.getMessage());
                    }

                    boolean isExactMatch =
                            hairdresser.equalsIgnoreCase(selectedHairdresser) &&
                                    service.equalsIgnoreCase(selectedService) &&
                                    date.equals(selectedDate) &&
                                    Math.abs(price - selectedPrice) < 0.01 &&
                                    username.equals(currentUser);

                    if (isExactMatch && !appointmentDeleted)
                    {
                        appointmentDeleted = true;
                        continue;
                    }
                }

                lines.add(line);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("reservations.txt", false))) {
            for (String l : lines) {
                writer.println(l);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return;
        }
        loadAppointments();

        appointmentsList.getSelectionModel().clearSelection();
        datesHairdresser.setText("");
        datesService.setText("");
        datesAppointment.setText("");
        datesPrice.setText("");
    }

}