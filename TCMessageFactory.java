public class TCMessageFactory {
  private byte[] packetBytes;

  public TCMessageFactory(byte[] packetBytes) {
    this.packetBytes = packetBytes;
  }

  public TCMessage getPacket() {
    byte packetType = packetBytes[0];

    switch (packetType) {
      case 0:
        return new TCUploadRequestMessage(packetBytes);
      case 1:
        return new TCDownloadRequestMessage(packetBytes);
      default:
        return null;
    }
  }
}