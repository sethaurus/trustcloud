/**
 * @author Thomas Drake-Brockman
**/

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.io.IOException;

public class TCDownloadCommand implements TCCommand {
  private String fileName;

  public TCDownloadCommand(String fileName) {
    this.fileName = fileName;
  }

  public void run(TCSocket socket) throws TCCommandException {
    TCMessage packet = new TCDownloadRequestMessage(fileName);

    try {
      socket.sendPacket(packet);
    } catch (TCSocketException e) {
      throw new TCCommandException(e.getMessage());
    }
  }

  private void saveFile(byte[] data) throws TCCommandException {

  }
}