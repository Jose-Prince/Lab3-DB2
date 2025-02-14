import java.util.HashMap;
import java.util.Map;

public class Relacion {

    private HashMap<String, String> atributos;


    // Method to concatenate key-value pairs
    public String concatenateAttributes(String relacion) {
        StringBuilder concatenatedString = new StringBuilder("{ ");

        // Iterate over each key-value pair in the HashMap
        for (Map.Entry<String, String> entry : atributos.entrySet()) {
            concatenatedString.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }

        // Remove the last ", " if the string is not empty
        if (concatenatedString.length() > 2) {
            concatenatedString.setLength(concatenatedString.length() - 2);
        }

        concatenatedString.append(" }");
        return concatenatedString.toString();
    }

    // Method to add attributes
    public void addAttribute(String key, String value) {
        this.atributos.put(key, value);
    }

    public Relacion(String relacion) {
        this.relacion = relacion;
        this.atributos = new HashMap<>();
    }
    
    public void setAtributos(HashMap<String, String> atributos) {
        this.atributos = atributos;
    }

}
