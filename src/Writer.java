
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wrapper for FileOutputStream
 *
 * @author delevoye977
 */
public class Writer {

    private String path;
    private FileOutputStream fs = null;

    /**
     * The class is a wrapper for FileOutputStream
     *
     * @param path path to the PCM file
     */
    public Writer(String path) {
        this.path = path;
        try {
            this.fs = new FileOutputStream(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(byte[] data) {
        try {
            this.fs.write(data);
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void forceFlush() {
        try {
            this.fs.flush();
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
