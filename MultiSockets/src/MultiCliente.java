
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiCliente extends javax.swing.JFrame {

    static InetAddress addr, addr2;
    static DatagramSocket ds, ds2;
    static DatagramPacket pkt, pkt2, msgenvia, msgenvia2, botão;
    static MulticastSocket mcs, mcs2;

    String resposta, contadorString, data, texto, concatenado;
    int contador = 0, validadon = 0, validadoi = 0;

    public MultiCliente() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(jTextField1);
        jTextField1.setBounds(90, 90, 140, 30);
        getContentPane().add(jTextField2);
        jTextField2.setBounds(90, 140, 140, 30);

        jLabel1.setText("Nome:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 90, 60, 30);

        jLabel2.setText("Idade:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 140, 60, 30);

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(100, 270, 120, 50);

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(153, 153, 0));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(270, 70, 250, 290);

        jLabel3.setText("Resposta do Servidor:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(330, 40, 140, 30);

        jLabel4.setText("Cadastro:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(140, 40, 100, 30);

        setSize(new java.awt.Dimension(569, 439));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Thread() {
            @Override
            public void run() {
                try {
                    if (!jTextField1.getText().equals("") && !jTextField2.getText().equals("")) {
                        concatenado = jTextField1.getText() + "/" + jTextField2.getText() + "/";
                        texto = jTextArea1.getText();
                        jTextArea1.setText(texto + "\n\nVALORES DE ENVIO\n\nEnviando valores: " +concatenado);
                        addr = InetAddress.getByName("239.0.0.1");
                        ds = new DatagramSocket();
                        mcs = new MulticastSocket(12347);
                        mcs.joinGroup(addr);
                        byte[] b = concatenado.getBytes();
                        pkt = new DatagramPacket(b, b.length, addr, 12344);
                        ds.send(pkt);
                    } else {
                        texto = jTextArea1.getText();
                        jTextArea1.setText(texto + "\nCOMPLETE OS CAMPOS");
                    }
                } catch (IOException | NumberFormatException e) {
                }
            }
        }.start();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // Reecebendo
                        mcs2 = new MulticastSocket(12349);
                        addr2 = InetAddress.getByName("239.0.0.1");
                        mcs2.joinGroup(addr2);
                        byte rec[] = new byte[256];
                        pkt2 = new DatagramPacket(rec, rec.length);
                        mcs2.receive(pkt2);
                        ds2 = new DatagramSocket();
                        data = new String(pkt2.getData());
                        texto = jTextArea1.getText();
                        jTextArea1.setText(texto +"\n\nVALORES DE CHEGADA\n\n"+ data);
                        mcs2.close();
                    } catch (IOException | NumberFormatException e) {
                    }
                }
            }
        }.start();
    }//GEN-LAST:event_formWindowOpened
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new MultiCliente().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
