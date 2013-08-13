/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.Serializable;

/**
 *
 * @author Battousai
 */
public class Usuario implements Serializable{
    public static int codigo;
    public static String nombre;
    public static String tipo;
    public static String contraseña;
    
    public Usuario(){
        
    }
    public Usuario(int codigo, String nombre, String tipo, String contraseña){
        this.codigo=codigo;
        this.nombre=nombre;
        this.tipo=tipo;
        this.contraseña=contraseña;
    }
}
