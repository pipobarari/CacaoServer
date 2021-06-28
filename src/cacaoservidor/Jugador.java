/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacaoservidor;

import java.net.Socket;

/**
 *
 * @author Christopher
 */
public class Jugador {
    
    Thread hilo;
    Socket so;

    public Jugador(Thread hilo, Socket so) {
        this.hilo = hilo;
        this.so = so;
    }

    public Thread getHilo() {
        return hilo;
    }

    public void setHilo(Thread hilo) {
        this.hilo = hilo;
    }

    public Socket getSo() {
        return so;
    }

    public void setSo(Socket so) {
        this.so = so;
    }
    
    
    
}
