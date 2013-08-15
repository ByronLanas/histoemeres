/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Battousai
 */
@Named(value = "Herramientas")
@RequestScoped
public class Herramientas {

    SimpleDateFormat format;
    public Herramientas() {
    }
    public String fechaConDia(){
        Date fecha=new Date();
        format=new SimpleDateFormat("yyyy-MM-dd") ;
        return format.format(fecha);
    }
    public String fechaSinDia(Date fecha){
        format=new SimpleDateFormat("yyyy-MM") ;
        return format.format(fecha);
    }
    
    private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
    public static String encriptaEnMD5(String stringAEncriptar)
    {
        try
        {
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           for (int i = 0; i < bytes.length; i++)
           {
               int bajo = (int)(bytes[i] & 0x0f);
               int alto = (int)((bytes[i] & 0xf0) >> 4);
               strbCadenaMD5.append(CONSTS_HEX[alto]);
               strbCadenaMD5.append(CONSTS_HEX[bajo]);
           }
           return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
           return null;
        }
    }
 
}
