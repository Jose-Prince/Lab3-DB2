package com.labdb.app;

import java.util.ArrayList;


public class Nodo {

    public String nodo_name;
    public ArrayList<HashMap<String, String>> atributos;

    // Method to concatenate key-value pairs
    public void concatenateAttributes(ArrayList<HashMap<String, String>> atributos) {
        // Initialize an empty string for concatenation
        String concatenatedString = "{ ";

        // Iterate over each HashMap in the ArrayList
        for (int i = 0; i < atributos.size(); i++) {
            HashMap<String, String> map = atributos.get(i);  // Get each HashMap
            
            // Iterate over each key-value pair in the HashMap
            for (String key : map.keySet()) {
                String value = map.get(key);

                // Concatenate "key: value" to the string
                concatenatedString += key + ": " + value + ", ";
            }
        }

        // Remove the last ", " if the string is not empty
        if (concatenatedString.length() > 2) {
            concatenatedString = concatenatedString.substring(0, concatenatedString.length() - 2);
        }

        // Close the string with a closing brace
        concatenatedString += " }";

        // Print the final concatenated string
        System.out.println(concatenatedString);
}
    public String getNodoName() {
            return nodo_name;
        }

    // Setter for nodo_name
    public void setNodoName(String nodo_name) {
        this.nodo_name = nodo_name;
    }



}