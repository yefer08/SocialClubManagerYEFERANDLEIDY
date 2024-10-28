/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socialclubmanager;

/**
 *
 * @author casa
 */
public class Affiliates {
    private String name;

    public Affiliates(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


    
    
    // Método para listar y agregar afiliados a un miembro
 /*public void listOfPeople(Scanner sc, Member member) {
    System.out.println("Enter the number of affiliates:");
    int numAffiliates = sc.nextInt();
    sc.nextLine(); // Limpiar el buffer

    for (int i = 0; i < numAffiliates; i++) {
        System.out.println("Enter the name of affiliate #" + (i + 1) + ":");
        member.addAffiliate(String affiliateName); // Llamar al método addAffiliate para agregar el afiliado
    }
  }*/
   

