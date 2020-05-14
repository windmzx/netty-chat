package com.mzx.Client.chatroom;


import com.mzx.Client.MainApp;
import com.mzx.Client.emojis.EmojiDisplayer;
import com.mzx.Client.model.ClientModel;
import com.mzx.Client.stage.ControlledStage;
import com.mzx.Client.stage.StageController;
import com.mzx.bean.ClientUser;

import com.google.gson.Gson;
import com.mzx.chatcommon.CreateGroupRequest;
import com.mzx.model.Message;
import com.mzx.netty.ClientHelper;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static com.mzx.Utils.Constants.*;
import static com.mzx.Utils.Constants.CONTENT;

public class MainView implements ControlledStage, Initializable {

    @FXML
    public Button btnEmoji;
    @FXML
    public TextArea textSend;
    @FXML
    public Button btnSend;

    @FXML
    public Button btnCreateGroup;
    @FXML
    public ListView chatWindow;
    @FXML
    public ListView userGroup;
    @FXML
    public Label labUserName;
    @FXML
    public Label labChatTip;
    @FXML
    public Label labUserCoumter;

    private Gson gson = new Gson();
    private StageController stageController;
    private ClientModel model;
    private static MainView instance;
    private boolean pattern = GROUP; //chat model
    private String seletUser = "[group]";
    private static String thisUser;
    private ObservableList<ClientUser> uselist;
    private ObservableList<Message> chatReccder;

    public MainView() {
        super();
        instance = this;
    }

    public static MainView getInstance() {
        return instance;
    }

    @Override
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
        ;
    }

    public void setUser() {
        labUserName.setText("Welcome " + model.getThisUser() + "!");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = ClientModel.getInstance();
        uselist = model.getUserList();
        chatReccder = model.getChatRecoder();
        userGroup.setItems(uselist);
        chatWindow.setItems(chatReccder);
        thisUser = model.getThisUser();
        labUserName.setText("Welcome " + model.getThisUser() + "!");

        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (pattern == GROUP) {
                    model.sentGroupMessage(seletUser, textSend.getText().trim());
                } else if (pattern == SINGLE) {

                    String message = textSend.getText().trim();
                    model.sentMessage(seletUser, gson.toJson(message));
                    Message m = new Message();
                    m.setSpeaker("我");
                    m.setTimer(String.valueOf(LocalDateTime.now()));
                    m.setContent(message);
                    model.addselfmessage(m);

                }
                textSend.setText("");
            }
        });

        userGroup.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ClientUser user = (ClientUser) newValue;
            System.out.println("You are selecting " + user.getUserName());
            if (user.getUserName().contains("[group]")) {
                pattern = GROUP;
                if (!seletUser.equals(user.getUserName())) {
                    model.setChatUser(user.getUserName());
                    seletUser = user.getUserName();
                    labChatTip.setText("Group Chat");
                }
            } else {
                pattern = SINGLE;
                if (!seletUser.equals(user.getUserName())) {
                    model.setChatUser(user.getUserName());
                    seletUser = user.getUserName();
                    labChatTip.setText("Chatting with " + seletUser);
                    // TODO: 2017/11/29
                }
            }
        });

        chatWindow.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ChatCell();
            }
        });

        userGroup.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new UserCell();
            }
        });
    }


    @FXML
    public void onEmojiBtnClcked() {
        stageController.loadStage(MainApp.EmojiSelectorID, MainApp.EmojiSelectorRes);
        stageController.setStage(MainApp.EmojiSelectorID);
    }

    @FXML
    public void onCreateGroup() {
        ClientHelper clientHelper = model.clientHelper;
        Stage secondStage = new Stage();
        Label label = new Label("输入你的好友ID创建群聊"); // 放一个标签
        Button button = new Button("创建群聊");
        TextField textArea = new TextField();
        button.setOnAction(actionEvent -> {
            String text = textArea.getText();
            System.out.println("你的输入内容是" + text);
            CreateGroupRequest request = new CreateGroupRequest();
            request.setGroupCreater(model.myUserName);
            String[] s = text.split(" ");
            List<String> users = new ArrayList<>();
            for (String s1 : s) {
                users.add(s1);
            }
            request.setGroupUsers(users);
            clientHelper.sendMessage(request);
        });
        VBox layout = new VBox(10);


        layout.getChildren().addAll(label, textArea, button);
//        secondPane.getChildren().add(textArea);
        layout.setAlignment(Pos.CENTER_LEFT);
        Scene secondScene = new Scene(layout, 300, 200);

        secondStage.setScene(secondScene);
        secondStage.show();
    }

    public TextArea getMessageBoxTextArea() {
        return textSend;
    }

    public Label getLabUserCoumter() {
        return labUserCoumter;
    }


    public static class UserCell extends ListCell<ClientUser> {
        @Override
        protected void updateItem(ClientUser item, boolean empty) {
            super.updateItem(item, empty);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (item != null) {
                        HBox hbox = new HBox();
                        ImageView imageHead = new ImageView(new Image("image/head.png"));
                        imageHead.setFitHeight(20);
                        imageHead.setFitWidth(20);
                        ClientUser user = (ClientUser) item;
                        ImageView imageStatus;
                        if (user.getUserName().equals("[group]")) {
                            imageStatus = new ImageView(new Image("image/online.png"));
                        } else if (user.isNotify() == true) {
                            imageStatus = new ImageView(new Image("image/message.png"));
                        } else {
                            if (user.getStatus().equals("online")) {
                                imageStatus = new ImageView(new Image("image/online.png"));
                            } else {
                                imageStatus = new ImageView(new Image("image/offline.png"));
                            }
                        }
                        imageStatus.setFitWidth(20);
                        imageStatus.setFitHeight(20);
                        Label label = new Label(user.getUserName());
                        hbox.getChildren().addAll(imageHead, label, imageStatus);
                        setGraphic(hbox);
                    } else {
                        setGraphic(null);
                    }
                }
            });
        }
    }

    public static class ChatCell extends ListCell<Message> {
        @Override
        protected void updateItem(Message item, boolean empty) {
            super.updateItem(item, empty);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //inorder to avoid the
                    if (item != null) {
                        VBox box = new VBox();
                        HBox hbox = new HBox();
                        TextFlow txtContent = new TextFlow(EmojiDisplayer.createEmojiAndTextNode(item.getContent()));
                        Label labUser = new Label(item.getSpeaker() + "[" + item.getTimer() + "]");
                        labUser.setStyle("-fx-background-color: #7bc5cd; -fx-text-fill: white;");
                        ImageView image = new ImageView(new Image("image/head.png"));
                        image.setFitHeight(20);
                        image.setFitWidth(20);
                        hbox.getChildren().addAll(image, labUser);
                        if (item.getSpeaker().equals(thisUser)) {
                            txtContent.setTextAlignment(TextAlignment.RIGHT);
                            hbox.setAlignment(Pos.CENTER_RIGHT);
                            box.setAlignment(Pos.CENTER_RIGHT);
                        }
                        box.getChildren().addAll(hbox, txtContent);
                        setGraphic(box);
                    } else {
                        setGraphic(null);
                    }
                }
            });
        }
    }

}
