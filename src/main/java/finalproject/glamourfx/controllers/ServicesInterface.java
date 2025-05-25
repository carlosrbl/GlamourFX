package finalproject.glamourfx.controllers;

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
import javafx.util.Duration;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServicesInterface implements Initializable, ButtonCursor {

    @FXML
    private ListView<Service> servicesList;

    @FXML
    private TextField servicesName;

    @FXML
    private TextField servicesPrice;

    @FXML
    private Button servicesAdd;

    @FXML
    private Button servicesUpdate;

    @FXML
    private Button servicesDelete;

    @FXML
    private Label errorFields;

    @FXML
    private Label errorPrice;

    @FXML
    private ChoiceBox<String> servicesOrder;

    static List<Service> services;

    @FXML
    private Button servicesButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadServices();

        servicesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            servicesName.setText(newValue.getName());
            servicesPrice.setText(newValue.getPrice()+"");
        });

        String[] orders = {"Name", "Name (inverted)", "Price", "Price (inverted)"};
        servicesOrder.setItems(FXCollections.observableArrayList(Arrays.asList(orders)));
        servicesOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showOrderedBy(servicesOrder.getValue());
        });
    }

    public void showOrderedBy(String order)
    {
        switch (order)
        {
            case "Name":
                services.sort((s1, s2)->s1.getName().compareToIgnoreCase(s2.getName()));
               break;
            case "Name (inverted)":
                services.sort((s1, s2)->s2.getName().compareToIgnoreCase(s1.getName()));
                break;
            case "Price":
                services.sort((s1, s2)->Double.compare(s1.getPrice(), s2.getPrice()));
                break;
            case "Price (inverted)":
                services.sort((s1, s2)->Double.compare(s2.getPrice(), s1.getPrice()));
                break;
        }

      servicesList.setItems(FXCollections.observableArrayList(services));
    }

    public void back(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalproject/glamourfx/admin.fxml"));
            Parent root = loader.load();

            AdminInterface controller = loader.getController();
            controller.setClienteName("Admin");

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

    public boolean emptyField()
    {
        return !servicesName.getText().isEmpty() && !servicesPrice.getText().isEmpty();
    }


    public void addService(ActionEvent actionEvent)
    {
        if (noErrorInFields())
        {
            if (!(services.contains(new Service(servicesName.getText(), Double.parseDouble(servicesPrice.getText())))))
            {
                    if (isValidPrice(servicesPrice.getText()))
                    {
                        services.add(new Service(servicesName.getText(), Double.parseDouble(servicesPrice.getText())));
                        servicesList.setItems(FXCollections.observableArrayList(services));
                        Service.storeInFile(services);
                    }
                    else {
                        setErrorPrice("The price has to be a valid number.");
                    }
            }
            else
            {
                setErrorFields("The service already exists.");
            }
        }
    }

    public void updateService()
    {
        if (noErrorInFields())
        {
            if (isValidPrice(servicesPrice.getText()))
            {
                servicesList.getSelectionModel().getSelectedItem()
                        .setName(servicesName.getText());
                servicesList.getSelectionModel().getSelectedItem()
                        .setPrice(Double.parseDouble(servicesPrice.getText()));
            }
            else
            {
                setErrorPrice("The price has to be a valid number.");
            }
        }
    }


    public void deleteService()
    {
        if (services.contains(servicesList.getSelectionModel().getSelectedItem()))
        {
            services.remove(servicesList.getSelectionModel().getSelectedItem());
            servicesList.setItems(FXCollections.observableArrayList(services));
            Service.storeInFile(services);
        }
    }

    public boolean noErrorInFields()
    {
        boolean comprove = true;
        if (emptyField())
        {
            if (Double.parseDouble(servicesPrice.getText()) < 0)
            {
                setErrorPrice("The price can't be less than 0.");
                comprove = false;
            }
        }
        else
        {
            setErrorFields("You must fill all the fields.");
            comprove = false;
        }


        return comprove;
    }

    public boolean isValidPrice(String number) {
        String expression = "^\\d*\\.\\d+$";
        Pattern p = Pattern.compile(expression);
        Matcher m = p.matcher(number);
        return m.matches();
    }


    public void loadServices()
    {
        services = Service.getServices();
        servicesList.setItems(FXCollections.observableArrayList(services));
    }

    public void setErrorPrice(String nombre)
    {
        errorPrice.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorPrice.setText(""));
        delay.play();
    }


    public void setErrorFields(String nombre)
    {
        errorFields.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorFields.setText(""));
        delay.play();
    }

    public void changeCursorToHand(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.HAND);
    }

    public void changeCursorToDefault(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.DEFAULT);
    }
}
