/**
 * @author Thomas Drake-Brockman
**/

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class TCUploadRequestMessage implements TCMessage {
  private String fileName;
  private byte[] fileData;

  public TCUploadRequestMessage(String fileName, byte[] fileData) {
    this.fileName = fileName;
    this.fileData = fileData;
  }

  public TCUploadRequestMessage(byte[] packetBytes) {
    int fileNameLength = TCByteTools.bytesToInt(TCByteTools.getRange(packetBytes, 1, 4));
    int fileDataLength = TCByteTools.bytesToInt(TCByteTools.getRange(packetBytes, fileNameLength + 5, 4));

    byte[] fileNameBytes = TCByteTools.getRange(packetBytes, 5, fileNameLength);
    try {
      this.fileName = new String(fileNameBytes, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      System.exit(1);
    }

    this.fileData = TCByteTools.getRange(packetBytes, fileNameLength + 9, fileDataLength);

    System.out.println(this.fileName);
    System.out.println(new String(this.fileData));
  }

  public byte[] toBytes() {
    byte[] fileNameBytes = fileName.getBytes(Charset.forName("UTF-8"));

    // Packet Type - 1 byte
    // Filename Length - 4 bytes
    // Filename - variable
    // File Length - 4 bytes
    // File - variable
    int packetLength = 1 + 4 + fileNameBytes.length + 4 + fileData.length;
    byte[] packetBytes = new byte[packetLength];

    packetBytes[0] = 0;
    TCByteTools.copyRange(TCByteTools.intToBytes(fileNameBytes.length), packetBytes, 1, 4);
    TCByteTools.copyRange(fileNameBytes, packetBytes, 5, fileNameBytes.length);
    TCByteTools.copyRange(TCByteTools.intToBytes(fileData.length), packetBytes, fileNameBytes.length + 5, 4);
    TCByteTools.copyRange(fileData, packetBytes, fileNameBytes.length + 9, fileData.length);

    return packetBytes;
  }
}