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
import java.util.Scanner;

public class ClientFileManager  {
    private static client[] clientArray;
    static int clientCount;

    public static String clientToCSV(client cl){
        return cl.getClientID() + ";" + cl.getFName() + ";" + cl.getLName() + ";" + cl.getEmail();
    }

    // save clients to a file 
    public static void saveClients(client[] clients, int clientCount, String filePath) throws IOException{
        client [] saveArray = clients.clone();
        ClientFileManager.clientCount = clientCount;

        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath,true));
            for (int i = 0; i<clients.length;i++){
                if (saveArray[i] != null) {
                    out.println(clientToCSV(saveArray[i]));
                    clientCount++;
                }
            }
            out.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while saving clients: " + e.getMessage());
        }
       
    }

    //LOADS CLIENDS FROM A TILE 
    public static int loadClients(client[] clients, String filePath) throws IOException, InvalidClientDataException, DuplicateEmailException {
        clientArray = clients;
        int count = 0;
        if (clientArray == null) {
           count = 0; // default size
        } else{
            for (int i = 0; i<clientArray.length;i++){
                if (clientArray[i]==null){
                    count = i; // checks if the array is empty , if not, appends the clients at the end of the array 
                    break;
                }
            }
        }

        try{
            Scanner reader = new Scanner (new FileReader(filePath));
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                try {
                    String [] parts = line.split(";");
                    
                    if (parts.length!=4){
                        ErrorLogger.log("Invalid Line for client: " + line);
                        break;
                    } 
                    clientArray[count] = new client(parts[1],parts[2],parts[3],clientArray);
                    count++;
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line " + line + ": " + e.getMessage());
                    continue;
                }
            }
                reader.close();
            
        } catch (Exception e) {
            ErrorLogger.log("Error loading clients: " + e.getMessage());
            
        }
        return count; 
    }

}
