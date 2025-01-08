package com.sdt.logging;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;

public class WriteJsonFile {

    public static void writeFile(JSONObject node) {
        try {


            FileWriter fWriter = new FileWriter(
                    "/Users/balra/OneDrive/Desktop/cricket-sim/cricket-bldc-batting/Media/act.json");

            fWriter.write(node.toString());

            fWriter.close();
        } catch (IOException e) {

            // Print the exception
            System.out.print(e.getMessage());
        }
    }
    public static JSONObject readFile() throws IOException, ParseException {
        JSONParser parser = new JSONParser();


            //Use JSONObject for simple JSON and JSONArray for array of JSON.
            JSONObject data = (JSONObject) parser.parse(
                    new FileReader("/Users/balra/OneDrive/Desktop/cricket-sim/cricket-bldc-batting/Media/act.json"));//path to the JSON file.

            return data;
    }
}
