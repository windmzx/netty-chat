package com.mzx.Client.Login;

import com.mzx.Client.model.ClientModel;
import com.mzx.Client.stage.ControlledStage;
import com.mzx.Client.MainApp;
import com.mzx.Client.stage.StageController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.mzx.Client.MainApp.mainViewID;

public class LoginViewController implements ControlledStage, Initializable {

    @FXML
    TextField textPassword;
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtHostName;
    @FXML
    Button btn_login;
    @FXML
    ImageView imageView;
    @FXML
    Button btn_signIn;

    StageController myController;
    ClientModel model;

    public LoginViewController() {
        super();
    }


    public void setStageController(StageController stageController) {
        this.myController = stageController;
        model = ClientModel.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToMain() {
        myController.loadStage(MainApp.mainViewID, MainApp.mainViewRes);
        myController.setStage(MainApp.mainViewID, MainApp.loginViewID);
        myController.getStage(MainApp.mainViewID).setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //                    model.disConnect();
                System.out.println("123");
                myController.unloadStage(MainApp.EmojiSelectorID);
            }
        });
        myController.getStage(MainApp.mainViewID).setOnCloseRequest(windowEvent -> System.exit(0));
    }

    public void logIn() throws IOException {
        StringBuffer result = new StringBuffer();
        if (model.CheckLogin(txtUsername.getText(), "localhost", textPassword.getText(), result, 0)) {
            goToMain();
        } else {
            showError(result.toString());
        }
    }

    /**
     * 最小化窗口
     *
     * @param event
     */
    @FXML
    public void minBtnAction(ActionEvent event) {
        Stage stage = myController.getStage(MainApp.loginViewID);
        stage.setIconified(true);
    }

    /**
     * 关闭窗口，关闭程序
     *
     * @param event
     */
    @FXML
    public void closeBtnAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void showError(String error) {
        // TODO: 17-11-1
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wechat");
        alert.setContentText("登录失败 " + error);
        alert.show();
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        StringBuffer result = new StringBuffer();
        if (model.CheckLogin(txtUsername.getText(), txtHostName.getText(), textPassword.getText(), result, 1)) {
            goToMain();
        } else {
            showError(result.toString());
        }
    }
}