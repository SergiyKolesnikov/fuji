package Everything;import java.util.LinkedList;




public interface Receiver {

    public void receive();

    public void close();

    public int getMessageCount();

    public LinkedList getMessageProperties(int n);

    public String getTextContent(int n);

    public LinkedList getFileAttachments(int n);

    public void saveFile(int n, int fileNumber, String destination);
}