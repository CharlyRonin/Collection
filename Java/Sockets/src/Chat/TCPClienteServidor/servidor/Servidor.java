
package Chat.TCPClienteServidor.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor extends Thread{
    
    private Socket socket;
    private ServerSocket server;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private final int puerto;
    //private static int clientCont = 1;
    private final FrmServidor frmServidor;
    private boolean isSocketON;
    private boolean isServerON;
    
    public Servidor(FrmServidor frm, int puerto) throws IOException, ConnectException {
        this.frmServidor = frm;
        this.puerto = puerto;
        initServer();
    }
    
    
    /**
     * Método que crea los flujos de entrada y salida de datos
     * @throws IOException 
     */
    private void initServer() throws IOException, ConnectException, BindException {
        System.out.println("Iniciando servidor");
        server = new ServerSocket(puerto);
        isServerON = true;
        initSocket();
    }
    
    public void initSocket() throws IOException{
        System.out.println("Esperando cliente....");
        socket = server.accept();
        
        //Cierra servidor para no permitir mas conexiones
        server.close();
        
        System.out.println("Cliente '"+socket.getRemoteSocketAddress()+"' conectado");
        
        //Entrada
        entrada = new DataInputStream( socket.getInputStream() );
        //Saluda
        salida = new DataOutputStream( socket.getOutputStream() );
        
        isSocketON = true;
        
        //Si es nuevo hilo
        if( !this.isAlive() )
            this.start();
    }
    
    
    /**
     * TODO: siempre cerrar el socket después de cada lectura y abrirlo en cada
     * nueva lectura
     */
    @Override
    public void run(){
        
        while( isServerON ){
            if( isSocketON )
                leerMsg();
            else{
                try{  
                    desconectarSocket();
                    //Inicia el servidor y un socket nuevo
                    initServer();
                }catch(IOException e){ e.printStackTrace(); }
            }
        }
    }
    
    
    
    
    private void stopSocket(){
        //Detener el ciclo de Run
        this.isSocketON = false;
    }
    
    public void stopServer(){
        stopSocket();
        this.isServerON = false;
    }
    
    
    /**
     * Metodo que envia un mensaje. Si este ocasiona una Exception significa
     * que la conexion se cerro y se detiene cierra el socket
     * @param msg mensaje a enviar
     */
    public void enviarMsg(String msg){
        try {
            salida.writeUTF(msg);
        } catch (IOException e) { 
            stopSocket();
            //desconectarSocket();
        } 
    }
    
    
    /**
     * Metodo que lee un mensaje. Si este ocasiona una Exception significa
     * que la conexion se cerro y se detiene cierra el socket
     */
    public void leerMsg(){
        try {
            String texto = entrada.readUTF();
            this.frmServidor.receiveMsg(texto);
        } catch (IOException e) { 
            stopSocket();
        } 
    }
    
    
    private void desconectarServer() throws IOException{
        server.close();
    }
    
    
    public void detenerServidor(){
        try {
            desconectarSocket();
            desconectarServer();
            stopServer();
            System.out.println("#Servidor detenido");
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    private void desconectarSocket(){
        stopSocket();
        try {
            entrada.close();
            salida.close();
            socket.close();
            System.out.println("#Cliente desconectado");    
        } catch (IOException e) { e.printStackTrace(); }
    }
    

    
    
}

    
