package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final Map<String, ClientConnection> clients = new HashMap<>();

    public void start(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = server.accept();
                System.out.println("Подключился новый клиент: " + clientSocket);
                ClientConnection client = new ClientConnection(clientSocket, this);
                String clientLogin = client.getLogin();
//                String clientLogin = client.getInput().nextLine();
                if (clients.containsKey(clientLogin)) {
                    client.sendMessage("Пользователь с таким логином уже подключён");
                    client.close();
                    continue;
                }

                clients.put(clientLogin, client);
                client.sendMessage("Подключение прошло успешно!");
                client.setOnCloseHandler(() -> {
                    clients.remove(clientLogin);
                });
                new Thread(client).start();

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendMessageToClient(String login,String message){
        ClientConnection clientConnection = clients.get(login);
        clientConnection.sendMessage(message);
    }

    public void sendMessageToAll(String message){
        for (ClientConnection client : clients.values()) {
            client.sendMessage(message);
        }
    }
}

