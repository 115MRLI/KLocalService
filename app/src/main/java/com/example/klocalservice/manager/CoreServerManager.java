package com.example.klocalservice.manager;

import android.app.Application;
import android.content.Intent;

import com.example.klocalservice.coreserver.CoreServer;

public class CoreServerManager {
    /**
     * 启动核心服务
     *
     * @param serverPort     服务器端口
     * @param scanServerPort 扫描服务端口
     */
    public static void startCoreServer(Application application, int serverPort, int scanServerPort) {
        Intent intent = new Intent(application, CoreServer.class);
        intent.putExtra(CoreServer.KEY_SERVER_PORT, serverPort);
        intent.putExtra(CoreServer.KEY_SCAN_SERVER_PORT, scanServerPort);
        application.startService(intent);
    }
}
