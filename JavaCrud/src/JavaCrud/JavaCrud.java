package JavaCrud;
import java.awt.EventQueue;

import java.sql.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtbedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	PreparedStatement pat;
	ResultSet ra;
	
	public void Connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud", "root","");
		}
		catch (ClassNotFoundException ce)
		{
			
		}
		catch(SQLException se)
		{
			
		}
	}
	
	public void table_load()
	{
		try {
			pat = con.prepareStatement("select * from book");
			ra = pat.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(ra));
		}
		
		catch(SQLException se)
		{
			se.printStackTrace();
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 994, 621);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(396, 27, 192, 52);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 90, 394, 295);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 64, 114, 49);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(10, 141, 125, 41);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(10, 203, 125, 59);
		panel.add(lblNewLabel_1_1_1);
		
		txtbname = 
				new JTextField();
		txtbname.setBounds(139, 76, 245, 26);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtbedition = new JTextField();
		txtbedition.setColumns(10);
		txtbedition.setBounds(139, 152, 245, 26);
		panel.add(txtbedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(139, 226, 245, 26);
		panel.add(txtprice);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(505, 90, 418, 378);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBounds(10, 419, 114, 49);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(151, 419, 114, 49);
		frame.getContentPane().add(btnExit);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(290, 419, 114, 49);
		frame.getContentPane().add(btnClear);
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 499, 403, 74);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Book ID");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_1_1_2.setBounds(10, 11, 97, 41);
		panel_1.add(lblNewLabel_1_1_2);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					String id = txtbid.getText();
					pat = con.prepareStatement("select name, edition, price from book where id = ?");
					pat.setString(1, id);
					ResultSet rs = pat.executeQuery();
					
					if( rs.next() == true)
					{
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtbname.setText(name);
						txtbedition.setText(edition);
						txtprice.setText(price);
					}
					
					else
					{
						txtbname.setText("");
						txtbedition.setText("");
						txtprice.setText("");
						
					}
				}
				catch(SQLException se)
				{
					se.printStackTrace();
				}
				
				
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(139, 11, 254, 41);
		panel_1.add(txtbid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				

				String name, edition, price, id;
				name = txtbname.getText();
				edition = txtbedition.getText();
				price = txtprice.getText();
				id = txtbid.getText();
				
				try {
					String query = "update book set name = ?, edition = ? , price = ? where id = ?";
					pat = con.prepareStatement(query);
					pat.setString(1, name);
					pat.setString(2, edition);
					pat.setString(3, price);
					pat.setString(4, id);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!");
					table_load();
					txtbname.setText("");
					txtbedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}
				catch(SQLException se)
				{
					se.printStackTrace();
				}
				
				
				
				
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUpdate.setBounds(564, 495, 114, 49);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				String  id;
				id = txtbid.getText();
				
				try {
					String query = "delete from book where id = ?";
					pat = con.prepareStatement(query);
					pat.setString(1, id);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					table_load();
					txtbname.setText("");
					txtbedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}
				catch(SQLException se)
				{
					se.printStackTrace();
				}
				
				
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.setBounds(747, 495, 114, 49);
		frame.getContentPane().add(btnDelete);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtbname.setText("");
				txtbedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name, edition, price;
				name = txtbname.getText();
				edition = txtbedition.getText();
				price = txtprice.getText();
				
				try {
					String query = "INSERT INTO book (name, edition, price) VALUES (?,?,?)";
					pat = con.prepareStatement(query);
					pat.setString(1, name);
					pat.setString(2, edition);
					pat.setString(3, price);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
					txtbname.setText("");
					txtbedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
					
				}
				catch(SQLException se)
				{
					se.printStackTrace();
				}
				
			}
		});
	}
}
