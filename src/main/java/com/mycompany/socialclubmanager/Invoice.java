/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socialclubmanager;

/**
 *
 * @author casa
 */
public class Invoice extends InvocesClub { // Contador para generar IDs únicos
private final String affiliateName;
    private String id;

    // Constructor
   public Invoice(double amount, String affiliateName) {
        super(amount); // Llama al constructor de la clase base
        this.affiliateName = affiliateName; // Asigna el nombre del afiliado
    }

    public String getAffiliateName() {
        return affiliateName; // Retorna el nombre del afiliado
    }

    // Métodos getters
    @Override
    public int getInvoiceId() {
        return invoiceId; // Retorna el ID de la factura
    }

    @Override
    public double getAmount() {
        return amount; // Retorna el monto de la factura
    }

    @Override
    public boolean isPaid() {
        return paid; // Devuelve el estado de pago
    }

    // Método para marcar la factura como pagada
    @Override
    public void setPaid(boolean paid) {
        this.paid = paid; // Cambia el estado de la factura a pagada
    }

    // Método para pagar la factura
    @Override
    public void payInvoice() {
        if (!paid) { // Verifica si la factura no está pagada
            setPaid(true); // Marca la factura como pagada
            System.out.println("Invoice " + invoiceId + " has been paid.");
        } else {
            System.out.println("Invoice " + invoiceId + " is already paid.");
        }
    }

    public String getId() {
        return this.id; // Devuelve el ID de la factura
    }
}



