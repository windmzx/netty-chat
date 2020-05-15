package com.mzx.Client.model;

import com.mzx.Client.chatroom.MainView;
import com.mzx.bean.ClientUser;
import com.mzx.chatcommon.*;
import com.mzx.model.Message;
import com.mzx.netty.ClientHelper;
import com.mzx.netty.NettyClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ClientModel {
    public ClientHelper clientHelper;
    private final int port = 8888;
    private String IP = "localhost";
    private volatile boolean isConnect = false;                               //连接标志
    private boolean chatChange = false;
    private String chatUser = "[group]";
    private String thisUser;
    public String myUserName = "123";
    private LinkedHashMap<String, ArrayList<Message>> userSession;   //用户消息队列存储用
    private ObservableList<ClientUser> userList;
    private ObservableList<Message> chatRecoder;

    private ClientModel() {
        super();
        ClientUser user = new ClientUser();
        user.setUserName("[group]");
        user.setStatus("");
        userSession = new LinkedHashMap<>();
        userSession.put("[group]", new ArrayList<>());
        userList = FXCollections.observableArrayList();
        chatRecoder = FXCollections.observableArrayList();
        userList.add(user);
    }


    public void initChatList(List<Friend> friends, List<Group> groups, String myUserName) {
        this.myUserName = myUserName;
        friends.forEach(friend -> {
            ClientUser user = new ClientUser();
            user.setStatus("online");
            user.setUserName(friend.getUsername());
            userList.add(user);
            userSession.put(user.getUserName(), new ArrayList<>());
        });


        groups.forEach(group -> {
            ClientUser user = new ClientUser();
            user.setUserName(group.getGroupId());
            user.setStatus("online");

            userList.add(user);
            userSession.put(group.getGroupId(), new ArrayList<>());
        });
    }

    private static ClientModel instance;

    public static ClientModel getInstance() {
        if (instance == null) {
            synchronized (ClientModel.class) {
                if (instance == null) {
                    instance = new ClientModel();
                }
            }
        }
        return instance;
    }

    public void joinGroup(CreateGroupResponse msg) {

        ClientUser user = new ClientUser();
        user.setUserName(msg.getGroupId());
        user.setStatus("online");
        userList.add(user);
        userSession.put(msg.getGroupId(), new ArrayList<>());
        Message message = new Message();
        message.setSpeaker(msg.getGroupCreater());
        StringBuilder helloMessage = new StringBuilder("" + msg.getGroupCreater() + "邀请你加入群聊\n" +
                "同时加入群聊的有：\n");

        for (String username : msg.getGroupUsers()) {
            helloMessage.append(username).append("\n");
        }
        message.setContent(helloMessage.toString());

        userSession.get(msg.getGroupId()).add(message);

    }



    public void sentMessage(String targetuserId, String message) {
        MessageRequest messagePackage = new MessageRequest();
        messagePackage.setMessage(message);
        messagePackage.setTargetUserId(targetuserId);
        messagePackage.setFromUserId(myUserName);
        messagePackage.setFromUserName(myUserName);

        try {
            clientHelper.sendMessage(messagePackage);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void sentGroupMessage(String groupid, String message) {

        GroupMessageRequest request = new GroupMessageRequest();
        request.setFromUserId(myUserName);
        request.setFromUserName(myUserName);
        request.setTargetGroupId(groupid);
        request.setMessage(message);
        try {
            clientHelper.sendMessage(request);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean CheckLogin(String username, String IP, String password, StringBuffer buf, int type) {
        this.IP = IP; //bind server IP
        clientHelper = new ClientHelper();
        NettyClient nettyClient = new NettyClient();
        nettyClient.setIp("localhost");
        nettyClient.setPort(8888);
        clientHelper.setClient(nettyClient);
        clientHelper.connect();
        LoginPackage loginPackage = new LoginPackage();
        loginPackage.setPassword(password);
        loginPackage.setUsername(username);
        clientHelper.sendMessage(loginPackage);
        System.out.println("login success");
        return true;

    }


    public void setChatUser(String chatUser) {
        if (!this.chatUser.equals(chatUser)) {
            chatChange = true;
        }
        this.chatUser = chatUser;
        //消除未读信息状态
        for (int i = 0; i < userList.size(); i++) {
            ClientUser user = userList.get(i);
            if (user.getUserName().equals(chatUser)) {
                if (user.isNotify()) {
                    System.out.println("更改消息目录");
//                    user.setStatus(user.getStatus().substring(0, user.getStatus().length() - 3));
                    userList.remove(i);
                    userList.add(i, user);
                    user.setNotify(false);
                }
                chatRecoder.clear();
                chatRecoder.addAll(userSession.get(user.getUserName()));
                break;
            }
        }
    }

    public ObservableList<Message> getChatRecoder() {
        return chatRecoder;
    }

    public ObservableList<ClientUser> getUserList() {
        return userList;
    }

    public String getThisUser() {
        return thisUser;
    }

    public void setThisUser(String username) {
        this.thisUser = username;
        MainView.getInstance().setUser();
    }


    public void addselfmessage(Message message) {
        //当前聊天框加入消息
        chatRecoder.add(message);
        //消息列表加入消息
        userSession.get(chatUser).add(message);
    }


    public void handleMessage(MessageResponse message) {

        Message m = new Message();
        m.setTimer(LocalDateTime.now().toString());
        m.setSpeaker(message.getFromUserId());
        m.setContent(message.getMessage());

//        ClientUser clientUser=
        userList.forEach(clientUser -> {
            if (clientUser.getUserName().equals(message.getFromUserId())) {
                clientUser.setNotify(true);
            }
        });
        if (chatUser.equals(message.getFromUserId())) {
            chatRecoder.add(m);
//            及时是在目标用户聊天也要加上聊天记录
            userSession.get(message.getTargetUserId()).add(m);
        } else {

            userSession.get(message.getFromUserId()).add(m);
        }

        System.out.println("服务器发来消息");
    }

    public void handleGroupMessage(GroupMessageResponse messageResponse) {
        Message m = new Message();
        m.setSpeaker(messageResponse.getFromUserId());
        m.setTimer(LocalDateTime.now().toString());
        m.setContent(messageResponse.getMessage());
        if (chatUser.equals(messageResponse.getTargetGroupId())) {
            chatRecoder.add(m);
            userSession.get(messageResponse.getTargetGroupId()).add(m);
        } else {
            userSession.get(messageResponse.getTargetGroupId()).add(m);
        }
    }
}
