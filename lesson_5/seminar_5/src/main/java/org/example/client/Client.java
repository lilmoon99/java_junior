package org.example.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Client {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите логин: ");
        String login = console.nextLine();
        try (Socket server = new Socket("localhost", 8181)) {
            System.out.println("Подключение к серверу успешно!");
            try (Scanner in = new Scanner(server.getInputStream())) {
                PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                out.println(login);


                String msgFromServer = in.nextLine();
                System.out.println("Сообщение от сервера: " + msgFromServer);
                while (true) {
                    String inputFromConsole = console.nextLine();
                    out.println(inputFromConsole);

                    if (Objects.equals("exit", inputFromConsole)) {
                        break;
                    }

                    msgFromServer = in.nextLine();
                    System.out.println("Сообщение от сервера: " + msgFromServer);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void close() throws IOException{

    }

}
