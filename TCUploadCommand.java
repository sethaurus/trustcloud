/**
 * @author Thomas Drake-Brockman
**/

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.io.IOException;

public class TCUploadCommand implements TCCommand {
  private String filePath;

  public TCUploadCommand(String filePath) {
    this.filePath = filePath;
  }

  public void run(TCSocket socket) throws TCCommandException {
    String fileName = Paths.get(filePath).getFileName().toString();
    byte[] fileData = getFile();

    TCMessage packet = new TCUploadRequestMessage(fileName, fileData);

    try {
      socket.sendPacket(packet);
    } catch (TCSocketException e) {
      throw new TCCommandException(e.getMessage());
    }
  }

  private byte[] getFile() throws TCCommandException {
    try {
      RandomAccessFile f = new RandomAccessFile(new File(filePath), "r");

      long length = f.length();

      if ((int) length != length) {
        throw new TCCommandException("Unable to read file, file too large.");
      }

      byte[] data = new byte[(int) length];
      f.readFully(data);
      return data;
    } catch (IOException e) {
      throw new TCCommandException(String.format("Unable to read file %s.", filePath));
    }
  }
}