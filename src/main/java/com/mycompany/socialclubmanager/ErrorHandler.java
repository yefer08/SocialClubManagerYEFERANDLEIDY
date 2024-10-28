/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socialclubmanager;

/**
 *
 * @author casa
 */
import java.util.List;

public class ErrorHandler {

    // Excepciones personalizadas
    public static class MaxUsersException extends Exception {
        public MaxUsersException(String message) {
            super(message);
        }
    }

    public static class DuplicateIDException extends Exception {
        public DuplicateIDException(String message) {
            super(message);
        }
    }

    public static class MaxVIPMembersException extends Exception {
        public MaxVIPMembersException(String message) {
            super(message);
        }
    }

    public static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    public static class InvalidInvoiceAmountException extends Exception {
        public InvalidInvoiceAmountException(String message) {
            super(message);
        }
    }

    public static class MemberNotFoundException extends Exception {
        public MemberNotFoundException(String message) {
            super(message);
        }
    }

    // Verificar el número máximo de usuarios
    public static void checkMaxUsers(int currentSize, int maxSize) throws MaxUsersException {
        if (currentSize >= maxSize) {
            throw new MaxUsersException("Cannot register more users. Maximum limit reached: " + maxSize);
        }
    }

    // Verificar ID duplicado
    public static void checkDuplicateID(String id, List<Member> userList) throws DuplicateIDException {
        for (Member member : userList) {
            if (member.getId().equals(id)) {
                throw new DuplicateIDException("The ID " + id + " is already registered.");
            }
        }
    }

    // Verificar el límite de miembros VIP
    public static void checkVIPLimit(int currentVIPCount, int maxVIP) throws MaxVIPMembersException {
        if (currentVIPCount >= maxVIP) {
            throw new MaxVIPMembersException("Cannot register more VIP members. Maximum limit reached: " + maxVIP);
        }
    }

    // Verificar fondos disponibles
    public static void checkAvailableFunds(double funds) throws InsufficientFundsException {
        if (funds < 0) {
            throw new InsufficientFundsException("Funds cannot be negative.");
        }
    }

    // Verificar que el monto de la factura sea válido
    public static void checkValidInvoiceAmount(double amount) throws InvalidInvoiceAmountException {
        if (amount <= 0) {
            throw new InvalidInvoiceAmountException("Invoice amount must be positive.");
        }
    }

}


