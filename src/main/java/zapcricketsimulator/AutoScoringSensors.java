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



public class AutoScoringSensors implements  Runnable {
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
        System.out.println("this is running");
        Thread thread = new Thread(this); // Start the thread
        thread.setDaemon(true); // Set as daemon so it stops when the app closes
        thread.start();

    }
    @Override
    public void run() {
        this.connect();  // Start Serial Connection
        this.jsonReader(); // Read Configuration
        this.changeThresholds(); // Apply Thresholds
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
            System.out.println("Sending command: " + command);
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
                // System.out.println("no file found " + workingDir);
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
            // Print resultss
            // System.out.println("Registered Scores: " + Arrays.toString(scores));
            // System.out.println("Sending Thresholds: " + Arrays.toString(thresholds));

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
            // System.out.println("connected to autoscoring sensors!");
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
        // System.out.println("has");
        // System.out.println(parsedCmd.get("SNSRACT"));
        String sensorData = parsedCmd.get("SNSRACT");
        String[] split = sensorData.split("\\,");
        String score = "NoRun";
        if (sensorData.length() > 1) {
            for (int i = 0; i < split.length; i++) {
                if (split[i].equals("1")) {
                    score = this.scores[i];
                    break;
                }
            }
        }
        this.ballReleased = false;
        this.handleScore(score);
        // System.out.println("Score Detected: " + score + " Data Rec from sensors: " + sensorData );
        AutoScoringSensors.score++;

    }
    public static boolean isBowlingVideo(String mediaPath) {
        if (mediaPath == null || mediaPath.isEmpty()) {
            return false;
        }

        // Decode URL (removes %20 spaces)
        String decodedPath = mediaPath.replace("%20", " ");

        // Split the path by "/"
        String[] pathParts = decodedPath.split("/");

        // Check if the second last folder is "bowling"
        if (pathParts.length > 2) {
            String parentFolder = pathParts[pathParts.length - 2]; // Get parent directory
            return parentFolder.equalsIgnoreCase("bowling");
        }

        return false;
    }

    public void handleScore (String score) {
        System.out.println("sending score");
//        if (HandleEvents.machineDataBean.getBall_status() != 1) {
//            try {
//                Thread.sleep(3000);
//            } catch (Exception e) {}
//        }
//        if (MediaStageNew.mp != null) {
//            Platform.runLater(() -> {
//                try {
//                    if (isBowlingVideo(MediaStageNew.mp.getMedia().getSource())) {
//                        MediaStageNew.mp.stop();
//                        MediaStageNew.mp.dispose();
//                        MediaStageNew.mp = null;
//                        System.out.println("Video stopped and disposed before sending score.");
//                    }
////                    MediaStageNew.mp.onEndOfMediaProperty()
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        MediaStageNew.mp.dispose();
        switch (score) {
            case "1LEG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 1);
                });
                break;
            case "1OFF":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_off, 1);
                });
                break;
            case "1STG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 1);

                });
                break;
            case "2LEG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 2);

                });
                break;
            case "2OFF":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_off, 2);

                });
                break;
            case "2STG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 2);

                });
                break;
            case "3LEG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 3);

                });
                break;
            case "3OFF":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_off, 3);
                });
                break;
            case "3STG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 3);
                });
                break;
            case "4LEG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 4);
                });
                break;
            case "4OFF":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_off, 4);
                });
                break;
            case "4STG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 4);
                });
                break;
            case "6LEG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 6);
                });
                break;
            case "6OFF":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_off, 6);

                });
                break;
            case "6STG":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 6);

                });
                break;
            case "NoRun":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_norun, 0);

                });
                break;
            case "NoBall":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_freehit, 0);

                });
                break;
            case "Out":
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_bowled, 0);

                });
                break;
            case "Wide":
                Platform.runLater(() -> {

                    HandleEvents.handleEvent(Variables.button_type_result_wide, 0);
                });
                break;
            default:
                Platform.runLater(() -> {
                    HandleEvents.handleEvent(Variables.button_type_result_norun, 0);

                });
        }
    }



    SerialPortEventListener dataReceiver() {
        return event -> {
            if (event.isRXCHAR() && event.getEventValue() > 0) { // Data received
                Platform.runLater(() -> {
                    try {
                        // System.out.println("gone here for auto");
                        String receivedData = serialPort.readString();
                        // System.out.println("Got message from sensors: " + receivedData);
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
