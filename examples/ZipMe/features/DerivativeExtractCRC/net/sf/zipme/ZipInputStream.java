//

package net.sf.zipme;
class ZipInputStream {
  private CRC32 crc=new CRC32();
  /** 
 * Open the next entry from the zip archive, and return its description.
 * If the previous entry wasn't closed, this method will close it.
 */
   public ZipEntry getNextEntry() throws IOException {
    if (crc == null)     throw new IOException("Stream closed.");
    return (ZipEntry) original();
  }
  /** 
 * Closes the current zip entry and moves to the next one.
 */
   public Void closeEntry() throws IOException {
    if (crc == null)     throw new IOException("Stream closed.");
    original();
    return null;
  }
   protected Void hook36() throws IOException {
    crc.reset();
    original();
    return null;
  }
   protected void hook37(  byte[] b,  int off,  int len) throws IOException {
    if (len > 0)     crc.update(b,off,len);
    original(b,off,len);
  }
   protected Void hook38() throws IOException {
    if (crc == null)     throw new IOException("Stream closed.");
    original();
    return null;
  }
   protected Void hook39() throws IOException {
    if ((crc.getValue() & 0xffffffffL) != entry.getCrc())     throw new ZipException("CRC mismatch");
    crc.reset();
    original();
    return null;
  }
   protected Void hook40() throws IOException {
    crc=null;
    original();
    return null;
  }
}
