package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatesInterface {

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

    public void Lector()
    {
        try(BufferedReader br = new BufferedReader(new FileReader("dates.txt")))
        {
            String [] appointments;
            String linea;
            ArrayList<Appointment> appointmentList = new ArrayList<>();
            while((linea = br.readLine()) != null)
            {
                appointments = linea.split(";");

            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void Load()
    {

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
