/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Battousai
 */
public class Venta implements Serializable{
    int codigoVenta;
    int rutCliente;
    int codigoProducto;
    int cantidad;
    Date fecha;
}
