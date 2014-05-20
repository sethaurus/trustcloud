/**
 * @author Thomas Drake-Brockman
**/

import java.nio.ByteBuffer;

public class TCByteTools {
  static public byte[] intToBytes(int n) {
    return ByteBuffer.allocate(4).putInt(n).array();
  }

  static public int bytesToInt(byte[] bytes) {
    return ByteBuffer.wrap(bytes).getInt();
  }

  static public void copyRange(byte[] source, byte[] target, int targetStart, int length) {
    for (int i = 0; i < length; i++) {
      target[targetStart + i] = source[i];
    }
  }

  static public byte[] getRange(byte[] source, int start, int length) {
    byte[] target = new byte[length];
    for (int i = 0; i < length; i++) {
      target[i] = source[start + i];
    }
    return target;
  }
}