/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication27;

/**
 *
 * @author ALUMNEDAM
 */
public class Producto {
    
    int codi;
    String nom;
    double preu;
    int unitats;

    public Producto(int codi, String nom, double preu, int unitats) {
        this.codi = codi;
        this.nom = nom;
        this.preu = preu;
        this.unitats = unitats;
        
        
    }

    public int getCodi() {
        return codi;
    }

    public String getNom() {
        return nom;
    }

    public double getPreu() {
        return preu;
    }

    public int getUnitats() {
        return unitats;
    }

    @Override
    public String toString() {
        return "Producto{" + "codi=" + codi + ", nom=" + nom + ", preu=" + preu + ", unitats=" + unitats + '}';
    }
    
    
    
}
