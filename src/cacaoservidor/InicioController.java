/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacaoservidor;

import com.jfoenix.controls.JFXButton;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christopher
 */
public class InicioController implements Initializable {

    @FXML
    private JFXButton btnConectar;
    
    ServerSocket servidor = null;
    Socket sc = null;
    DataInputStream in;
    DataOutputStream out;   
    ArrayList<String> jugadores = new ArrayList<>();
    ArrayList<Socket> socketJugadores = new ArrayList<>();
    int contJugadores = 0;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionbtnconectar(ActionEvent event) {

        //puerto de nuestro servidor
        final int PUERTO = 5000;

        try {
            //Creamos el socket del servidor
            servidor = new ServerSocket(PUERTO);
            System.out.println("SERVIDOR INICIADO");

            
            
            //Siempre estara escuchando peticiones
            while (contJugadores < 4) {

                //Espero a que un cliente se conecte
                sc = new Socket();
                sc = servidor.accept();
                
                InetSocketAddress direccionSocket = (InetSocketAddress)sc.getRemoteSocketAddress();  //obtener direcciones IP de los clientes
                InetAddress dirIP = direccionSocket.getAddress();
                Inet4Address dirIP4 = (Inet4Address)dirIP;
                byte[] dirIP4bytes = dirIP4.getAddress(); 
                String dirIP4string = dirIP4.toString();  
                System.out.println(dirIP4string);
                
                socketJugadores.add(sc);

                System.out.println("CLIENTE CONECTADO");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                
                //Le envio un mensaje
                out.writeUTF("ESPERANDO JUGADORES...");
                
                //Leo el mensaje que me envia
                String mensaje = in.readUTF();
                jugadores.add(mensaje);
                contJugadores++;
                
                if (contJugadores == 2 || contJugadores == 3){        //2-3 jugadores
                    for (Socket so : socketJugadores){
                        try {
                            in = new DataInputStream(so.getInputStream());
                            out = new DataOutputStream(so.getOutputStream());
                            out.writeUTF("CONECTADO");
                        } catch (IOException ex) {
                            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
                else if (contJugadores == 4){   //4 jugadores

                    for (Socket so : socketJugadores){
                        try {
                            in = new DataInputStream(so.getInputStream());
                            out = new DataOutputStream(so.getOutputStream());
                            out.writeUTF("CONECTADO");
                        } catch (IOException ex) {
                            Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
                System.out.println(jugadores);
            }
            //Cierro el socket
            /*sc.close();
            System.out.println("Cliente desconectado");*/
        } catch (IOException ex) {
            Logger.getLogger(CacaoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
