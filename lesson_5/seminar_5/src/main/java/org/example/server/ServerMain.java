package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ServerMain {
    public static final int SERVER_PORT = 8181;

    public static void main(String[] args) {

        new Server().start(SERVER_PORT);
    }
}