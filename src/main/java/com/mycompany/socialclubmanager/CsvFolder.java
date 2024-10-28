/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socialclubmanager;

/**
 *
 * @author casa
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JOptionPane;

public class CsvFolder {
    private final String nameFile;

    public CsvFolder(String nameFile) {
        this.nameFile = nameFile;
    }

    // Método que recibe Listas de ID de miembros y nombres de afiliados
    public void writerFile(List<String> userList, List<String> affiliates) {
        try (FileWriter fileWriter = new FileWriter(nameFile);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escribir la cabecera
            printWriter.println("ID,NAME_MEMBER");

            // Escribir los datos de las listas en el archivo CSV
            for (int i = 0; i < userList.size(); i++) {
                String userId = userList.get(i);
                String affiliateName = affiliates.get(i);
                // Asegurarse de que los datos estén entre comillas
                printWriter.println("\"" + userId + "\",\"" + affiliateName + "\"");
            }

            System.out.println("Archivo CSV creado exitosamente: " + nameFile);

        } catch (IOException e) {
            showErrorDialog("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace(); // Manejo de excepciones
        }
    }

    // Método que recibe arrays de ID de miembros y nombres de afiliados
    public void writerFile(String[] userList, String[] affiliates) {
        try (FileWriter fileWriter = new FileWriter(nameFile);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escribir la cabecera
            printWriter.println("ID - NAME,NAME_MEMBER");

            // Escribir los datos de los arrays en el archivo CSV
            for (int i = 0; i < userList.length; i++) {
                String userId = userList[i];
                String affiliateName = affiliates[i];

                printWriter.println("\"" + userId + "\",\"" + affiliateName + "\"");
            }

            System.out.println("CSV file created successfully: " + nameFile);

        } catch (IOException e) {
            showErrorDialog("Error writing to file: " + e.getMessage());
            e.printStackTrace(); // Manejo de excepciones
        }
    }

    // Método para limpiar el archivo existente
    public void clearFile() {
        try (FileWriter fileWriter = new FileWriter(nameFile);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            // Escribir solo la cabecera
            printWriter.println("ID - NAME,NAME_MEMBER");
            System.out.println("CSV file deleted and prepared for new data: " + nameFile);
        } catch (IOException e) {
            showErrorDialog("Error cleaning file: " + e.getMessage());
            e.printStackTrace(); // Manejo de excepciones
        }
    }

    // Método para leer el archivo CSV y mostrar su contenido
    public void displayCsvContent() {
        StringBuilder content = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nameFile))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n"); // Agregar cada línea al StringBuilder
            }
        } catch (IOException e) {
            showErrorDialog("Error reading file: " + e.getMessage());
            e.printStackTrace(); // Manejo de excepciones
        }

        // Mostrar el contenido en un cuadro de diálogo
        JOptionPane.showMessageDialog(null, content.toString(), "CSV File Content", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para mostrar diálogos de error
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}





 
