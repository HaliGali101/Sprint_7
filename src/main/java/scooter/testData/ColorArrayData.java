package scooter.testData;

import java.util.ArrayList;

public class ColorArrayData {

    public ArrayList<String> getColorData(String colorConfig) {
        ArrayList<String> colorArrayData = new ArrayList<>();
        switch (colorConfig) {
            case "black":
                colorArrayData.add("BLACK");
            case "gray":
                colorArrayData.add("GRAY");
            case "all":
                colorArrayData.add("BLACK");
                colorArrayData.add("GRAY");
        }
        return colorArrayData;
    }
}
