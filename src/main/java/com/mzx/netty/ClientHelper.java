package com.mzx.netty;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientHelper {
//    private static Logger logger = LoggerFactory.getLogger(ClientHelper.class);

    private static final List<ClientHelper> instances = new ArrayList<>();

    private volatile NettyClient client;


    public ClientHelper() {
        instances.add(this);
    }

    public NettyClient getClient() {
        return client;
    }

    public void setClient(NettyClient client) {
        this.client = client;
    }


    public static ClientHelper getFirst() {
        return instances.size() > 0 ? instances.get(0) : null;
    }

    public void setRemoteHost(String ip, int port) {
        client.setIp(ip);
        client.setPort(port);
    }

    public void connect() {
        client.connect();
        // connectOrDisconnect为界面组件在UIUpdater中注册的名字
    }

    public void disConnect() {
        client.disConnect();
//        updater.update("connectOrDisconnect", new Object[] { "连接" });
    }

    public void stopAll() {
//        for (NettyClientHelper helper : instances) {
//            if (helper.getClient() != null) {
//                helper.getClient().close();
//            }
//        }
    }

    public void sendMessage(ByteBuf message) {
//        NettyMessage bizMsg = new NettyMessage(message);
//        bizMsg.setLogId(newLogId());
//        logger.info("发送消息  -- {}", bizMsg.toString());
//        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world\n", CharsetUtil.UTF_8);
//        ByteBuf buf = Unpooled.copiedBuffer(bizMsg.composeFull());
        client.future.channel().writeAndFlush(message);
        // ReferenceCountUtil.release(buf);
    }

    /**
     * 随机生成logId
     *
     * @return logId 介于[1000000,10000000]的随机奇数
     */
    private int newLogId() {
        int logId = randomInt(1000000, 10000000);
        if (logId % 2 == 0) {
            logId -= 1;
        }

        return logId;
    }

    /**
     * 生成介于min，max之间的随机整数
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max) {
        Random random = new Random(System.currentTimeMillis());
        // int x = (int) (Math.random() * max + min);
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
}
