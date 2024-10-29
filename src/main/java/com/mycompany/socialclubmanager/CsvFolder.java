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

    // Método que recibe Listas de ID de miembros y nombres de miembro
    public void writeMembersFile(List<Member> userList, String headers) {
        try (FileWriter fileWriter = new FileWriter(nameFile);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escribir la cabecera
            printWriter.println(headers);
            
            
            for (Member member : userList) {
                 printWriter.println("\"" + member.getId() + "\",\"" + member.getName() + "\"");
            }

            System.out.println("Archivo CSV creado exitosamente: " + nameFile);

        } catch (IOException e) {
            showErrorDialog("Error al escribir en el archivo: " + e.getMessage());
            // Manejo de excepciones
        }
    }
    public void writeAffiliatesFile(List<Affiliates> affiliate, String headers) {
        try (FileWriter fileWriter = new FileWriter(nameFile,true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Escribir la cabecera
            printWriter.println(headers);
            
            
            for (Affiliates afiliate:affiliate) {
                 printWriter.println("\"" + afiliate.getMemberId()+ "\",\"" + afiliate.getName()+ "\"");
            }

            System.out.println("Archivo CSV creado exitosamente: " + nameFile);

        } catch (IOException e) {
            showErrorDialog("Error al escribir en el archivo: " + e.getMessage());
            // Manejo de excepciones
        }
    }
    
    
    public void writeInvoicesFile(List<Invoice> invoices, String headers) {
    try (FileWriter fileWriter = new FileWriter(nameFile, true);
         PrintWriter printWriter = new PrintWriter(fileWriter)) {
        
         printWriter.println(headers);


        for (Invoice invoice : invoices) {
            printWriter.println("\"" + invoice.getIdmember()+ "\",\"" + invoice.getId() + "\",\"" + invoice.getAffiliateName() + "\"");
        }

        System.out.println("Archivo CSV actualizado exitosamente: " + nameFile);

    } catch (IOException e) {
        showErrorDialog("Error al escribir en el archivo: " + e.getMessage());
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
            // Manejo de excepciones
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
            // Manejo de excepciones
        }

        // Mostrar el contenido en un cuadro de diálogo
        JOptionPane.showMessageDialog(null, content.toString(), "CSV File Content", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para mostrar diálogos de error
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}




 
