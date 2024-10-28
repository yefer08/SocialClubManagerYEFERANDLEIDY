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
import javax.swing.JOptionPane;


public class ClubSocial {
    private final int MAX_USERS = 35;
    private final int MAX_VIP = 3;
    private final int REGULAR_LIMIT = 100000;
    private final int VIP_LIMIT = 5000000;
    private List<Member> membersList;

    private List<Member> userList;
    private int vipCount;

    public ClubSocial() {
        this.userList = new ArrayList<>();
        this.vipCount = 0;
        membersList = new ArrayList<>();
    }

    public List<Member> getUserList() {
        return userList;
    }
    
    public List<String> getUserListAsString() {
        List<String> userListAsString = new ArrayList<>();
        for (Member member : userList) {
            userListAsString.add(member.toString()); // Agregar la representación de String
        }
        return userListAsString;
    }
    
    

public void createInvoiceForMemberOrAffiliate(Scanner sc) {
    // Usar un JOptionPane para obtener el ID del miembro
    String memberId = JOptionPane.showInputDialog("Enter the ID of the member to create an invoice for:");

    if (memberId == null || memberId.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Member ID cannot be empty.");
        return;
    }

    Member member = findMemberById(memberId);

    if (member != null) {
        // Preguntar si la factura es para un afiliado
        int response = JOptionPane.showConfirmDialog(null, "Is this invoice for an affiliate?", "Invoice Type", JOptionPane.YES_NO_OPTION);
        
        final String affiliateName; // Declarar como final
        if (response == JOptionPane.YES_OPTION) {
            // Pedir el nombre del afiliado
            affiliateName = JOptionPane.showInputDialog("Enter the affiliate's name:");
            if (affiliateName == null || affiliateName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Affiliate name cannot be empty.");
                return;
            }

            // Verificar si el afiliado existe en la lista de afiliados del miembro
            boolean affiliateExists = member.getAffiliates().stream()
                .anyMatch(affiliate -> affiliate.getName().equalsIgnoreCase(affiliateName));

            if (!affiliateExists) {
                JOptionPane.showMessageDialog(null, "Affiliate " + affiliateName + " not found.");
                return;
            }
        } else {
            affiliateName = null; //factura es solo para el miembro
        }

        // Pedir el monto de la factura
        String amountStr = JOptionPane.showInputDialog("Enter the invoice amount:");
        if (amountStr == null || amountStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invoice amount cannot be empty.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr); // Convertir a número
            // Verificar si el monto de la factura es válido
            ErrorHandler.checkValidInvoiceAmount(amount);

            member.addInvoice(amount, affiliateName);  // Crear la factura para el miembro o su afiliado
            JOptionPane.showMessageDialog(null, "Invoice created successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a numeric value.");
        } catch (ErrorHandler.InvalidInvoiceAmountException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Member with ID " + memberId + " not found.");
    }
}

     
     public void payMemberInvoice() {
    
    String memberId = JOptionPane.showInputDialog("Enter the ID of the member to pay an invoice for:");

    if (memberId == null || memberId.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Member ID cannot be empty.");
        return;
    }

    Member member = findMemberById(memberId);

    if (member != null) {
        // Mostrar la lista de facturas del miembro
        String invoiceList = member.listInvoices(); 
        JOptionPane.showMessageDialog(null, invoiceList, "Invoices for Member " + member.getName(), JOptionPane.INFORMATION_MESSAGE);
        
        // Pedir el ID de la factura a pagar
        String invoiceIdStr = JOptionPane.showInputDialog("Enter the invoice ID to pay:");

        if (invoiceIdStr == null || invoiceIdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invoice ID cannot be empty.");
            return;
        }

        try {
            int invoiceId = Integer.parseInt(invoiceIdStr); // Convertir a número
            member.payInvoice(invoiceId); // Intentar pagar la factura
            JOptionPane.showMessageDialog(null, "Invoice paid successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid invoice ID. Please enter a numeric value.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Member with ID " + memberId + " not found.");
    }
}
     
     public void listMemberInvoices() {
    // Solicitar el ID del miembro
    String memberId = JOptionPane.showInputDialog("Enter the ID of the member to list invoices for:");

    // Validar entrada
    if (memberId == null || memberId.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Error: Member ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Salir si el ID está vacío
    }

    // Buscar el miembro por ID
    Member member = findMemberById(memberId);

    // Comprobar si el miembro existe
    if (member != null) {
        // Obtener la lista de facturas del miembro
        List<Invoice> invoices = member.getInvoices(); 
        StringBuilder invoiceList = new StringBuilder(); // Para construir la lista de facturas

        if (invoices.isEmpty()) {
            invoiceList.append("No invoices found for this member.");
        } else {
            // Construir la lista de facturas con su estado
            for (Invoice invoice : invoices) {
                String status = invoice.isPaid() ? "Paid" : "Not Paid"; // Verificar el estado de pago
                invoiceList.append("Invoice ID: ").append(invoice.getId())
                            .append(" - Amount: ").append(invoice.getAmount())
                            .append(" - Status: ").append(status).append("\n");
            }
        }

        // Mostrar la lista de facturas en un diálogo
        JOptionPane.showMessageDialog(null, invoiceList.toString(), "Invoices for Member ID: " + memberId, JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, "Member with ID " + memberId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    // Método para registrar un nuevo miembro
public void registerMember() {
    try {
        ErrorHandler.checkMaxUsers(userList.size(), MAX_USERS);

        // Solicitar el nombre del usuario
        String name = JOptionPane.showInputDialog("Enter the user's name:");
        // Solicitar el ID del usuario
        String id = JOptionPane.showInputDialog("Enter the user's ID:");
        
        // Verificar duplicados
        ErrorHandler.checkDuplicateID(id, userList);

        // Solicitar el monto de suscripción
        String fundsInput = JOptionPane.showInputDialog("Enter the subscription amount:");
        int funds = Integer.parseInt(fundsInput); // Convertir la entrada a entero

        ErrorHandler.checkAvailableFunds(funds);  // Check available funds

        Member newUser = new Member(name, id); // Create the new member

        // Logic to validate subscription amount
        if (funds < 50000) { // Check for minimum subscription amount
            JOptionPane.showMessageDialog(null, "Amount is below the minimum required subscription of 50,000. Please enter a valid amount.");
            return;
        } else if (funds < 100000) {
            if (funds <= REGULAR_LIMIT) {
                newUser.setSubscription("REGULAR");
                JOptionPane.showMessageDialog(null, "Subscription set to REGULAR.");
            } else {
                JOptionPane.showMessageDialog(null, "Amount exceeds the REGULAR limit of " + REGULAR_LIMIT + ". Please enter a valid amount.");
                return;
            }
        } else if (funds >= 100000 && funds <= VIP_LIMIT) {
            ErrorHandler.checkVIPLimit(vipCount, MAX_VIP);  // Check VIP limit
            newUser.setSubscription("VIP");
            vipCount++;
            JOptionPane.showMessageDialog(null, "Subscription set to VIP.");
        } else {
            JOptionPane.showMessageDialog(null, "Amount exceeds the VIP limit of " + VIP_LIMIT + ". Please enter a valid amount.");
            return;
        }

        newUser.setAvailableFunds(funds); // Set available funds
        userList.add(newUser);
        JOptionPane.showMessageDialog(null, "User added: " + name);
        
        // Agregar fondos al miembro
        newUser.addFunds(); 

        // Confirmar registro del miembro
        JOptionPane.showMessageDialog(null, "Member registered: " + newUser.getName());
        
        // Preguntar si se desea agregar afiliados
        int response = JOptionPane.showConfirmDialog(null, "Do you want to add affiliates for this member?", "Add Affiliates", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            newUser.manageAffiliates(); 
        }

    } catch (ErrorHandler.MaxUsersException | ErrorHandler.DuplicateIDException |
             ErrorHandler.MaxVIPMembersException | ErrorHandler.InsufficientFundsException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid input for funds. Please enter a numeric value.");
    }
}



    // Método para buscar un miembro por ID
    public Member findMemberById() {
    String memberId = JOptionPane.showInputDialog("Enter the ID of the member to search:");

    // Validación de entrada
    if (memberId == null || memberId.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Error: Member ID cannot be empty.", 
                                      "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    try {
        if (userList == null || userList.isEmpty()) {
            throw new ErrorHandler.MemberNotFoundException("The member list is empty.");
        }

        for (Member member : userList) {
            if (member.getId().equals(memberId.trim())) {
                StringBuilder memberInfo = new StringBuilder();
                memberInfo.append("Member found:\n")
                          .append("Name: ").append(member.getName()).append("\n")
                          .append("Subscription type: ").append(member.getSubscription()).append("\n")
                          .append("Available funds: ").append(member.getAvailableFunds()).append("\n")
                          .append("Affiliates:\n");

                List<Affiliates> affiliates = member.getAffiliates();
                if (affiliates.isEmpty()) {
                    memberInfo.append("No affiliates found for this member.");
                } else {
                    for (Affiliates affiliate : affiliates) {
                        memberInfo.append("- ").append(affiliate.getName()).append("\n");
                    }
                }

                JOptionPane.showMessageDialog(null, memberInfo.toString(), "Member Details", 
                                              JOptionPane.INFORMATION_MESSAGE);
                return member;
            }
        }

        throw new ErrorHandler.MemberNotFoundException("Member with ID " + memberId + " not found.");

    } catch (ErrorHandler.MemberNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage(), 
                                      "Error", JOptionPane.ERROR_MESSAGE);
    }

    return null;
}


    // Método para manejar afiliados de un miembro por su ID
    public void manageAffiliatesById(Scanner sc) {
        System.out.println("Enter the member's ID to manage affiliates:");
        String id = sc.nextLine();
        Member member = findMemberById();

        if (member != null) {
            member.manageAffiliates();
        } else {
            System.out.println("Member with ID " + id + " not found.");
        }
    }

    // Método para eliminar miembro o afiliado
    public void removeMemberOrAffiliate() {
    try {
        String choice = JOptionPane.showInputDialog("¿You want to delete a Member or Affiliate? (M/A)");

        if (choice == null) {
            return; // Si el usuario cancela, salir del método
        }

        choice = choice.trim().toUpperCase();

        switch (choice) {
            case "M" -> {
                // Eliminación de un miembro
                removeMember();
            }
            case "A" -> {
                // Manejo de la eliminación de un afiliado
                String memberId = JOptionPane.showInputDialog("Enter Member ID to manage affiliates:");

                if (memberId == null) {
                    return; // Si el usuario cancela, salir del método
                }

                Member member = findMemberById(memberId);

                // Verificamos si el miembro existe
                if (member != null) {
                    removeAffiliate(member);
                } else {
                    // Lanzamos una excepción si el miembro no se encuentra
                    throw new ErrorHandler.MemberNotFoundException("Member with ID " + memberId + " not found.");
                }
            }
            default -> {
                // Manejo de opción inválida
                JOptionPane.showMessageDialog(null, "Invalid option. Please enter 'M' for Member or 'A' for Affiliate.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (ErrorHandler.MemberNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        // Manejo de excepciones generales
        JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    // Método para eliminar un miembro
   public void removeMember() {
    String memberId = JOptionPane.showInputDialog("Enter the ID of the member to remove:");
    
    // Verificar si el usuario canceló el cuadro de entrada
    if (memberId == null || memberId.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Operation canceled.");
        return;
    }

    memberId = memberId.trim();
    Member memberToRemove = null;

    // Buscar el miembro en la lista
    for (Member member : userList) {
        if (member.getId().equals(memberId)) {
            memberToRemove = member;
            break;
        }
    }

    try {
        if (memberToRemove == null) {
            throw new ErrorHandler.MemberNotFoundException("Member with ID " + memberId + " not found.");
        }

        if ("VIP".equals(memberToRemove.getSubscription())) {
            throw new Exception("Cannot remove member with VIP subscription.");
        }

        if (memberToRemove.hasPendingInvoices()) {
            throw new Exception("Cannot remove member with pending invoices.");
        }

        if (memberToRemove.getAffiliateCount() > 1) {
            throw new Exception("Cannot remove member with more than one affiliate.");
        }

        userList.remove(memberToRemove);
        JOptionPane.showMessageDialog(null, "Member with ID " + memberId + " has been removed.");
    } catch (ErrorHandler.MemberNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}

// Método para eliminar un afiliado
public void removeAffiliate(Member member) {
    try {
        if (member.hasPendingInvoices()) {
            throw new Exception("Cannot remove affiliates. Member has pending invoices.");
        }

        List<Affiliates> affiliates = member.getAffiliates();
        if (affiliates.isEmpty()) {
            throw new Exception("No affiliates found for this member.");
        }

        // Mostrar la lista de afiliados en un cuadro de diálogo
        StringBuilder affiliatesList = new StringBuilder("Affiliates for member " + member.getName() + ":\n");
        for (Affiliates affiliate : affiliates) {
            affiliatesList.append("- ").append(affiliate.getName()).append("\n");
        }

        // Pedir al usuario el nombre del afiliado a eliminar
        String affiliateName = JOptionPane.showInputDialog(affiliatesList.toString() + "Enter the name of the affiliate to remove:");
        
        // Verificar si el usuario canceló o dejó el campo vacío
        if (affiliateName == null || affiliateName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Operation canceled.");
            return;
        }

        boolean removed = affiliates.removeIf(affiliate -> affiliate.getName().equalsIgnoreCase(affiliateName.trim()));

        if (removed) {
            JOptionPane.showMessageDialog(null, "Affiliate " + affiliateName + " removed.");
        } else {
            throw new Exception("Affiliate " + affiliateName + " not found.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}


public void EditInformation() {
    boolean continueEditing = true;

    String idToEdit = JOptionPane.showInputDialog("Enter the ID of the member you want to edit:");

    if (idToEdit == null || idToEdit.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Operation canceled.");
        return;
    }

    // Buscar al miembro en la lista por su ID
     Member memberToEdit = findMemberById(idToEdit);// Cambiado para usar findMemberById con el ID

    if (memberToEdit == null) {
        JOptionPane.showMessageDialog(null, "Member with ID " + idToEdit + " not found.");
        return;
    }

    while (continueEditing) {
        String options = """
                         === Edit Member Information ===
                         1. Change Name
                         2. Add Affiliates
                         3. Add Funds
                         0. Exit
                         Select an option:""";
        
        String input = JOptionPane.showInputDialog(options);
        if (input == null) {
            JOptionPane.showMessageDialog(null, "Operation canceled.");
            return;
        }

        try {
            int option = Integer.parseInt(input);

            switch (option) {
                case 1 -> {
                    String newName = JOptionPane.showInputDialog("Enter new name:");
                    if (newName != null && !newName.trim().isEmpty()) {
                        memberToEdit.setName(newName); // Cambiar el nombre del miembro en la lista
                        JOptionPane.showMessageDialog(null, "Name updated to: " + memberToEdit.getName());
                    } else {
                        JOptionPane.showMessageDialog(null, "Name cannot be empty.");
                    }
                }
                case 2 -> memberToEdit.manageAffiliates(); // Cambiado para agregar afiliados al miembro específico
                case 3 -> memberToEdit.addFunds(); // Agregar más fondos al miembro
                case 0 -> continueEditing = false; // Salir del bucle
                default -> JOptionPane.showMessageDialog(null, "INVALID OPTION, PLEASE TRY AGAIN.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
        }
    }
}

    private Member findMemberById(String idToEdit) {
    for (Member member : userList) { // userList es la lista que contiene los miembros
        if (member.getId().equals(idToEdit)) { // Compara el ID del miembro con el ID proporcionado
            return member; // Devuelve el miembro si se encuentra
        }
    }
    return null; // Si no se encuentra ningún miembro, devuelve null
}
    public void showHelp() {
    StringBuilder helpMessage = new StringBuilder();
    helpMessage.append("=== Help Menu ===\n");
    helpMessage.append("1. Register Member: \n")
               .append("   - Register a new member in the club.\n")
               .append("2. Remove Member or Affiliate: \n")
               .append("   - Remove a member or an affiliate from the club.\n")
               .append("3. Find Member by ID: \n")
               .append("   - Search for a member using their ID.\n")
               .append("4. Create Invoice for Member or Affiliate: \n")
               .append("   - Generate an invoice for a member or their affiliate.\n")
               .append("5. Pay Member Invoice: \n")
               .append("   - Process payment for a specific invoice of a member.\n")
               .append("6. List Member Invoices: \n")
               .append("   - Display all invoices associated with a member.\n")
               .append("7. Edit Information: \n")
               .append("   - Update member information, such as name or affiliates.\n")
               .append("0. Exit: \n")
               .append("   - Exit the application.\n");

    // Mostrar el mensaje de ayuda
    JOptionPane.showMessageDialog(null, helpMessage.toString(), "Help Menu", JOptionPane.INFORMATION_MESSAGE);
}

    public List<Member> getMembers() {
        return membersList; // Devuelve la lista de miembros
    }
}

