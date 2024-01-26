/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloodbankdonationsystem;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author WINDOWS 10
 */
public class dashboardContoller implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;
    @FXML
    private Button viewinventorybtn;
    @FXML
    private Button addMed_btn;

    @FXML
    private Button pruchase_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private  AnchorPane deleteBloodForm;
    @FXML
    private TextField addMedicines_batchno1;
    @FXML
    private AnchorPane view_inventory_form;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private Label dashboard_availableMed;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Label dashboard_totalCustomers;

    @FXML
    private AnchorPane addMedicines_form;

    @FXML
    private TextField addMedicines_medicineID;

    @FXML
    private TextField addMedicines_brand;
    @FXML
    private TextField addMedicines_batchno;
    @FXML
    private  TextField addMedicines_pints;

    @FXML
    private TextField addMedicines_productName;

    @FXML
    private ComboBox<?> addMedicines_type;

    @FXML
    private ComboBox<?> addMedicines_status;

    @FXML
    private TextField addMedicines_price;

    @FXML
    private ImageView addMedicines_imageView;

    @FXML
    private Button addMedicines_importBtn;

    @FXML
    private Button addMedicines_addBtn;

    @FXML
    private Button addMedicines_updateBtn;

    @FXML
    private Button addMedicines_clearBtn;

    @FXML
    private Button addMedicines_deleteBtn;

    @FXML
    private TextField addMedicines_search;

    @FXML
    private TableView<bloodData> addMedicines_tableView;

    @FXML
    private TableColumn<bloodData, String> col_blood_donorName;

    @FXML
    private TableColumn<bloodData, String> col_blood_donorID;

    @FXML
    private TableColumn<bloodData, String> col_blood_pintsDonated;

    @FXML
    private TableColumn<bloodData, String> col_blood_type;


@FXML
private  TableColumn<bloodData, String>col_blood_batch;
    @FXML
    private TableColumn<bloodData, String> col_blood_date;

    @FXML
    private AnchorPane purchase_form;
@FXML
private TextField purchase_address;
    @FXML
    private ComboBox<String> purchase_type;

    @FXML
    private Spinner<Integer> purchase_quantity;


