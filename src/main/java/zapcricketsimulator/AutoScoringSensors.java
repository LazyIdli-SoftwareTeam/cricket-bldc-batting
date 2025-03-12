package zapcricketsimulator;
import jssc.SerialPort;
import jssc.SerialPortEventListener;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;
import javafx.application.Platform;

import java.io.OutputStream;
import java.util.HashMap; // import the HashMap class
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Arrays;



public class AutoScoringSensors {
    String port;
    Boolean connected = false;
    public static Boolean ballReleased = false;
    public static int baudRate = 11200;
    SerialPort serialPort;
    public static int score = 0;
    String[] thresholds;
    String[] scores = {"NoRun", "Out", "NoRun", "2OFF", "2STG", "3STG", "2LEG", "3LEG","3OFF", "6STG", "4STG", "4STG", "6STG"};
    AutoScoringSensors(String port) {
        this.port = port;
        this.connect();
        this.jsonReader();
        this.changeThresholds();
    }

    public void readThresholds() {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    String command = "AT+THRSH?="; // Example UART command
                    this.serialPort.writeBytes(command.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
    }

    public void changeThresholds() {
        try {
            StringBuilder command = new StringBuilder("AT+THRSHBULK="); // Example UART command
            for (int i = 0; i < this.thresholds.length; i++) {
                command.append(this.thresholds[i]);
                if (i < this.thresholds.length - 1) {
                    command.append(",");
                }
            }
            command.append("\r\n");
            this.serialPort.writeBytes(command.toString().getBytes());
            System.out.println("command: " + command);
            this.readThresholds();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



        public void jsonReader() {
            try {
                String workingDir = System.getProperty("user.dir");;
                String configFile = "/Media/autoScore.json";
                File file =new File(workingDir, configFile);
                if(!file.exists()){
                    System.out.println("no file found " + workingDir);
                    //ErrorAlert.info("File not found");
                    return;
                }
                // Read JSON file
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(file);

                // Convert "scores" to a String array
                String[] scores = mapper.convertValue(root.get("scores"), String[].class);
                // Convert "thresholds" to a String array
                String[] thresholds = mapper.convertValue(root.get("thresholds"), String[].class);
                this.scores = scores;
                this.thresholds  = thresholds;
                // Print results
                System.out.println("Sending Scores: " + Arrays.toString(scores));
                System.out.println("Sending Thresholds: " + Arrays.toString(thresholds));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public void connect() {
        try {
            AutoScoringSensors.score = 0;
            serialPort = new SerialPort(this.port);
            serialPort.openPort();
            serialPort.setParams(AutoScoringSensors.baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            System.out.println("connected to autoscoring sensors!");
            serialPort.addEventListener(this.dataReceiver());
        } catch (Exception e) {
            this.connected = false;
        }
    }

    HashMap<String, String> parseData(String cmd) {
        String[] split =  cmd.split("\\+");
        split = split[1].split("\\=");
        HashMap<String, String> parsedCmd = new HashMap<>();
        parsedCmd.put(split[0], split[1]);
        return parsedCmd;
    }

    void dataHandler(String cmd) {
        HashMap<String, String> parsedCmd = parseData(cmd);
        //System.out.println("has");
        //System.out.println(parsedCmd.get("SNSRACT"));
        String sensorData = parsedCmd.get("SNSRACT");
        String[] split = sensorData.split("\\,");
        String score = "NoRun";
        if (sensorData.length() > 1) {
            for (int i = 0; i < split.length; i++) {
                if (split[i].equals("1")) {
                    score = this.scores[i];
                }
            }
        }
        this.ballReleased = false;
        this.handleScore(score);
        System.out.println("Score Detected: " + score + " Data Rec from sensors: " + sensorData );
        AutoScoringSensors.score++;

    }


    public void handleScore (String score) {
        switch (score) {
            case "1LEG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 1);
                break;
            case "1OFF":
                HandleEvents.handleEvent(Variables.button_type_result_runs_off, 1);
                break;
            case "1STG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 1);
                break;
            case "2LEG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 2);
                break;
            case "2OFF":
                HandleEvents.handleEvent(Variables.button_type_result_runs_off, 2);
                break;
            case "2STG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 2);
                break;
            case "3LEG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 3);
                break;
            case "3OFF":
                HandleEvents.handleEvent(Variables.button_type_result_runs_off, 3);
                break;
            case "3STG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 3);
                break;
            case "4LEG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 4);
                break;
            case "4OFF":
                HandleEvents.handleEvent(Variables.button_type_result_runs_off, 4);
                break;
            case "4STG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 4);
                break;
            case "6LEG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 6);
                break;
            case "6OFF":
                HandleEvents.handleEvent(Variables.button_type_result_runs_off, 6);
                break;
            case "6STG":
                HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 6);
                break;
            case "NoRun":
                HandleEvents.handleEvent(Variables.button_type_result_norun, 0);
                break;
            case "NoBall":
                HandleEvents.handleEvent(Variables.button_type_result_freehit, 0);
                break;
            case "Out":
                HandleEvents.handleEvent(Variables.button_type_result_bowled, 0);
                break;
            case "Wide":
                HandleEvents.handleEvent(Variables.button_type_result_wide, 0);
                break;
            default:
                HandleEvents.handleEvent(Variables.button_type_result_norun, 0);
        }
    }



    SerialPortEventListener dataReceiver() {
        return event -> {
            if (event.isRXCHAR() && event.getEventValue() > 0) { // Data received
                Platform.runLater(() -> {
                    try {
                        //System.out.println("gone here for auto");
                        String receivedData = serialPort.readString();
                        System.out.println("Got message from sensors: " + receivedData);
                        if (!AutoScoringSensors.ballReleased) return;
                        if (receivedData.contains("ERROR")) return;
                        if (receivedData.startsWith("OK")) {
                            this.connected = true;
                        } else {
                            this.dataHandler(receivedData);
                        }
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
                });
            }
        };
    }

}
