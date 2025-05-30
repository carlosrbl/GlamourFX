/**
 * @author Adrían
 * This class contains the user interface controller for the services section
 */

package finalproject.glamourfx.controllers;

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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
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
    private Label errorFields;

    @FXML
    private Label errorPrice;

    @FXML
    private ChoiceBox<String> servicesOrder;

    static List<Service> services;

    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadServices();
        applyStyle();
        setupMouseEvents();

        servicesList.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            servicesName.setText(newValue.getName());
            servicesPrice.setText(newValue.getPrice()+"");
        });

        String[] orders = {"Name", "Name (inverted)", "Price", "Price (inverted)"};
        servicesOrder.setItems(FXCollections.observableArrayList(Arrays.asList(orders)));
        servicesOrder.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> showOrderedBy(servicesOrder.getValue()));
    }

    public void applyStyle()
    {
        servicesList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Service> call(ListView<Service> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Service item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.toString());
                        }
                        setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 20px;");
                    }
                };
            }
        });
    }

    private void setupMouseEvents() {
        servicesList.setOnMouseClicked(this::CursorToHand);
        servicesList.setOnMouseExited(this::CursorToDefault);
    }

    /**
     * Changes the cursor to a hand icon when hovering over the list view.
     * @param event The mouse event triggered by the user.
     */
    private void CursorToHand(MouseEvent event) {
        servicesList.setCursor(Cursor.HAND);
    }

    /**
     * Changes the cursor back to the default icon when not hovering over the list view.
     * @param event The mouse event triggered by the user.
     */
    private void CursorToDefault(MouseEvent event) {
        servicesList.setCursor(Cursor.DEFAULT);
    }

    /**
     * Orders the services based on the selected criteria.
     * @param order The criteria by which to order the services.
     */
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
                services.sort(Comparator.comparingDouble(Service::getPrice));
                break;
            case "Price (inverted)":
                services.sort((s1, s2)->Double.compare(s2.getPrice(), s1.getPrice()));
                break;
        }

      servicesList.setItems(FXCollections.observableArrayList(services));
    }

    /**
     * Navigates back to the admin interface.
     * @param event The action event triggered by the user.
     */
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

    /**
     * Checks if the input fields are not empty.
     * @return true if the fields are not empty, false otherwise.
     */
    public boolean emptyField()
    {
        return !servicesName.getText().isEmpty() && !servicesPrice.getText().isEmpty();
    }


    public void addService()
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

    /**
     * Checks if there are no errors in the input fields.
     * @return true if there are no errors, false otherwise.
     */
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

    /**
     * Validates if the input string is a valid price.
     * @param number The input string to validate.
     * @return true if the input is a valid price, false otherwise.
     */
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

    /**
     * Sets the error message for the price field.
     * @param nombre The error message to be displayed.
     */
    public void setErrorPrice(String nombre)
    {
        errorPrice.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(_ -> errorPrice.setText(""));
        delay.play();
    }

    /**
     * Sets the error message for the input fields.
     * @param nombre The error message to be displayed.
     */
    public void setErrorFields(String nombre)
    {
        errorFields.setText(nombre);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(_ -> errorFields.setText(""));
        delay.play();
    }

    /**
     * Changes the cursor to a hand icon when hovering over a button.
     * @param event The mouse event triggered by the user.
     */
    @Override
    public void changeCursorToHand(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.HAND);
    }

    /**
     * Changes the cursor back to the default icon when not hovering over a button.
     * @param event The mouse event triggered by the user.
     */
    @Override
    public void changeCursorToDefault(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setCursor(Cursor.DEFAULT);
    }
}
