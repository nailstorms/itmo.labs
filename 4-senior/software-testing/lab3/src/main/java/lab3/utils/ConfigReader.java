package lab3.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {
    public static JSONObject readJSONConfig (String path) {
        try {
            return (JSONObject) new JSONParser().parse(new FileReader(path));
        } catch (IOException | ParseException ignored) {
            throw new IllegalArgumentException("Error while reading the config file.");
        }
    }
}
