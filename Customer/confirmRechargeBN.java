import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.text.SimpleDateFormat;  
import java.util.Date; 

public class confirmRechargeBN extends JFrame {

    public String fname, lname, mobile, email, dob, gender, nationality, password, recMobile;
    double newBalance, recBalance, newRecBalance, amount;
    PreparedStatement ps, ps1, ps2, ps3, ps4;
    ResultSet rs, rs1, rs2, rs4;

    JLabel recipientLabel, amountLabel, newBalanceLabel;
    JTextField recipientText, amountText, newBalanceText;
    JButton backButton;


    JButton confirmButton;

    public confirmRechargeBN (String mobile, double newBalance, String recMobile, double amount) {
        this.mobile = mobile;
        this.newBalance = newBalance;
        this.recMobile = recMobile;
        this.amount = amount;

        //database connection
        /*String query3 = "SELECT * FROM account WHERE MOBILE = '" + mobile + "'" ;
        try {
            ps4 = database().prepareStatement(query3);
            rs4 = ps4.executeQuery();
            if (rs4.next()) {
                recBalance = rs4.getDouble(2);         
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }*/



        backButton = new JButton();
        backButton.setBounds(14,20, 30, 30);
        backButton.setBackground(new Color(239, 250, 254));
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setFocusPainted(false);
        ImageIcon backIcon = new ImageIcon("back.png");
        Image backImg = backIcon.getImage();
        Image backImgScale = backImg.getScaledInstance(backButton.getWidth(), backButton.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon backScaled = new ImageIcon(backImgScale);
        backButton.setIcon(backScaled);
        add(backButton);

        
        recipientLabel = new JLabel("প্রাপক");
        recipientLabel.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 20));
        recipientLabel.setBounds(75, 70, 100, 25);
        recipientLabel.setForeground(new Color(0, 59, 79));
        add(recipientLabel);

        recipientText = new JTextField();
        recipientText.setBounds(75, 100, 240, 30);
        recipientText.setText(recMobile);
        recipientText.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 20));
        recipientText.setBackground(Color.white);
        recipientText.setForeground(new Color(0, 59, 79));
        recipientText.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 59, 79)));
        recipientText.setEditable(false);
        add(recipientText);

        amountLabel = new JLabel("পরিমাণ");
        amountLabel.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 20));
        amountLabel.setBounds(75, 145, 100, 20);
        amountLabel.setForeground(new Color(0, 59, 79));
        add(amountLabel);

        amountText = new JTextField();
        amountText.setBounds(75, 175, 240, 30);
        amountText.setText(Double.toString(amount) + " টাকা");
        amountText.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 20));
        amountText.setBackground(Color.white);
        amountText.setForeground(new Color(0, 59, 79));
        amountText.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 59, 79)));
        amountText.setEditable(false);
        add(amountText);

        newBalanceLabel = new JLabel("নতুন পরিমাণ");
        newBalanceLabel.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 20));
        newBalanceLabel.setBounds(75, 220, 150, 20);
        newBalanceLabel.setForeground(new Color(0, 59, 79));
        add(newBalanceLabel);
        newBalanceText = new JTextField();
        newBalanceText.setBounds(75, 250, 240, 30);
        newBalanceText.setText(Double.toString(newBalance) + " টাকা");
        newBalanceText.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 20));
        newBalanceText.setBackground(Color.white);
        newBalanceText.setForeground(new Color(0, 59, 79));
        newBalanceText.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 59, 79)));
        newBalanceText.setEditable(false);
        add(newBalanceText);


        confirmButton = new JButton();
        confirmButton.setBackground(new Color(0, 59, 79));
        confirmButton.setForeground(Color.white);
        confirmButton.setText("নিশ্চিত করুন");
        confirmButton.setFont(new Font("Potro Sans Bangla", Font.PLAIN, 25));
        confirmButton.setFocusPainted(false);
        confirmButton.setBounds(115, 310, 160, 50);
        add(confirmButton);
        confirmButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
             confirmButton.setBackground(new Color(0, 87, 117));
            }
            @Override
            public void mouseExited(MouseEvent e) {
             confirmButton.setBackground(new Color(0, 59, 79));
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


        confirmButton.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                newRecBalance = recBalance + amount;
                String query1 = "UPDATE account SET BALANCE = '" + newBalance + "' WHERE MOBILE = '" + mobile + "'" ;
                String query3 = "INSERT INTO " + mobile + "trans(OUTGOING, DATE, AMOUNT) VALUES ('OUT(Recharge)', '" + getDateTime() + "','"+ amount + "')";

                try {
                    ps1 = database().prepareStatement(query1);
                    ps3 = database().prepareStatement(query3);
                    ps1.executeUpdate();
                    ps3.executeUpdate();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                HomePageBN home = new HomePageBN(mobile);
                dispose();
                home.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                RechargeBN recharge = new RechargeBN(mobile);
                recharge.setVisible(true);
                dispose();
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


        setTitle("Confirm Transaction"); //title
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(239, 250, 254));
        setLayout(null);
        setSize(400,500); //sets the dimension
        setVisible(true); //makes frame visible
        setResizable(false); //diasables resizing
        setLocationRelativeTo(null);
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
    public static String getDateTime() {  
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");  
        Date date = new Date();  
        String date_time = formatter.format(date); 
        return  date_time;
    }
}
