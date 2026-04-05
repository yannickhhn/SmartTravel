//----------------------------------------------
//Assignment 1
//Package Persistence
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package Persistence;
import Client.client;
import exceptions.DuplicateEmailException;
import exceptions.InvalidClientDataException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ClientFileManager {

    public static String clientToCSV(client cl){
        return cl.getClientID() + ";" + cl.getFName() + ";" + cl.getLName() + ";" + cl.getEmail();
    }

    // save clients to a file
    public static void saveClients(List<client> clients, int clientCount, String filePath) throws IOException{
        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath, true));
            for (client c : clients) {
                if (c != null) {
                    out.println(clientToCSV(c));
                }
            }
            out.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while saving clients: " + e.getMessage());
        }
    }

    // load clients from a file into the list
    public static void loadClients(List<client> clients, String filePath) throws IOException, InvalidClientDataException, DuplicateEmailException {
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 4) {
                        ErrorLogger.log("Invalid Line for client: " + line);
                        continue;
                    }
                    clients.add(new client(parts[1], parts[2], parts[3], clients));
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line " + line + ": " + e.getMessage());
                }
            }
            reader.close();
        } catch (Exception e) {
            ErrorLogger.log("Error loading clients: " + e.getMessage());
        }
    }

}
