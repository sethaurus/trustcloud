/**
 * @author Thomas Drake-Brockman
**/

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class TCDownloadRequestMessage implements TCMessage {
  private String fileName;
  private byte[] fileData;

  public TCDownloadRequestMessage(String fileName) {
    this.fileName = fileName;
  }

  public TCDownloadRequestMessage(byte[] packetBytes) {
    int fileNameLength = TCByteTools.bytesToInt(TCByteTools.getRange(packetBytes, 1, 4));

    byte[] fileNameBytes = TCByteTools.getRange(packetBytes, 5, fileNameLength);
    try {
      this.fileName = new String(fileNameBytes, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      System.exit(1);
    }

    System.out.println(this.fileName);
  }

  public byte[] toBytes() {
    byte[] fileNameBytes = fileName.getBytes(Charset.forName("UTF-8"));

    // Packet Type - 1 byte
    // Filename Length - 4 bytes
    // Filename - variable
    int packetLength = 1 + 4 + fileNameBytes.length;
    byte[] packetBytes = new byte[packetLength];

    packetBytes[0] = 1;
    TCByteTools.copyRange(TCByteTools.intToBytes(fileNameBytes.length), packetBytes, 1, 4);
    TCByteTools.copyRange(fileNameBytes, packetBytes, 5, fileNameBytes.length);

    return packetBytes;
  }
}