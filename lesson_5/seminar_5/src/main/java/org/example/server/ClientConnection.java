package org.example.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientConnection implements Closeable, Runnable {
    private final Socket socket;
    private final Scanner input;
    private final PrintWriter output;
    private final String login;
    private Runnable onCloseHandler;
    private final Server server;

    public ClientConnection(Socket socket,Server server) throws IOException {
        this.socket = socket;
        this.input = new Scanner(socket.getInputStream());
        this.output = new PrintWriter(socket.getOutputStream(), true);
        this.login = input.nextLine();
        this.server = server;
    }

    @Override
    public void run() {
        try {


            while (true) {
                String msgFromClient = input.nextLine();
                if (Objects.equals("exit", msgFromClient)) {
                    System.out.println("Клиент отключился!");
                    if (onCloseHandler != null) {
                        onCloseHandler.run();
                    }
                    break;
                }

                System.out.println("Клиент прислал сообщение: " + msgFromClient);
                output.println("Получено сообщение: [" + msgFromClient + "]");
            }

            try {
                close();
            } catch (IOException e) {
                System.err.println("Произошла ошибка при закрытии сокета: " + e.getMessage());
            }
        } finally {
            if (onCloseHandler != null) {
                onCloseHandler.run();
            }
        }

    }




    @Override
    public void close() throws IOException {
        socket.close();
    }

    public void setOnCloseHandler(Runnable onCloseHandler) {
        this.onCloseHandler = onCloseHandler;
    }

    public Socket getSocket() {
        return socket;
    }

    public Scanner getInput() {
        return input;
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public String getLogin() {
        return login;
    }
}
