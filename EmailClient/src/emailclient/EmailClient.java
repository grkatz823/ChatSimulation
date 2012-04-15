package emailclient;

import java.io.*;
import java.net.*;

public class EmailClient {

    private static InetAddress host;
    private static final int PORT = 64006;
    private static String name;
    private static BufferedReader in,  userEntry;
    private static PrintWriter out;
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }

        userEntry = new BufferedReader(
                new InputStreamReader(System.in));
        do {
            System.out.print(
                    "\nEnter name ('Dave' or 'Karen'): ");
            name = userEntry.readLine();
        } while (!name.equals("Dave") && !name.equals("Karen"));

        run();
    }

    private static void run() throws IOException {
            String option = "";
            String response = "";


        do {
             option = "";
             response = "";
           while(!option.equalsIgnoreCase("s")&&!option.equalsIgnoreCase("r")){
            System.out.println("Type 's' to send a message, type 'r' to" +
                    " read one");
            option = userEntry.readLine();}

            socket = new Socket(host, PORT);


            //Set up stream for keyboard entry...
            in = new BufferedReader(new InputStreamReader
                    (socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);



                if(option.equalsIgnoreCase("s")){
                doSend();
                }else{
                doRead();
               }

           while(!response.equalsIgnoreCase("y")&& !response.equalsIgnoreCase("n")){
                System.out.println("Do you want to continue? (y/n)");
                response = userEntry.readLine();
           }

            //else if (!option.equalsIgnoreCase("n")) {
              //  System.out.println("Invalid Input");
            //}


            /********************************************************

            CREATE A SOCKET, SET UP INPUT AND OUTPUT STREAMS,
            ACCEPT THE USER'S REQUEST, CALL UP THE APPROPRIATE
            METHOD (doSend OR doRead), CLOSE THE LINK AND THEN
            ASK IF USER WANTS TO DO ANOTHER READ/SEND.


             ********************************************************/
                     socket.close();
        } while (!response.equals("n"));

        out.println(name);
        out.println("quit");
        System.out.println("Goodbye");
    }

    private static void doSend() throws IOException {
        System.out.println("\nEnter 1-line message: ");
        String message = userEntry.readLine();
        out.println(name);
        out.println("send");
        out.println(message);
    }

    private static void doRead() throws IOException {
        out.println(name);
        out.println("read");
        String s = in.readLine();
        int numMess = Integer.valueOf(s);
        if (numMess > 0) {
            System.out.println("\nYou have " + numMess + " messages. ");
            for (int i = 0; i < numMess; i++) {
                System.out.println(in.readLine());
            }
        } else {
            System.out.println("\nYou have no messages: ");
        }
    /*********************************
    BODY OF THIS METHOD REQUIRED
     *********************************/
    }
}
