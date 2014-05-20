/**
 * @author Thomas Drake-Brockman
**/

import java.util.Scanner;

public class Server {
  private static final int MAX_PORT = 65535;

  public static void main(String[] args) {
    System.out.println("TrustCloud Server");
    System.out.println("CITS3002 - Thomas Drake-Brockman (21150739)\n");  

    // Parse command arguments.
    Integer serverPort = null;

    int i = 0;
    while (i < args.length) {
      if (args[i].equals("-p")) {
        if (serverPort != null) {
          System.out.println("You may only specify -p once");
          return;   
        }

        if (i + 1 == args.length || args[i+1].startsWith("-")) {
          System.out.println("Argument required for -p.");
          return;
        }

        Scanner sc = new Scanner(args[i+1]);

        if (sc.hasNextInt()) {
          serverPort = sc.nextInt();
        } else {
          System.out.println("Argument for -p must be an integer.");
          return;
        }

        i += 2;
      } else {
        System.out.println(String.format("Unrecognised options %s", args[i]));
        return;
      } 
    }

    if (serverPort == null) {
      System.out.println("No port specified.");
      return;
    }

    if (serverPort < 1 || serverPort > MAX_PORT) {
      System.out.println(String.format("Illegal port specificed. Must be between 1 and %d.", MAX_PORT));
      return;
    }

    try {
      TCServerSocket serverSock = new TCServerSocketFactory(serverPort).open();
      System.out.println(String.format("Bound on port %d. Awaiting connections.\n", serverPort));

      while (true) {
        TCSocket socket = serverSock.accept();

        System.out.println("Client has connected.");

        while (true) {
          try {
            TCMessage p = socket.readPacket();
          } catch (TCSocketException e) {
            break;
          }
        }
                  
        System.out.println("Client has disconnected.");
      }
    } catch (TCSocketException e) {
      System.out.println(e.getMessage());
      return;
    }
  }
}