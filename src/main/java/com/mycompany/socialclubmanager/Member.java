/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socialclubmanager;

/**
 *
 * @author casa
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
 
public class Member {
    private String name;
    private String id;
    private double availableFunds;
    private String subscription;
    private List<Affiliates> affiliates;  // Lista de afiliados
    private List<Invoice> invoices; // Lista de facturas
    private final int MAX_INVOICES = 20;
 
    // Constructor sin parámetros
    public Member() {
        this.affiliates = new ArrayList<>(); // Inicializa la lista de afiliados
        this.invoices = new ArrayList<>(); // Inicializar la lista de facturas
    }
 
    // Constructor con parámetros
    public Member(String name, String id) {
        this.name = name;
        this.id = id;
        this.affiliates = new ArrayList<>();
        this.invoices = new ArrayList<>(); // Inicializar la lista de facturas
    }
 
    // Getters y Setters
    public String getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getSubscription() {
        return subscription;
    }
 
    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
 
    public void setAvailableFunds(int availableFunds) {
        this.availableFunds = availableFunds;
    }
 
    public double getAvailableFunds() {
        return availableFunds;
    }
 
    public void setAffiliates(List<Affiliates> affiliates) {
        this.affiliates = affiliates;
    }
 
    public List<Affiliates> getAffiliates() {
        return affiliates;
    }
 
    public boolean hasPendingInvoices() {
        return invoices.stream().anyMatch(invoice -> !invoice.isPaid());
    }
 
    public int getAffiliateCount() {
        return affiliates.size(); // Retorna el tamaño de la lista de afiliados
    }
    public List<Invoice> getInvoices() {
        return invoices; // Devuelve la lista de facturas
    }
    public List<String> getInvoicesAsString() {
        List<String> invoicesAsString = new ArrayList<>();
        for (Invoice invoice : invoices) {
            invoicesAsString.add(String.valueOf(invoice)); // Convierte Integer a String
        }
        return invoicesAsString;
    }

    
 
    public void addInvoice(double amount, String affiliateName) {
        try {
            ErrorHandler.checkValidInvoiceAmount(amount);
            if (invoices.size() < MAX_INVOICES) {
                invoices.add(new Invoice(amount, affiliateName));
                if (affiliateName != null) {
                    System.out.println("Invoice created for " + name + " by affiliate " + affiliateName + " with amount: " + amount);
                } else {
                    System.out.println("Invoice created for " + name + " with amount: " + amount);
                }
            } else {
                System.out.println("Maximum number of invoices reached for " + name + ".");
            }
        } catch (ErrorHandler.InvalidInvoiceAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
 
    // Método para buscar una factura por ID
    public Invoice getInvoiceById(int invoiceId) {
        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceId() == invoiceId) { 
                return invoice; // Devuelve la factura si se encuentra
            }
        }
        return null; // Devuelve null si no se encuentra
    }
 
    public void payInvoice(int invoiceId) {
        Invoice invoiceToPay = getInvoiceById(invoiceId); // Busca la factura por ID
        try {
            if (invoiceToPay != null && !invoiceToPay.isPaid()) { // Verifica que la factura exista y no esté pagada
                ErrorHandler.checkAvailableFunds(availableFunds - invoiceToPay.getAmount());
                invoiceToPay.setPaid(true); // Marca la factura como pagada
                availableFunds -= invoiceToPay.getAmount(); // Descuenta el monto de los fondos disponibles
                System.out.println("Invoice " + invoiceId + " has been paid."); // Mensaje de éxito
            } else {
                System.out.println("Invoice not found or already paid."); // Mensaje de error
            }
        } catch (ErrorHandler.InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
 
    public String listInvoices() {
    StringBuilder invoicesList = new StringBuilder();
    
    if (invoices.isEmpty()) {
        invoicesList.append("No invoices found for this member.");
    } else {
        invoicesList.append("Invoices for member ").append(this.name).append(":\n");
        for (Invoice invoice : invoices) { 
            invoicesList.append("Invoice ID: ").append(invoice.getId()).append(", Amount: ").append(invoice.getAmount()).append("\n");
        }
    }
    
    return invoicesList.toString(); // Devuelve la lista como un String
}
 
    // Método para agregar fondos
    public void addFunds() {
    int response = JOptionPane.showConfirmDialog(null, "Do you want to add additional funds?", "Add Funds", JOptionPane.YES_NO_OPTION);
    
    if (response == JOptionPane.YES_OPTION) {
        String inputAmount = JOptionPane.showInputDialog("Enter the amount to add:");
        
        if (inputAmount != null && !inputAmount.trim().isEmpty()) {
            try {
                int additionalFunds = Integer.parseInt(inputAmount.trim());
                // Verificar si el monto total es válido
                ErrorHandler.checkAvailableFunds(this.availableFunds + additionalFunds);
                this.availableFunds += additionalFunds;
                JOptionPane.showMessageDialog(null, "Funds updated. Total funds: " + this.availableFunds);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value.");
            } catch (ErrorHandler.InsufficientFundsException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Amount cannot be empty. Please try again.");
        }
    }
}

 
    // Método para agregar un afiliado por nombre
    public void addAffiliate(Scanner sc) {
        System.out.println("Enter affiliate's name:");
        String affiliateName = sc.nextLine();
        Affiliates newAffiliate = new Affiliates(affiliateName); // Crea un objeto Affiliates
        affiliates.add(newAffiliate);  // Agrega el afiliado a la lista
        System.out.println("Affiliate added: " + affiliateName);
    }
 
    // Método para listar y agregar múltiples afiliados
    public void manageAffiliates() {
    StringBuilder affiliatesList = new StringBuilder(); // Para construir la lista de afiliados
    affiliatesList.append("You can add affiliates for this member. Type 'stop' to finish.\n");

    // Mostrar el ID y el nombre del miembro al inicio
    affiliatesList.append("Member ID: ").append(this.id).append("\n");
    affiliatesList.append("Member Name: ").append(this.name).append("\n");

    while (true) {
        String affiliateName = JOptionPane.showInputDialog("Enter affiliate's name (or type 'stop' to finish):");

        // Salir del ciclo si el usuario cancela o escribe "stop"
        if (affiliateName == null || affiliateName.equalsIgnoreCase("stop")) {
            break;
        }

        affiliateName = affiliateName.trim();

        if (affiliateName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Affiliate name cannot be empty. Please try again.");
            continue;  // Volver a pedir el nombre si está vacío
        }

        Affiliates newAffiliate = new Affiliates(affiliateName);
        affiliates.add(newAffiliate);  // Agregar el afiliado a la lista
        JOptionPane.showMessageDialog(null, "Affiliate added: " + affiliateName);
    }

    // Mostrar la lista completa de afiliados
    StringBuilder resultMessage = new StringBuilder();
    resultMessage.append("=== Affiliates list for Member ===\n");
    resultMessage.append("Member ID: ").append(this.id).append("\n");
    resultMessage.append("Member Name: ").append(this.name).append("\n");
    
    if (affiliates.isEmpty()) {
        resultMessage.append("No affiliates added.");
    } else {
        for (Affiliates affiliate : affiliates) {
            resultMessage.append("Affiliate: ").append(affiliate.getName()).append("\n");
        }
    }

    resultMessage.append("Affiliate management completed.");

    // Mostrar el resultado final en un cuadro de diálogo
    JOptionPane.showMessageDialog(null, resultMessage.toString());
}


 
    // Método para mostrar los afiliados
    public void showAffiliates(Scanner sc) {
        System.out.println("Enter Member ID to view your affiliates: ");
        String memAffiliates = sc.nextLine(); // Leer ID del miembro
        // Verificar si el ID del miembro coincide
        if (this.id.equals(memAffiliates)) {
            // Verificar si el miembro tiene afiliados
            if (affiliates.isEmpty()) {
                System.out.println("There are no affiliates registered for " + name + ".");
            } else {
                System.out.println("Affiliates for " + name + ":");
                // Mostrar la lista de afiliados (nombres)
                for (Affiliates affiliate : affiliates) {
                    System.out.println("- " + affiliate);
                }
            }
        } else {
            System.out.println("Member ID does not match. Affiliates cannot be shown.");
        }
    }

   public String getAffiliatesAsString() {
        return affiliates.stream()
                .map(Affiliates::toString) 
                .collect(Collectors.joining(", ")); // Une los nombres separados por comas
    }
    
}




