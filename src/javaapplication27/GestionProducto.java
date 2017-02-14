/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication27;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author ALUMNEDAM
 */
public class GestionProducto extends Gestion<Producto> {

    public GestionProducto(String ruta) {
        super(ruta);
        xml = new File(ruta);
        System.out.println(xml.getName());
    }

    /**
     *
     * Este metodo empieza por crear un Document Builder y para ello creamos una
     * instancia de DocumentBuilderFactory y declararla como un constructor de
     * documentos(Document Builder)
     *
     * Creamos un doc de la clase Document para devolverlo y pasamos la
     * instancia con el parse y la ruta del fichero xml para declararla como
     * este tipo Document.
     *
     *
     *
     * @return
     */
    @Override
    public Document ActualizarDOM() {

        try {

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.normalizeDocument();
            return doc;

        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(GestionProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     *
     * Este metodo recibe por parámetro un Document ese document lo pasamos a DOM
     * para modificar el mismo documento
     * 
     * Declaramos tambien un resultado que sera el nuevo fichero xml que sera el que tenemos que devolver
     * 
     * Con el transformer podremos actualizar el contenido del primero al segundo
     *
     *
     * @param dom
     */
    @Override
    public void GuardarEstadoDom(Document dom) {

        try {

            StreamResult result = new StreamResult(xml);
            DOMSource source = new DOMSource(dom);
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(source, result);

        } catch (TransformerException ex) {
            Logger.getLogger(GestionProducto.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    
    /**
     * 
     * 
     * Creamos dos Listas, una para el nombre del elemento que vamos a meter 
     * y otra para el valor que este tendra
     * 
     * Creamos el element produ que será el generico
     * 
     * Vamos rellenando las listas con la informacion necesaria: el nombre del
     * elemento y el valor que tendra 
     * 
     * Recorremos qualquier de las dos listas ya que tendran el mismo tamaño
     * y añadimos cada uno de los nodos en el elemento producto para despues 
     * meterle los valores que queremos
     * 
     * @param productos
     * @param doc 
     */
    @Override
    public void AñadirElementoDom(Producto productos, Document doc) {

        ArrayList <Element> caracteristicas = new ArrayList <>();
        ArrayList <Text> datos = new ArrayList <>();
        int cantidad = 0;
        
        Element produ = doc.createElement("Producto");

        
        caracteristicas.add(doc.createElement("codigo"));
        datos.add(doc.createTextNode(productos.getCodi() + ""));
        
        caracteristicas.add(doc.createElement("nombre"));
        datos.add(doc.createTextNode(productos.getNom()+ ""));


        caracteristicas.add(doc.createElement("precio"));
        datos.add(doc.createTextNode(productos.getPreu()+ ""));
        
        caracteristicas.add(doc.createElement("cantidad"));
        datos.add(doc.createTextNode(productos.getUnitats()+ ""));

        
        doc.getDocumentElement().appendChild(produ);
        
        for (Element e : caracteristicas) {
            produ.appendChild(e);
            e.appendChild(datos.get(cantidad));
            cantidad++;
        }
     

    }

    
    /**
     * 
     * Creamos una lista de de elementos del elemento que queremos buscar.
     * 
     * Hacemos un bucle que pase por todos los nodos y miramos el primer item
     * que es el que corresponde con el codigo, en cuanto coincida alguno lo devolverá 
     * pasando tambien todos los items restantes
     * 
     * 
     * @param doc
     * @param codigoBuscar
     * @return 
     */
    @Override
    public Producto obtenerPorCodigo(Document doc, Producto buscar) {
        NodeList listaElementos = doc.getElementsByTagName("Producto");
        int codigoBuscar = buscar.getCodi();
        
        
        for (int i = 0; i < listaElementos.getLength(); i++) {
            NodeList nodosHijo = listaElementos.item(i).getChildNodes();

            int codigo = Integer.parseInt(nodosHijo.item(0).getTextContent());

            if (codigo ==codigoBuscar) {

                String nombre = nodosHijo.item(1).getTextContent();
                double precio = Double.parseDouble(nodosHijo.item(2).getTextContent());
                int cantidad = Integer.parseInt(nodosHijo.item(3).getTextContent());
                return new Producto(codigo, nombre, precio, cantidad);

            }
        }
        return null;
    }

    /**
     * 
     * Declaramos un ArrayList de Productos
     * 
     * I un Nodelist que contenga cada elemento que tenga producto
     * 
     * recorremos cada elemento y lo pasamos a variables para despues meterlos en
     * el ArrayList deproductos como atributos de Prodcuto
     * 
     * 
     * @param doc
     * @return 
     */
    @Override
    public ArrayList<Producto> obtenerListaElementos(Document doc) {

        ArrayList<Producto> listaProductos = new ArrayList<>();
        NodeList listaElementos = doc.getElementsByTagName("Producto");

        for (int i = 0; i < listaElementos.getLength(); i++) {
            NodeList nodosHijo = listaElementos.item(i).getChildNodes();

            int codigo = Integer.parseInt(nodosHijo.item(0).getTextContent());
            String nombre = nodosHijo.item(1).getTextContent();
            double precio = Double.parseDouble(nodosHijo.item(2).getTextContent());
            int cantidad = Integer.parseInt(nodosHijo.item(3).getTextContent());

            listaProductos.add(new Producto(codigo, nombre, precio, cantidad));
        }

        return listaProductos;
    }

    /**
     * 
     * Teniendo un documento pillamos todos los elementos de producto y los metemos
     * en un bucle para comprovar cada uno de ellos.
     * 
     * 
     * En cuando el codigo del elemento coincida con el elemento del producto
     * se modifican los nodos de nombre, precio y unidades con los que tiene
     * el producto en cuestion
     * 
     * 
     * @param doc
     * @param producto
     * @return 
     */
    @Override
    public boolean modificarElementoDom(Document doc, Producto producto) {

        NodeList listaElementos = doc.getElementsByTagName("Producto");

        for (int i = 0; i < listaElementos.getLength(); i++) {
            
            NodeList nodosHijo = listaElementos.item(i).getChildNodes();
            System.out.println(nodosHijo.item(0).getTextContent());
            int codigo = Integer.parseInt(nodosHijo.item(0).getTextContent());
            
            
            if (codigo == producto.getCodi()) {

                Element element = (Element) nodosHijo;
                element.getChildNodes().item(1).setTextContent(producto.getNom());
                element.getChildNodes().item(2).setTextContent(String.valueOf(producto.getPreu()));
                element.getChildNodes().item(3).setTextContent(String.valueOf(producto.getUnitats()));

                return true;
            }
        }
        return false;

    }

    /**
     * 
     * Como en modificar aqui miramos todos los elementos de producto y los vamos recorriendo
     * en cuanto el elemento de codigo coindicia con el cdigo del producto que hemos pasado
     * lo borramos 
     * 
     * @param doc
     * @param producto
     * @return 
     */
    @Override
    public boolean eliminarElementoDom(Document doc, Producto producto) {
        NodeList listaElementos = doc.getElementsByTagName("Producto");

        for (int i = 0; i < listaElementos.getLength(); i++) {

            if (Integer.parseInt(listaElementos.item(i).getChildNodes().item(0).getTextContent()) == producto.getCodi()) {

                listaElementos.item(i).getParentNode().removeChild(listaElementos.item(i));
                return true;
            }
        }
        return false;
    }

}
