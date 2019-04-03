import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * A simple program that reads a text file line-by-line using Java8.
 * @author Denis Giovan Marques
 */
public class SpreadProcessInstances {

    private int numberOfQueues = 10;
    private final String characters = "1234567890abcdef";
    private Map<String, Integer> queueByChar = new HashMap<>();
    private String fileName = "ids-procinst-oi-homologa.txt";
    private Map<String, Integer> instancesByQueue = new HashMap<>();

    public static void main(String[] args) {
        SpreadProcessInstances spi = new SpreadProcessInstances();
        spi.doit();
        spi.printSpreadResult();
    }

    public SpreadProcessInstances() {
        int queue = 1;

        for (int c=0; c < this.characters.length(); c++) {
            this.queueByChar.put(Character.toString(this.characters.charAt(c)), queue);
            queue++;

            if (queue > numberOfQueues) {
                queue = 1;
            }
        }

        System.out.println("*********************************************");
        System.out.println(Collections.singletonList(this.queueByChar));
        System.out.println("*********************************************");
    }

    private void doit() {
        try {
            Stream<String> lines = Files.lines(Paths.get(this.fileName));

            System.out.println("<!-----Read all lines as a Stream-----!>");

            String lastLine = "";

            for (String line: lines.collect(Collectors.toList())) {
                if (!line.equals(lastLine)) {
                    System.out.print(line + " - ");
                    System.out.println(getQueueForLastCharacter(line));
                    lastLine = line;
                }
            }

            lines.close();
        } catch(IOException io) {
            io.printStackTrace();
        }
    }

    private String getQueueForLastCharacter(String pid) {
        String lastChar = pid.substring(pid.length() - 2, pid.length() - 1);
        String queue = this.queueByChar.get(lastChar).toString();

        // Total of instances by queue
        Integer total = this.instancesByQueue.get(queue);
        if (total == null) total = 0;
        this.instancesByQueue.put(queue, total+1);
        return queue;
    }

    private void printSpreadResult() {
        System.out.println("================================================");
        System.out.println(Collections.singletonList(this.instancesByQueue));
    }
}
