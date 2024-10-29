/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.socialclubmanager;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class SocialClubManager {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ClubSocial clubsocial = new ClubSocial();
            Member member = new Member();

            int option;

            do {
                // Menú de opciones utilizando JOptionPane
                String menu = """
                        =============================
                        MENU SOCIAL CLUB YEYE AND LADY
                        =============================
                        1. REGISTER MEMBER
                        2. REMOVE MEMBER OR AFFILIATE
                        3. CHECK INFO MEMBER
                        4. REGISTER COST
                        5. PAY INVOICES
                        6. LIST INVOICES
                        7. EDIT MEMBER INFORMATION
                        8. SEE DOCUMENTS
                        9. HELP
                        0. EXIT...
                        Please select an option:""";

                String input = JOptionPane.showInputDialog(menu);

                if (input == null) {
                    option = 0;
                } else {
                    try {
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid option. Please enter a number.");
                        option = -1;
                    }
                }

                switch (option) {
                    case 1 -> 
                        clubsocial.registerMember();
                    case 2 ->
                        clubsocial.removeMemberOrAffiliate();
                    case 3 ->
                        clubsocial.findMemberById();
                    case 4 ->
                        clubsocial.createInvoiceForMemberOrAffiliate(sc);
                    case 5 ->
                        clubsocial.payMemberInvoice();
                    case 6 ->
                        clubsocial.listMemberInvoices();
                    case 7 ->
                        clubsocial.EditInformation();
                    case 8 ->
                        clubsocial.showDocumentInfo();   
                    case 9 ->
                        clubsocial.showHelp(); 
                    case 0 ->
                        clubsocial.exit();
                    default ->
                        JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
                }
            } while (option != 0);
        }
    }
}






