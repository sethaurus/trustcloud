/**
 * @author Thomas Drake-Brockman
**/

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
  private static final int MAX_PORT = 65535;

  public static void main(String[] args) {
    System.out.println("TrustCloud Client");
    System.out.println("CITS3002 - Thomas Drake-Brockman (21150739)");

    // Parse command arguments.
    String hostString = null;
    List<TCCommand> commandList = new ArrayList<TCCommand>();

    int i = 0;
    while (i < args.length) {
      if (args[i].equals("-h")) {
        if (hostString != null) {
          System.out.println("You may only specify -h once");
          return;   
        }

        if (i + 1 == args.length || args[i+1].startsWith("-")) {
          System.out.println("Argument required for -p.");
          return;
        }

        hostString = args[i+1];

        i += 2;
      } else if (args[i].equals("-a")) {
        if (i + 1 == args.length || args[i+1].startsWith("-")) {
          System.out.println("Argument required for -a.");
          return;
        }
        String fileName = args[i+1];

        TCCommand command = new TCUploadCommand(fileName);
        commandList.add(command);

        i += 2;
      } else if (args[i].equals("-f")) {
        if (i + 1 == args.length || args[i+1].startsWith("-")) {
          System.out.println("Argument required for -f.");
          return;
        }
        String fileName = args[i+1];

        TCCommand command = new TCDownloadCommand(fileName);
        commandList.add(command);

        i += 2;
      } else {
        System.out.println(String.format("Unrecognised options %s", args[i]));
        return;
      } 
    }

    if (hostString == null) {
      System.out.println("No host specified.");
      return;
    }

    Integer hostPort;
    String hostName;

    Scanner hostScanner = new Scanner(hostString);
    hostScanner.useDelimiter(":");

    try {
      hostName = hostScanner.next();
      hostPort = hostScanner.nextInt();
    } catch (NoSuchElementException e) {
      System.out.println("Unable to parse provided host. Must be in the form <hostname>:<port>.");
      return;
    }

    // Connect to server.
    try {
      TCSocket sock = new TCSocketFactory(hostName, hostPort).open();
      System.out.println("Connected to server.\n");

      System.out.println(String.format("%d commands to run...", commandList.size()));

      for (TCCommand c : commandList) {
        try {
          c.run(sock);
        } catch (TCCommandException e) {
          System.out.println(e.getMessage());
        }
      }

      System.out.println("Disconnected from server.\n");
    } catch (TCSocketException e) {
      System.out.println(e.getMessage());
      return;
    } 
  }
}