package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Appointment;
import finalproject.glamourfx.data.Hairdresser;
import finalproject.glamourfx.data.Service;
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

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadAppointments();
        applyStyle();
        setupMouseEvents();

        appointmentsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

    private void CursorToHand(MouseEvent event) {
        appointmentsList.setCursor(Cursor.HAND);
    }

    private void CursorToDefault(MouseEvent event) {
        appointmentsList.setCursor(Cursor.DEFAULT);
    }

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
            e.printStackTrace();
        }
    }

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

    public void loadAppointments()
    {
        appointments = Appointment.getAppointments();
        appointmentsList.setItems(FXCollections.observableArrayList(appointments));
    }

    @FXML
    private void deleteSelectedAppointment(ActionEvent event)
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
            e.printStackTrace();
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("reservations.txt", false))) {
            for (String l : lines) {
                writer.println(l);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
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