
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiServer extends javax.swing.JFrame {

    static InetAddress addr, addr2;
    static DatagramSocket ds, ds2;
    static DatagramPacket pkt, pkt2, msgenvia, msgenvia2;
    static MulticastSocket mcs, mcs2;

    String data, contadorString, texto, valorenvio, antibugnome;
    int valortotal = 0, valorparcial = 0, valormaior = 0, valorparacalculo = 0, numeroantibug = 0, duaspool = 0, antibugidade = 0;

    public MultiServer() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Bodoni Bk BT", 0, 18)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(0, 255, 0));
        jTextArea1.setRows(5);
        jTextArea1.setText("Aguardando cliente se conectar...\n");
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 500, 400);

        setSize(new java.awt.Dimension(515, 440));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        numeroantibug = 1;
                        // Recebendo
                        mcs = new MulticastSocket(12344);
                        addr = InetAddress.getByName("239.0.0.1");

                        mcs.joinGroup(addr);
                        byte rec[] = new byte[256];
                        pkt = new DatagramPacket(rec, rec.length);
                        mcs.receive(pkt);
                        ds = new DatagramSocket();
                        data = new String(pkt.getData());
                        data = data + "/";
                        System.out.println(data);
                        String msg[] = data.split("/");
                        antibugnome = msg[0];
                        antibugidade = Integer.parseInt(msg[1]);
                        System.out.println(antibugnome);
                        System.out.println(antibugidade);

                        if (antibugnome.equals("Maria") && antibugidade >= 18) {
                            duaspool = 1;
                        }
                        if (!antibugnome.equals("Maria") && antibugidade >= 18) {
                            duaspool = 2;
                        }
                        if (!antibugnome.equals("Maria") && antibugidade < 18) {
                            duaspool = 3;
                        }
                        System.out.println(duaspool);
                        texto = jTextArea1.getText();
                        jTextArea1.setText(texto + "\n\nVALORES DE CHEGADA\n\nRecebendo valores: " + antibugnome + "\n" + antibugidade);

                        reenviando();

                    } catch (IOException | NumberFormatException e) {
                    }
                }
            }
        }.start();
    }//GEN-LAST:event_formWindowOpened
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new MultiServer().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
public void reenviando() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (numeroantibug == 1) {

                            // Reenviando
                            addr2 = InetAddress.getByName("239.0.0.1");
                            ds2 = new DatagramSocket();
                            mcs2 = new MulticastSocket(12348);
                            mcs2.joinGroup(addr2);
                            if (duaspool == 1) {
                                valorenvio = "OLA MARIA, tudo bem?";
                            } else if (duaspool == 2) {
                                valorenvio = "OLA ESTRANHA.";
                            } else if (duaspool == 3) {
                                valorenvio = "Olá jovem, obrigado por usar o computador.";
                            }
                            
                            numeroantibug = 0;
                            
                            texto = jTextArea1.getText();
                            jTextArea1.setText(texto + "\n\nVALORES DE ENVIO\n\nEnviando dados: " + valorenvio);
                            byte[] b = valorenvio.getBytes();
                            pkt2 = new DatagramPacket(b, b.length, addr2, 12349);
                            ds2.send(pkt2);
                            mcs2.close();
                            
                            duaspool = 0;

                            texto = jTextArea1.getText();
                            jTextArea1.setText(texto + "\nAguardando cliente se conectar...");
                        }
                    } catch (IOException | NumberFormatException e) {
                    }
                }
            }
        }.start();
    }

}
