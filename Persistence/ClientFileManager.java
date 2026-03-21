package Persistence;
import Client.client;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class ClientFileManager  {
    private static client[] clientArray;
    static int clientCount;

    public static String clientToCSV(client cl){
        return cl.getClientID() + ";" + cl.getFName() + ";" + cl.getLName() + ";" + cl.getEmail();
    }

    // save clients to a file 
    public static void saveClients(client[] clients, int clientCount, String filePath) throws IOException{
        clientArray = clients.clone();
        ClientFileManager.clientCount = clientCount;

        try {
            PrintWriter out = new PrintWriter(new FileWriter(filePath,true));
            for (int i = 0; i<clients.length;i++){
                if (clientArray[i] != null) {
                    out.println(clientToCSV(clientArray[i]));
                    clientCount++;
                }
            }
            out.close();
        } catch (Exception e) {
            ErrorLogger.log("Error occurred while saving clients: " + e.getMessage());
        }
       
    }

    //LOADS CLIENDS FROM A TILE 
    public static int loadClients(client[] clients, String filePath) throws IOException {
        clientArray = clients.clone();
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
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine(); 
            while(line!=null){
                try {
                    String [] parts = line.split(";");
                    if (parts.length!=4){
                        ErrorLogger.log("Invalid Line");
                    } 
                    clientArray[count] = new client(parts[1],parts[2],parts[3],clientArray);
                    count++;
                } catch (Exception e) {
                    ErrorLogger.log("Error parsing line " + line + ": " + e.getMessage());
                }
            }
                reader.close();
            
        } catch (Exception e) {
            ErrorLogger.log("Error loading clients: " + e.getMessage());
        }
        return count; 
    }

}
