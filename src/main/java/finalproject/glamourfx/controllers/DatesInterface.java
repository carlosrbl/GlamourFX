/**
 * @author Miguel
 * This class contains the user interface controller for the dates section
 */

package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Appointment;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import java.util.ResourceBundle;

public class DatesInterface implements Initializable,ButtonCursor{

    @FXML
    private TextField PriceDate;
    @FXML
    private TextField ServiceDate;
    @FXML
    private TextField HairdresserDate;
    @FXML
    private TextField CustomerDate;
    @FXML
    private TextField TimeOfDate;
    @FXML
    private ListView<Appointment> DatesList;

    static ArrayList<Appointment> appointmentList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showData();
        applyStyle();
        DatesList.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            PriceDate.setText(String.valueOf(newValue.getTotalPrice()));
            ServiceDate.setText(newValue.getService());
            HairdresserDate.setText(newValue.getHairdresser());
            CustomerDate.setText(newValue.getCustomer());
            TimeOfDate.setText(newValue.getTime().toString());
        });
    }

    public ArrayList<Appointment> reader()
    {
        String [] appointments;
        String linea;
        appointmentList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("reservations.txt")))
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while((linea = br.readLine()) != null)
            {
                appointments = linea.split(";");
                appointmentList.add(new Appointment(LocalDate.parse(appointments[2], formatter),appointments[4],appointments[0],Double.parseDouble(appointments[3]),appointments[1]));

            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return appointmentList;
    }

    public void showData()
    {
        ObservableList<Appointment> datosObservables = FXCollections.observableArrayList(reader());
        DatesList.setItems(datosObservables);
    }

    public void update()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DatesList.getSelectionModel().getSelectedItem().setTotalPrice(Double.parseDouble(PriceDate.getText()));
        DatesList.getSelectionModel().getSelectedItem().setTime(LocalDate.parse(TimeOfDate.getText(), formatter));
        DatesList.getSelectionModel().getSelectedItem().setCustomer(CustomerDate.getText());
        DatesList.getSelectionModel().getSelectedItem().setHairdresser(HairdresserDate.getText());
        DatesList.getSelectionModel().getSelectedItem().setService(ServiceDate.getText());

        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("reservations.txt")));

            for(Appointment appointment : DatesList.getItems())
            {
                writer.println(appointment.getHairdresser()+";"+appointment.getService()+";"+appointment.getTime()+";"+appointment.getTotalPrice()+";"+appointment.getCustomer());
            }
            writer.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void delete()
    {
        try( PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("reservations.txt"))))
        {
            appointmentList.remove(DatesList.getSelectionModel().getSelectedItem());
            DatesList.setItems(FXCollections.observableArrayList(appointmentList));
            for(Appointment appointment : appointmentList)
            {
                writer.println(appointment.getHairdresser()+";"+appointment.getService()+";"+appointment.getTime()+";"+appointment.getTotalPrice()+";"+appointment.getCustomer());
            }

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
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

    public void applyStyle()
    {
        DatesList.setCellFactory(new Callback<>()
        {
            @Override
            public ListCell<Appointment> call(ListView<Appointment> param)
            {
                return new ListCell<>()
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
                        setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 20px");
                    }
                };
            }
        });
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
