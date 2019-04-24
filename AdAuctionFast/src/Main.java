import java.awt.EventQueue;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    /**
     * Launch the application.
     * 
     * @throws ParseException
     * @throws IOException
     */
    public static void main(String[] args) throws ParseException, IOException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new EvaluationTool(new Controller());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
