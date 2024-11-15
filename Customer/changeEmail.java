import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
//import java.io.File;


public class changeEmail extends JFrame { 
    String fname, lname, mobile, email, dob, gender, nationality, password;
    public JPanel panel;
    public JLabel label;
    public JTextField label1Text, label2Text, label3Text, label6Text, label8Text;
    public JPasswordField label7Pass;
    public JRadioButton maleButton, femaleButton, othersButton;
    public JComboBox <String> date, month, year;
    public JButton submitButton, backButton;
    PreparedStatement ps, ps1;
    ResultSet rs, rs1;

    
    public changeEmail(String mobile) {

        this.mobile = mobile;

        panel = new JPanel();
        panel.setBounds(55, 0, 400, 250);
        panel.setLayout(null);
        panel.setOpaque(false);


        //String query = "SELECT * FROM user_info WHERE MOBILE = '" + mobile + "'" ;
        String query = "SELECT * FROM user_info WHERE MOBILE = '" + mobile + "'" ;
        try {
            ps = database().prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString(3);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        //email
        label3Text = new RoundJTextField(20);
        label3Text.setBounds(0, 70, 280, 30);
        label3Text.setFont(new Font("Potro Sans Bangla", Font. PLAIN, 17));
        label3Text.setForeground(new Color(0, 59, 79));
        label3Text.setCaretColor(new Color(0, 59, 79));
        label3Text.setText(email);
        panel.add(label3Text);


        //signup button
        submitButton = new RoundJButton(50);
        submitButton.setText("Submit");
        submitButton.setBounds(0, 120, 280, 35);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 22));
        submitButton.setContentAreaFilled(true);
        submitButton.setBackground(new Color(0, 59, 79));
        submitButton.setForeground(Color.white);
        panel.add(submitButton); 
        submitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
             submitButton.setBackground(new Color(0, 87, 117));
            }
            @Override
            public void mouseExited(MouseEvent e) {
             submitButton.setBackground(new Color(0, 59, 79));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
        });   

        //back to logIn
        backButton = new JButton("Back to Profile");
        backButton.setBackground(new Color(238, 238, 238));
        backButton.setBounds(0, 20, 100, 25);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 15));
        backButton.setForeground(new Color(0, 59, 79));
        backButton.setBackground(new Color(239, 250, 254));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(backButton);
        backButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
             backButton.setForeground(new Color(0, 87, 117));
            }
            @Override
            public void mouseExited(MouseEvent e) {
             backButton.setForeground(new Color(0, 59, 79));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
        });
        

        //submitButton action
        submitButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) { 
            String error = errorMsg(label3Text.getText());
            if (error != null) {
                JOptionPane.showOptionDialog(null, error, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null);
            }
            else {
                String query1 = "UPDATE user_info SET EMAIL = '" + label3Text.getText() + "' WHERE MOBILE = '" + mobile + "'" ;
                try {
                    ps1 = database().prepareStatement(query1);
                    ps1.executeUpdate();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            }  
        });

        //backButton action
        backButton.addActionListener(new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                dispose();
                //setVisible(false);
                ProfilePage profile =  new ProfilePage(mobile);
                profile.setVisible(true);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
                else if (result == JOptionPane.YES_OPTION){
                    File folder = new File("D:\\Java Projects\\GUI Project\\Experiment\\Customer");
					File fList[] = folder.listFiles();

					for (File f : fList) {
						if (f.getName().endsWith(".class")) {
							f.delete();
                        } 
					}
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });



        setTitle("Edit Profile"); //title
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(239, 250, 254));
        setLayout(null);
        setSize(400,250); //sets the dimension
        setVisible(true); //makes frame visible
        setResizable(false); //diasables resizing
        setLocationRelativeTo(null);
        add(panel);
    }

    public static Connection database() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "root");
            return connection;            
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String errorMsg(String email) {
        if (email.isBlank() || email.equals("Email")) {
            return "Email cannot be empty!";
        }
        else if (!isValidEmailAddress(email)) {
            return "Invalid Email!";
        }
        return null;
    }
}