//    DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;


    public void homeAM(){

        String sql = "SELECT COUNT(id) FROM blood_info ";

        connect = database.connectDb();
        int countAM = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countAM = result.getInt("COUNT(id)");
            }

            dashboard_availableMed.setText(String.valueOf(countAM));

        }catch(Exception e){e.printStackTrace();}

    }

    public void homeTI(){
        String sql = "SELECT SUM(pints_donated) FROM blood_info";

        connect = database.connectDb();
        double totalDisplay = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                totalDisplay = result.getDouble("SUM(pints_donated)");
            }

            dashboard_totalIncome.setText(String.valueOf(totalDisplay));

        }catch(Exception e){e.printStackTrace();}

    }



    public void homeTC(){

        String sql = "SELECT COUNT(id) FROM blood_info";

        connect = database.connectDb();
        int countTC = 0;

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countTC = result.getInt("COUNT(id)");
            }

            dashboard_totalCustomers.setText(String.valueOf(countTC));

        }catch(Exception e){e.printStackTrace();}

    }

    public void addBlood(){

        String sql = "INSERT INTO blood_info (donor_name, donor_id, blood_type, pints_donated, batch_no) "
                + "VALUES(?,?,?,?,?)";

        connect = database.connectDb();

        try{

            Alert alert;

            if(
                       addMedicines_brand.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_type.getSelectionModel().getSelectedItem() == null
                    || addMedicines_pints.getText().isEmpty()
                    || addMedicines_batchno.getText().isEmpty()
                    ){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                String checkData = "SELECT batch_no FROM blood_info WHERE batch_no = '"
                        +addMedicines_batchno.getText()+"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Batch Number: " + addMedicines_batchno.getText() + "  already exists!");
                    alert.showAndWait();
                }else{
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_brand.getText());
                    prepare.setString(2, addMedicines_productName.getText());
                    prepare.setString(3, (String)addMedicines_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, addMedicines_pints.getText());
                    prepare.setString(5, addMedicines_batchno.getText());



                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addMedicineShowListData();

                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
public  void openDeleteMedicine(){
    deleteBloodForm.setVisible(true);
}
    public void deleteMedicine() {
        String batchNo = addMedicines_batchno1.getText();
        String sql = "DELETE FROM blood_info WHERE batch_no = '" + batchNo + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (batchNo.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please provide a Batch Number to delete");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Batch Number: " + batchNo + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                    // User confirmed, make the deleteBloodForm visible
                    deleteBloodForm.setVisible(true);

                    // Perform the deletion
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // Hide the deleteBloodForm after deletion
                    deleteBloodForm.setVisible(false);

                    // Update the medicine list and reset form
                    addMedicineShowListData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private String[] addMedicineListT = {"A", "B", "AB", "O"};
    public void addMedicineListType(){
        List<String> listT = new ArrayList<>();

        for(String data: addMedicineListT){
            listT.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listT);
        addMedicines_type.setItems(listData);

    }






    public ObservableList<bloodData> addMedicinesListData() {
        String sql = "SELECT * FROM blood_info";
        ObservableList<bloodData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            bloodData medData;

            while (result.next()) {
                medData = new bloodData(
                        result.getString("donor_name"),
                        result.getString("donor_id"),
                        result.getString("blood_type"),
                        result.getDouble("pints_donated"),
                        result.getString("batch_no"),
                        result.getDate("date"));

                listData.add(medData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<bloodData> addMedicineList;

    public void addMedicineShowListData() {
        addMedicineList = addMedicinesListData();

        col_blood_donorName.setCellValueFactory(new PropertyValueFactory<>("donorName"));
        col_blood_donorID.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        col_blood_type.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        col_blood_pintsDonated.setCellValueFactory(new PropertyValueFactory<>("pintsDonated"));
        col_blood_batch.setCellValueFactory(new PropertyValueFactory<>("batchNo"));
        col_blood_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addMedicines_tableView.setItems(addMedicineList);
    }




    public void addRequest(){
        purchaseCustomerId();
        String sql = "INSERT INTO request_info (blood_type, address, pints_requested) "
                + "VALUES(?,?,?)";

        try{
            Alert alert;

             if(
                      purchase_address.getText().isEmpty()
                                   || purchase_type.getSelectionModel().getSelectedItem() == null
                                   || purchase_quantity.getValue() == null

            ){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf( purchase_type.getSelectionModel().getSelectedItem()));
                    prepare.setString(2, String.valueOf(purchase_address.getText()));
                    prepare.setString(3, String.valueOf(purchase_quantity.getValue()));



                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();

                }
            }

        }catch(Exception e){e.printStackTrace();}

    }

    private SpinnerValueFactory<Integer> spinner;
    public void purchaseShowValue(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        purchase_quantity.setValueFactory(spinner);
    }

    private int qty;
    public void purchaseQuantity(){
        qty = purchase_quantity.getValue();
    }



    private int customerId;
    public void purchaseCustomerId(){

        String sql = "SELECT customer_id FROM customer";

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                customerId = result.getInt("customer_id");
            }
            int cID = 0;
            String checkData = "SELECT customer_id FROM customer_info";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            while(result.next()){
                cID = result.getInt("customer_id");
            }

            if(customerId == 0){
                customerId+=1;
            }else if(cID == customerId){
                customerId = cID+1;
            }

        }catch(Exception e){e.printStackTrace();}

    }

    public void requestType() {
        // Define the blood types
        String[] bloodTypes = {"A", "B", "AB", "O"};

        // Create an ObservableList and populate it with the blood types
        ObservableList<String> listData = FXCollections.observableArrayList(bloodTypes);

        // Set the items in the purchase_type ComboBox
        purchase_type.setItems(listData);


    }






    public void defaultNav(){
        dashboard_btn.setStyle("-fx-background-color: #FF3F3F");    }

    public void switchForm(ActionEvent event){

        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(false);
            view_inventory_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #FF3F3F");
            addMed_btn.setStyle("-fx-background-color:transparent");
            pruchase_btn.setStyle("-fx-background-color:transparent");


            homeAM();
            homeTI();
            homeTC();

        }else if(event.getSource() == addMed_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(true);
            purchase_form.setVisible(false);
            view_inventory_form.setVisible(false);

            addMed_btn.setStyle("-fx-background-color: #FF3F3F");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            pruchase_btn.setStyle("-fx-background-color:transparent");
            viewinventorybtn.setStyle("-fx-background-color:transparent");
            addMedicineShowListData();

            addMedicineListType();


        }else if(event.getSource() == pruchase_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(true);
            view_inventory_form.setVisible(false);

            pruchase_btn.setStyle("-fx-background-color: #FF3F3F");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            addMed_btn.setStyle("-fx-background-color:transparent");
            viewinventorybtn.setStyle("-fx-background-color:transparent");


            requestType();


            purchaseShowValue();


        }else if(event.getSource() == viewinventorybtn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(true);
            purchase_form.setVisible(false);
            view_inventory_form.setVisible(true);

            viewinventorybtn.setStyle("-fx-background-color: #FF3F3F");
            pruchase_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            addMed_btn.setStyle("-fx-background-color:transparent");

            requestType();

            purchaseShowValue();


        }

    }

    public void displayUsername(){
        String user = getData.username;

        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }

    private double x = 0;
    private double y = 0;

    public void logout() {

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // HIDE THE DASHBOARD FORM
                logout.getScene().getWindow().hide();
                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        defaultNav();

        homeAM();
        homeTI();
        homeTC();


        addMedicineShowListData();
        addMedicineListType();

        requestType();


        purchaseShowValue();


    }

}
