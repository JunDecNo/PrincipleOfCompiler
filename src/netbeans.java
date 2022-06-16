//import java.awt.*;
//
//public class netbeans extends javax.swing.JFrame {
//    public netbeans() {
//        initComponents();
//    }
//    @SuppressWarnings("unchecked")
//    private void initComponents() {
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        String forName[] = ge.getAvailableFontFamilyNames();
//        jLabel1 = new javax.swing.JLabel();
//        jTextField1 = new javax.swing.JTextField();
//        jLabel2 = new javax.swing.JLabel();
//        jComboBox1 = new javax.swing.JComboBox<>();
//        jLabel3 = new javax.swing.JLabel();
//        jButton1 = new javax.swing.JButton();
//        jLabel4 = new javax.swing.JLabel();
//        jButton2 = new javax.swing.JButton();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//
//        jLabel1.setText("选择字体大小");
//
//        jTextField1.setText("12");
//        jTextField1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField1ActionPerformed(evt);
//            }
//        });
//
//        jLabel2.setText("选择字体样式");
//
//        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(forName));
//        jComboBox1.setEditable(false);
//        jComboBox1.setSelectedItem(source);
//
//        jLabel3.setText("选择字体颜色");
//
//        jButton1.setText("选择");
//        jButton1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton1ActionPerformed(evt);
//            }
//        });
//
//        jLabel4.setText("示例文本 Sample text");
//        jButton2.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 18)); // NOI18N
//        jButton2.setText("确认");
//        jButton2.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton2ActionPerformed(evt);
//            }
//        });
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addGap(21, 21, 21)
//                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                        .addGroup(layout.createSequentialGroup()
//                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                                .addGap(0, 0, Short.MAX_VALUE)))
//                                .addContainerGap())
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addGap(23, 23, 23)
//                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                                        .addGroup(layout.createSequentialGroup()
//                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                                        .addComponent(jLabel1)
//                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                                        .addComponent(jLabel2)
//                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                                        .addComponent(jLabel3)
//                                                        .addComponent(jButton1)))
//                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
//                                .addContainerGap())
//        );
//
//        pack();
//    }// </editor-fold>
//
//    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//    }
//
//    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//    }
//
//    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//    }
//    public static void main(String args[]) {
//                new netbeans().setVisible(true);
//    }
//
//    // Variables declaration - do not modify
//    private javax.swing.JButton jButton1;
//    private javax.swing.JButton jButton2;
//    private javax.swing.JComboBox<String> jComboBox1;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
//    private javax.swing.JLabel jLabel4;
//    private javax.swing.JTextField jTextField1;
//    // End of variables declaration
//}
