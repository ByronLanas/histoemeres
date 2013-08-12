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
public class Aporte implements Serializable {

    public static String municipio;
    public static float valor;
    public static Date fecha;

    public Aporte() {
    }

    public Aporte(String municipio, float valor, Date fecha) {
        this.fecha = fecha;
        this.municipio = municipio;
        this.valor = valor;
    }
}
