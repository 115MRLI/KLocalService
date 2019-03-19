package com.example.klocalservice.coreserver;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.klocalservice.model.event.ServerStatusEvent;
import com.example.klocalservice.util.Bus;
import com.example.klocalservice.util.NetUtils;
import com.example.klocalservice.util.ToastUtils;
import com.google.gson.Gson;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 核心网络服务
 */
public class CoreServer extends Service implements Server.ServerListener {

    //扫描服务端口号
    public final static String KEY_SCAN_SERVER_PORT = "KeyScanServerPort";
    //本地服务器端口号
    public final static String KEY_SERVER_PORT = "KeyServerPort";

    //客户端消息头
    private final static String HAITA_PAD_HEAD = "clientScan:";
    //服务消息头
    private final static String HAITA_SERVER_HEAD = "clientServer:";
    //服务请求超时
    private final static int TIME_OUT = 10;

    //扫描服务端口号
    private int scanServerPort = 8081;
    //本地服务器端口号
    private int serverPort = 8080;
    private Server server;

    @Override
    public void onCreate() {
        super.onCreate();
        this.server = AndServer.serverBuilder()
                .inetAddress(NetUtils.getLocalIPAddress())
                .port(serverPort)
                .timeout(TIME_OUT, TimeUnit.SECONDS)
                .listener(this)
                .build();
    }


    @Override
    public void onStarted() {
        Bus.get().post(new ServerStatusEvent(0));
        ToastUtils.showShort(getApplicationContext(), "本地服务已启动");
    }

    @Override
    public void onStopped() {
        this.server = null;
        Bus.get().post(new ServerStatusEvent(1));
        ToastUtils.showShort(getApplicationContext(), "本地服务已停止");
    }

    @Override
    public void onException(Exception e) {
        Bus.get().post(new ServerStatusEvent(-2, e.getMessage()));
        ToastUtils.showShort(getApplicationContext(), "本地服务启动失败");
    }

    /**
     * Start server.
     */
    private void startServer() {
        if (null != server && !server.isRunning()) {
            server.startup();
        }
    }

    /**
     * Stop server.
     */
    private void stopServer() {
        if (null != server) {
            server.shutdown();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            this.scanServerPort = intent.getIntExtra(KEY_SCAN_SERVER_PORT, 8081);
            this.serverPort = intent.getIntExtra(KEY_SERVER_PORT, 8080);
        }
        //启动本地服务器
        startServer();
        //启动扫描服务
        new Thread(new ClientScanRunnable()).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopServer();
        super.onDestroy();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://接收到服务器消息
                    ScanServerModel model = (ScanServerModel) msg.obj;
                    ToastUtils.showShort(getApplicationContext(), model.getDeviceName() + model.getHostAddress());
                    break;
                case -1://服务停止
                    ToastUtils.showShort(getApplicationContext(), "服务停止");
                    break;
                case -2://接受消息失败
                    ToastUtils.showShort(getApplicationContext(), "消息接受失败");
                    break;
            }
        }
    };

    /**
     * 服务器扫描服务
     */
    private class ClientScanRunnable implements Runnable {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            try (DatagramSocket datagramSocket = new DatagramSocket(scanServerPort)) {
                String hostAddress = Objects.requireNonNull(
                        NetUtils.getLocalIPAddress()).getHostAddress();
                byte[] replyData = new ScanServerModel(HAITA_SERVER_HEAD)
                        .setDeviceName(Build.DEVICE)
                        .setHostAddress(hostAddress + ":" + serverPort)
                        .toBytes();
                while (!datagramSocket.isClosed()) {
                    try {
                        byte[] data = new byte[1024];
                        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
                        datagramSocket.receive(datagramPacket);
                        String clientInfo = new String(data, 0, datagramPacket.getLength());
                        //当收到的消息为固定字符串开头的时候，则需要返回消息
                        if (clientInfo.startsWith(HAITA_PAD_HEAD)) {
                            InetAddress inetAddress = datagramPacket.getAddress();
                            DatagramPacket datagramPacketReply = new DatagramPacket(replyData, replyData.length, inetAddress, scanServerPort);
                            datagramSocket.send(datagramPacketReply);
                            ScanServerModel scanServerModel = new Gson().fromJson(clientInfo
                                    .substring(HAITA_PAD_HEAD.length()), ScanServerModel.class);
                            Message message = new Message();
                            message.obj = scanServerModel;
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(-2);
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(-1);
            }
        }
    }

    /**
     * 扫描服务数据传输对象
     */
    private class ScanServerModel {
        //设备名称
        private String deviceName = "";
        //主机地址
        private String hostAddress = "";
        //请求头
        private String scanHeader;

        ScanServerModel(@NonNull String scanHeader) {
            this.scanHeader = scanHeader;
        }

        String getDeviceName() {
            return deviceName;
        }

        ScanServerModel setDeviceName(String deviceName) {
            this.deviceName = deviceName;
            return this;
        }

        String getHostAddress() {
            return hostAddress;
        }

        ScanServerModel setHostAddress(String hostAddress) {
            this.hostAddress = hostAddress;
            return this;
        }

        public String getScanHeader() {
            return scanHeader;
        }

        public ScanServerModel setScanHeader(String scanHeader) {
            this.scanHeader = scanHeader;
            return this;
        }

        /**
         * 将当前对象转json并拼接请求头并转bytes
         *
         * @return
         */
        byte[] toBytes() {
            String json = new Gson().toJson(this, ScanServerModel.class);
            return (scanHeader + json).getBytes();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
