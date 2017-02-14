/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication27;

import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 *
 * @author ALUMNEDAM
 */
public abstract class Gestion<T> {
    
    protected File xml;

    public Gestion(String ruta) {
        this.xml = new File(ruta);
    }
    
    public abstract Document ActualizarDOM();
    
    public abstract void GuardarEstadoDom(Document dom);
    
    public abstract void AñadirElementoDom (T t, Document dom);
    
    public abstract T obtenerPorCodigo(Document doc, Producto buscar);
    
    public abstract ArrayList<T> obtenerListaElementos(Document doc);
    
    public abstract boolean modificarElementoDom (Document doc, Producto producto);
    
    public abstract boolean eliminarElementoDom (Document doc, Producto producto);
    
}
