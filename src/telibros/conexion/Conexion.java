package telibros.conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author igor
 */
public class Conexion {
    private final String RUTA_DE_ARCHIVO_DE_AJUSTES = "config/config.txt";
    private final String PROTOCOLO = "jdbc:mysql://";
    private String server, user, pass, database;
    private final Connection con;
    public Conexion() throws IOException, SQLException{
        leerArchivoAjustes(RUTA_DE_ARCHIVO_DE_AJUSTES);
        String url = PROTOCOLO + server + "/" + database + "?user=" + user +
            "&password=" + pass;
        System.out.println("URL = " + url);
        con = DriverManager.getConnection(url);
    }
    public Connection getConection(){
        return con;
    }
    
    private enum LoadState{
        SERVER,
        USER,
        PASS,
        DATABASE,
        STOP
    }
    private void leerArchivoAjustes(String ruta) throws 
            UnsupportedEncodingException, IOException {
        InputStream is = this.getClass().getResourceAsStream(ruta);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String str;
        LoadState ls;
        while((str = br.readLine()) != null){
            if(str.equalsIgnoreCase("[Server]")){
                ls = LoadState.SERVER;
            } else if(str.equalsIgnoreCase("[User]")){
                ls = LoadState.USER;
            } else if(str.equalsIgnoreCase("[Password]")){
                ls = LoadState.PASS;
            } else if(str.equalsIgnoreCase("[Database]")){
                ls = LoadState.DATABASE;
            } else {
                ls = LoadState.STOP;
            }
            str = br.readLine();
            switch(ls){
                case SERVER :
                    server = str;
                    break;
                case USER :
                    user = str;
                    break;
                case PASS :
                    pass = str;
                    break;
                case DATABASE :
                    database = str;
                    break;
                case STOP :
                    break;
            }
            if(ls == LoadState.STOP){
                break;
            }
        }
    }
}
