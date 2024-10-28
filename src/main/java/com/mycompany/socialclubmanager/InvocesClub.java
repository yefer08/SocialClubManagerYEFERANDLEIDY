/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socialclubmanager;

/**
 *
 * @author casa
 */
public abstract class InvocesClub {
    private static int invoiceCounter = 1; // Contador para generar IDs únicos
    int invoiceId; // ID de la factura
    double amount; // Monto de la factura
    boolean paid; // Estado de la factura

    // Constructor
    protected InvocesClub(double amount) {
        this.invoiceId = invoiceCounter++;
        this.amount = amount;
        this.paid = false; // Inicialmente, la factura no está pagada
    }

    // Métodos getters
    public int getInvoiceId() {
        return invoiceId; // Retorna el ID de la factura
    }

    public double getAmount() {
        return amount; // Retorna el monto de la factura
    }

    public boolean isPaid() {
        return paid; // Devuelve el estado de pago
    }

    // Método para marcar la factura como pagada
    protected void setPaid(boolean paid) {
        this.paid = paid; // Cambia el estado de la factura a pagada
    }

    // Método para pagar la factura
    public void payInvoice() {
        if (!paid) { // Verifica si la factura no está pagada
            setPaid(true); // Marca la factura como pagada
            System.out.println("Invoice " + invoiceId + " has been paid.");
        } else {
            System.out.println("Invoice " + invoiceId + " is already paid.");
        }
    }
}
    
