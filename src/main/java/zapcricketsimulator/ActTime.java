package zapcricketsimulator;

import com.sdt.serial.USB_Com;
import javafx.scene.control.Alert;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ActTime {
    static boolean timeout = false;
    public static boolean calibrate = false;
    public static byte DEFAULT_MSB = 0x00;
    public static byte DEFAULT_LSB = (byte) 0xFA;
    //seamin /offspin left wheel up right wheel full down
    //leg spin /seam out left wheel down right wheel up
//        ArrayList<KeyValueBean> Bowling_Types = new ArrayList(Arrays.asList(new KeyValueBean(3, "Speed"),new KeyValueBean(6, "Off_Spin"),new KeyValueBean(7, "Leg_Spin"),new KeyValueBean(8, "Seem_In"),new KeyValueBean(9, "Seem_Out"),new KeyValueBean(12, "Seem_Magic"),new KeyValueBean(13, "Spin_Magic"),new KeyValueBean(14, "Bowler_Magic")));

    public static void previousConfig(int type, int prv_type) {
        System.out.println(type + " " +
                prv_type);
        if (type == 3 && prv_type == 6 || type == 3 && prv_type == 8) {
            fastSeemIn();
            System.out.println("1st");
        } else if (type == 3 && prv_type == 9 || type == 3 && prv_type == 7) {
            fastSeamOut();
            System.out.println("2 nd");
        } else if (type == 6 || type == 8) {
            seamIn();
        } else  if (type == 9 || prv_type == 7) {
            seamOut();
        }
    }
    public static void fast(int previousBowlingType) {
        byte byteval[] = new byte[12];
        //pan
        //0x01 left
        //0x02 right
        byteval[0] = 0x00;
        byteval[1] = 0x00;
        byteval[2] = (byte) 0x00;
        //tilt
        //0x01 up
        //0x02 down
        byteval[3] = 0x00;
        byteval[4] = 0x00;
        byteval[5] = (byte) 0x00;

        //left motor
        byteval[6] = 0x02;
        byteval[7] =  0x13 ;
        byteval[8] = (byte) 0x88 ;

        //right motor
        byteval[9] = 0x01;
        byteval[10] = 0x13 ;
        byteval[11] = (byte) 0x88 ;
        byte[] data = USB_Com.getCmd(0x84, byteval, 12);
        USB_Com.WriteData(data);
    }

    public static void fastSeemIn() {
        byte byteval[] = new byte[12];
        //pan
        //0x01 left
        //0x02 right
        byteval[0] = 0x00;
        byteval[1] = 0x00;
        byteval[2] = (byte) 0x00;
        //tilt
        //0x01 up
        //0x02 down
        byteval[3] = 0x00;
        byteval[4] = 0x00;
        byteval[5] = (byte) 0x00;

        //left motor
        byteval[6] = 0x01;
        byteval[7] =  0x09 ;
        byteval[8] = (byte) 0xC4;

        //right motor
        byteval[9] = 0x02;
        byteval[10] = 0x09;
        byteval[11] = (byte) 0xC4;
        byte[] data = USB_Com.getCmd(0x84, byteval, 12);
        USB_Com.WriteData(data);
    }

    public static void parseAndPrintHexMSBLSB(int input) {
        byte msb = (byte) ((input >> 8) & 0xFF);  // cast to byte
        byte lsb = (byte) (input & 0xFF);         // cast to byte
        DEFAULT_MSB = msb;
        DEFAULT_LSB = lsb;
        System.out.println(DEFAULT_LSB + " " +  DEFAULT_MSB);
        System.out.printf("MSB: 0x%02X, LSB: 0x%02X\n", msb & 0xFF, lsb & 0xFF);
    }

    public static void fastSeamOut() {
        byte byteval[] = new byte[12];
        //pan
        //0x01 left
        //0x02 right
        byteval[0] = 0x00;
        byteval[1] = 0x00;
        byteval[2] = (byte) 0x00;
        //tilt
        //0x01 up
        //0x02 down
        byteval[3] = 0x00;
        byteval[4] = 0x00;
        byteval[5] = (byte) 0x00;

        //left motor
        byteval[6] = 0x02;
        byteval[7] =  0x09 ;
        byteval[8] = (byte) 0xC4 ;

        //right motor
        byteval[9] = 0x01;
        byteval[10] = 0x09 ;
        byteval[11] = (byte) 0xC4;
        byte[] data = USB_Com.getCmd(0x84, byteval, 12);
        USB_Com.WriteData(data);
    }

    public static void  seamIn() {
        byte byteval[] = new byte[12];
        //pan
        //0x01 left
        //0x02 right
        byteval[0] = 0x00;
        byteval[1] = 0x00;
        byteval[2] = (byte) 0x00;
        //tilt
        //0x01 up
        //0x02 down
        byteval[3] = 0x00;
        byteval[4] = 0x00;
        byteval[5] = (byte) 0x00;

        //left motor
        byteval[6] = 0x02;
        byteval[7] =  0x13 ;
        byteval[8] = (byte) 0x88 ;

        //right motor
        byteval[9] = 0x01;
        byteval[10] = 0x13 ;
        byteval[11] = (byte) 0x88 ;
        byte[] data = USB_Com.getCmd(0x84, byteval, 12);
        USB_Com.WriteData(data);
    }

    public static void  seamOut() {
        byte byteval[] = new byte[12];
        //pan
        //0x01 left
        //0x02 right
        byteval[0] = 0x00;
        byteval[1] = 0x00;
        byteval[2] = (byte) 0x00;
        //tilt
        //0x01 up
        //0x02 down
        byteval[3] = 0x00;
        byteval[4] = 0x00;
        byteval[5] = (byte) 0x00;

        //left motor
        byteval[6] = 0x01;
        byteval[7] =  0x13 ;
        byteval[8] = (byte) 0x88 ;

        //right motor
        byteval[9] = 0x02;
        byteval[10] = 0x13 ;
        byteval[11] = (byte) 0x88 ;
        byte[] data = USB_Com.getCmd(0x84, byteval, 12);
        USB_Com.WriteData(data);
    }

    public static void move(String act, String dct) {
        if (!USB_Com.status) {
            return;
        }
        if (!calibrate) {
            Alert infoAlert = new Alert(Alert.AlertType.ERROR);
            infoAlert.setTitle("Calibration!");
            infoAlert.setContentText("Calibration pending!");
            infoAlert.showAndWait();
            return;
        }
        if (timeout) {
            Alert infoAlert = new Alert(Alert.AlertType.ERROR);
            infoAlert.setTitle("Timeout!");
            infoAlert.setContentText("Wait 2 seconds!");
            infoAlert.showAndWait();
            return;
        }
        System.out.println("moveing" + act + dct);
        byte byteval[]=new byte[12];
        //pan
        //0x01 left
        //0x02 right
        parseAndPrintHexMSBLSB( HandleEvents.generalSettings.getTilt_pan_timeout());
        byteval[0] = 0x00;
        byteval[1] = 0x00;
        byteval[2] = (byte) 0x00;
        if (act.equals("PAN")) {
            if (dct.equals("LEFT")) {
                byteval[0] = 0x01;
            } else if (dct.equals("RIGHT")) {
                byteval[0] = 0x02;
            }
            byteval[1] = DEFAULT_MSB;
            byteval[2] = DEFAULT_LSB;
        }

        //tilt
        //0x01 up
        //0x02 down
        byteval[3] = 0x00;
        byteval[4] = 0x00;
        byteval[5] = (byte) 0x00;
        if (act.equals("TILT")) {
            if (dct.equals("TOP")) {
                byteval[3] = 0x01;
            } else if (dct.equals("BOTTOM")) {
                byteval[3] = 0x02;
            }
            byteval[4] = DEFAULT_MSB;
            byteval[5] = DEFAULT_LSB;
        }
        //left motor
        byteval[6] = 0x00;
        byteval[7] = 0x00;
        byteval[8] = 0x00;
        //right motor
        byteval[9] = 0x00;
        byteval[10] = 0x00;
        byteval[11] = 0x00;
        byte[] data = USB_Com.getCmd(0x84, byteval, 12);
        USB_Com.WriteData(data);
        timeout = true;
        Thread sleepThread = new Thread(() -> {
            try {

                Thread.sleep(2000); // Sleep for 2 seconds
                timeout = false;
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
            }
        });

        // Start the thread
        sleepThread.start();

    }
    public static void initCalibration() {
        if (!USB_Com.status) {
            return;
        }
        if (calibrate) {
            return;
        }
        byte byteval[] = new byte[12];
        //pan
        //0x01 left
        //0x02 right
        byteval[0] = 0x00;
        byteval[1] = 0x00;
        byteval[2] = (byte) 0x00;
        //tilt
        //0x01 up
        //0x02 down
        byteval[3] = 0x00;
        byteval[4] = 0x00;
        byteval[5] = (byte) 0x00;

        //left motor
        byteval[6] = 0x02;
        byteval[7] =  0x13 ;
        byteval[8] = (byte) 0x88 ;

        //right motor
        byteval[9] = 0x02;
        byteval[10] = 0x13 ;
        byteval[11] = (byte) 0x88 ;
        byte[] data = USB_Com.getCmd(0x84, byteval, 12);
        try {
            Thread.sleep(2000);
            USB_Com.WriteData(data);
            calibrate = true;

            System.out.println("calibrated up");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(9000);
            byteval[6] = 0x01;
            byteval[7] = 0x09;
            byteval[8] = (byte) 0xC4;
            byteval[9] = 0x01;
            byteval[10] = 0x09;
            byteval[11] = (byte) 0xC4;
            byte[] data2 = USB_Com.getCmd(0x84, byteval, 12);
            USB_Com.WriteData(data2);
            System.out.println("calibrated down");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
