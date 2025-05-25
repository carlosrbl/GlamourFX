package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Appointment;
import finalproject.glamourfx.data.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DatesInterface implements Initializable {

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
        MostrarDatos();
        DatesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            PriceDate.setText(String.valueOf(newValue.getTotalPrice()));
            ServiceDate.setText(newValue.getService());
            HairdresserDate.setText(newValue.getHairdresser());
            CustomerDate.setText(newValue.getCustomer());
            TimeOfDate.setText(newValue.getTime().toString());
        });
    }

    public ArrayList<Appointment> Lector()
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

    public void MostrarDatos()
    {
        ObservableList<Appointment> datosObservables = FXCollections.observableArrayList(Lector());
        DatesList.setItems(datosObservables);
    }

    public void update(ActionEvent actionEvent)
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

    public void delete(ActionEvent actionEvent)
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
}
