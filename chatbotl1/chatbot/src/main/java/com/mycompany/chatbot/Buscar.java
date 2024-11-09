package com.mycompany.chatbot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import org.json.JSONObject;
<<<<<<< HEAD
    
/**
 *
 * @author Kiara García
 */
=======



>>>>>>> 01062697b11eff4b689943b1338667c835cf33fc
public class Buscar extends javax.swing.JFrame {
    DefaultListModel<String>modeloHistorial;
    String [][]chats; 
    int chatcount;
    int maxchats= 50;
    int maxpalabras= 1000;
   StringBuilder chatactual;  
    int indice;
   private void enviarPreguntaAlChatbot(String pregunta) {
    HttpURLConnection conn = null; // Declaramos la variable fuera del bloque try
    try {
        String modelName = "gemma2:2b";
        String jsonInputString = String.format("{\"model\":\"%s\", \"prompt\":\"%s\",\"stream\": false}", modelName, pregunta);
        System.out.println("JSON Enviado: " + jsonInputString);

        URL url = new URL("http://localhost:11434/api/generate");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        // Enviar la solicitud
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
        InputStream stream = (responseCode >= 400) ? conn.getErrorStream() : conn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        
        StringBuilder response = new StringBuilder();
        String line;
        
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        
        in.close();
        
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JSONObject jsonResponse = new JSONObject(response.toString());
            String responseText = jsonResponse.getString("response");
            txtchat.append("Bot: " + responseText + "\n");
            if(chatactual.length() > 0) {
                chatactual.append(" | ");
            }
            chatactual.append("Tu: ").append(pregunta).append(" | Bot: ").append(responseText);
        } else {
            manejarErrores(responseCode, response.toString());
        }
    } catch (Exception e) {
        manejarErrores(e);
    } finally {
        // Asegúrate de cerrar la conexión explícitamente en el bloque finally
        if (conn != null) {
            conn.disconnect(); // Esto es lo que asegura que se liberen los recursos de la conexión
        }
    }
    private void manejarErrores(int responseCode, String responseBody) {
        switch (responseCode) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
                txtchat.append("Error 400: Solicitud incorrecta. Verifica tu pregunta.\n");
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                txtchat.append("Error 401: No autorizado. Verifica tus credenciales.\n");
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                txtchat.append("Error 404: Recurso no encontrado.\n");
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                txtchat.append("Error 500: Error interno del servidor.\n");
                break;
                default:
                    txtchat.append("Error desconocido: " + responseCode + "\n" + responseBody + "\n");
                    break;
            }
        }
}   private void manejarErrores(Exception e) {
    if (e instanceof java.net.SocketTimeoutException) {
        txtchat.append("Error: Tiempo de espera excedido. Intenta nuevamente.\n");
        System.out.println("Excepción capturada: Tiempo de espera excedido.");
    } else if (e instanceof java.net.ConnectException) {
        txtchat.append("Error: No se pudo conectar al servidor. Verifica tu conexión a Internet.\n");
        System.out.println("Excepción capturada: No se pudo conectar al servidor.");
    } else {
        e.printStackTrace();
        txtchat.append("Error inesperado: " + e.getMessage() + "\n");
        System.out.println("Excepción capturada: " + e.getMessage());
    }}


        
         

    /**
     * Creates new form Buscar
     */
    public Buscar() {
        
        initComponents();
        modeloHistorial = new DefaultListModel<>(); 
        lsthistorial.setModel(modeloHistorial);
        chats = new String[maxchats][1];
        chatcount=0;
        chatactual = new StringBuilder();  
        
        
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lsthistorial = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtchat = new javax.swing.JTextArea();
        txtpregunta = new javax.swing.JTextField();
        btnenviar = new javax.swing.JButton();
        btnnuevochat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cognitia AI");

        lsthistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lsthistorialMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lsthistorial);

        txtchat.setColumns(20);
        txtchat.setRows(5);
        jScrollPane2.setViewportView(txtchat);

        btnenviar.setText("Enviar");
        btnenviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnenviarMouseClicked(evt);
            }
        });

        btnnuevochat.setText("Nuevo chat");
        btnnuevochat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnnuevochatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnnuevochat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtpregunta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnenviar))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnnuevochat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtpregunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnenviar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
                .addGap(46, 46, 46))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnenviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnenviarMouseClicked
        String pregunta = txtpregunta.getText(); 
            
        
        
            if (pregunta.isEmpty()) {
                
               return;
               
            } 

                txtchat.append("Tu: " + pregunta + "\n");

            enviarPreguntaAlChatbot(pregunta);
      
        
  
           txtpregunta.setText("");
        
        

    }//GEN-LAST:event_btnenviarMouseClicked

    private void lsthistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lsthistorialMouseClicked
            int indice = lsthistorial.getSelectedIndex();
            System.out.println("indice seleccionado" + indice);
            if(indice >=0 && indice<chatcount){
                txtchat.setText(chats[indice][0]);
                
                System.out.println("Mensaje mostrado: " + chats[indice][0]);
                }
            
       
        
    }//GEN-LAST:event_lsthistorialMouseClicked

    private void btnnuevochatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnnuevochatMouseClicked
      
    if(chatactual.length() > 0 && chatcount < maxchats) {
    chats[chatcount][0] = chatactual.toString(); 
    modeloHistorial.addElement("Chat " + (chatcount + 1)); 
    System.out.println("chat guardado: " + chats[chatcount][0]);
    chatcount++;
}

     txtchat.setText("");
     txtpregunta.setText("");
     chatactual.setLength(0);
        System.out.println("nuevo chat");
       
    }//GEN-LAST:event_btnnuevochatMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Buscar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnenviar;
    private javax.swing.JButton btnnuevochat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lsthistorial;
    private javax.swing.JTextArea txtchat;
    private javax.swing.JTextField txtpregunta;
    // End of variables declaration//GEN-END:variables
}
