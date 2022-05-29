import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class Main extends JFrame implements ActionListener, MouseListener{
	public Connection con;
	public Statement st;
	public ResultSet rs;
	public PreparedStatement ps; 
	
	
	JPanel
	northPanel, titlePanel,leftPanel, 
	kodePanel, namaPanel, hargaPanel, stokPanel, buttonPanel,
	rightPanel, tablePanel, southPanel;
	
	JLabel
	titleLabel, kodeLabel, namaLabel, hargaLabel, stokLabel, guideLabel; 
	
	JTextField
	titleTextField, kodeTextField, namaTextField, hargaTextField, stokTextField;
	
	JButton
	insert, view, update, delete, cancel;
	
	JTable
	table;
	
	JScrollPane
	sp;
	
	DefaultTableModel
	model;
	
	Menu m = new Menu();
	
	public Main() {
		settings();
		connect();
		initComponent();
		setVisible(true);
	}
	
	public void settings() {
		setTitle("PT Pudding Menu");
		setLayout(new BorderLayout());
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void initComponent() {
		// Panel
		northPanel = new JPanel();
		titlePanel = new JPanel();
		
		leftPanel = new JPanel(new GridLayout(6, 1));
		kodePanel = new JPanel();
		namaPanel = new JPanel();
		hargaPanel = new JPanel();
		stokPanel = new JPanel();
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		rightPanel = new JPanel();
		tablePanel = new JPanel();
//		+ southPanel -> petunjuk gunakan button view
		southPanel = new JPanel();
		
		// Label
		titleLabel = new JLabel("PT Pudding Menu");
		kodeLabel = new JLabel("Kode Menu (PD-XXX)");
		namaLabel = new JLabel("Nama Menu");
		hargaLabel = new JLabel("Harga Menu");
		stokLabel = new JLabel("Stok Menu");
		guideLabel = new JLabel("Gunakan tombol View untuk melihat dan refresh data");
		
		// TextField
		kodeTextField = new JTextField();
		namaTextField = new JTextField();
		hargaTextField = new JTextField();
		stokTextField = new JTextField();
		
		
		// Button
		insert = new JButton("Insert");
		view = new JButton("View");
		update = new JButton("Update");
		delete = new JButton("Delete");
		cancel = new JButton("Cancel");
		
		// Add Action Listener
		insert.addActionListener(this);
		view.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		cancel.addActionListener(this);
		
		// Tabel
		model = new DefaultTableModel();
		
		// Menambahkan nama kolom
		model.addColumn("Kode");
		model.addColumn("Nama");
		model.addColumn("Harga");
		model.addColumn("Stok");
		
		// Init table
		table = new JTable();
		table.setModel(model);
		sp = new JScrollPane(table);
		
		// Set Font
		titleLabel.setFont(titleLabel.getFont().deriveFont(20f));
		
		// Set Preferred Size
		leftPanel.setPreferredSize(new Dimension (300, 300));
		rightPanel.setPreferredSize(new Dimension (500, 300));
		kodeLabel.setPreferredSize(new Dimension(200,20));
		namaLabel.setPreferredSize(new Dimension(200,20));
		hargaLabel.setPreferredSize(new Dimension(200,20));
		stokLabel.setPreferredSize(new Dimension(200,20));
		
		kodeTextField.setPreferredSize(new Dimension(200,30));
		namaTextField.setPreferredSize(new Dimension(200,30));
		hargaTextField.setPreferredSize(new Dimension(200,30));
		stokTextField.setPreferredSize(new Dimension(200,30));
		
		insert.setPreferredSize(new Dimension(100,20));
		view.setPreferredSize(new Dimension(100,20));
		update.setPreferredSize(new Dimension(100,20));
		delete.setPreferredSize(new Dimension(100,20));
		cancel.setPreferredSize(new Dimension(100,20));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		sp.setPreferredSize(new Dimension(450, 400));
		
		// Empty Border
		titleLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		
		// Color
		northPanel.setBackground(Color.ORANGE);
		leftPanel.setBackground(Color.white);
		rightPanel.setBackground(Color.WHITE);
		southPanel.setBackground(Color.ORANGE);
		titlePanel.setBackground(Color.ORANGE);
		kodePanel.setBackground(Color.WHITE);
		namaPanel.setBackground(Color.WHITE);
		hargaPanel.setBackground(Color.WHITE);
		stokPanel.setBackground(Color.WHITE);
		buttonPanel.setBackground(Color.WHITE);
		tablePanel.setBackground(Color.WHITE);
		
		// Add to Panel
		titlePanel.add(titleLabel);
		northPanel.add(titlePanel);
		
		kodePanel.add(kodeLabel);
		kodePanel.add(kodeTextField);
		
		namaPanel.add(namaLabel);
		namaPanel.add(namaTextField);
		
		hargaPanel.add(hargaLabel);
		hargaPanel.add(hargaTextField);
		
		stokPanel.add(stokLabel);
		stokPanel.add(stokTextField);
		
		buttonPanel.add(insert);
		buttonPanel.add(view);
		buttonPanel.add(update);
		buttonPanel.add(delete);
		buttonPanel.add(cancel);
		
		leftPanel.add(kodePanel);
		leftPanel.add(namaPanel);
		leftPanel.add(hargaPanel);
		leftPanel.add(stokPanel);
		leftPanel.add(buttonPanel);
		
		tablePanel.add(sp);
		rightPanel.add(tablePanel);
		southPanel.add(guideLabel);
		
		add(leftPanel, BorderLayout.WEST);
		add(northPanel, BorderLayout.NORTH);
		add(rightPanel, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);
	}
	
	public void connect() {
		try {
			String className = "com.mysql.jdbc.Driver";
			Class.forName(className);
			
			String url = "jdbc:mysql://localhost:3306/pt_pudding";
			String username = "root";
			String password = "";
			
			con = DriverManager.getConnection(url, username, password);
			st = con.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tampilkan_data() {
		try {
			String sql = "SELECT * FROM `menu`";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			// delete all rows / clear table
			while(model.getRowCount()!=0) {
				model.removeRow(model.getRowCount()-1);
			}
			
			// add row
			while(rs.next()) {
				model.addRow(new Object[] {
					rs.getString("Kode"),
					rs.getString("Nama"),
					rs.getInt("Harga"),
					rs.getInt("Stok")
				});
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kosongin_form() {
		kodeTextField.setText(null);
		namaTextField.setText(null);
		hargaTextField.setText(null);
		stokTextField.setText(null);
	}
	
	
	public void tambah() {
		try {
			String sql = "INSERT INTO `menu` (`Kode`, `Nama`, `Harga`, `Stok`)" + " VALUES((?),(?),(?),(?))";
			
			String kode = kodeTextField.getText();
			String nama = namaTextField.getText();
			Integer harga = Integer.valueOf(hargaTextField.getText());
			Integer stok = Integer.valueOf(stokTextField.getText());
			
			ps = con.prepareStatement(sql);
			ps.setString(1, kode);
			ps.setString(2, nama);
			ps.setInt(3, harga);
			ps.setInt(4, stok);
			
			ps.execute();
			JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", "BERHASIL", JOptionPane.INFORMATION_MESSAGE);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void edit() {
		try {
			String sql = "UPDATE `menu` SET `Harga` = (?), `Stok`= (?) WHERE Kode = (?)";
			
			String kode = kodeTextField.getText();
			Integer harga = Integer.valueOf(hargaTextField.getText());
			Integer stok = Integer.valueOf(stokTextField.getText());
			
			ps = con.prepareStatement(sql);
			if (getKode(kode)==true) {
				ps.setString(3, kode);
				ps.setInt(1, harga);
				ps.setInt(2, stok);
				
				ps.execute();
				JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!", "BERHASIL", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Tidak dapat menemukan data!", "GAGAL", JOptionPane.INFORMATION_MESSAGE);
			}
			
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getKode(String kode) {
		boolean exist = false;
		try {
			String sql = "SELECT Kode FROM `menu` WHERE Kode = '" + kodeTextField.getText() + "'";
			
			rs = st.executeQuery(sql);
			
			if (rs.next()) { 
				exist = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
			exist = false;
		}
		return exist;
	}
	
	public void hapus() {
		try {
			String sql = "DELETE FROM `menu` WHERE Kode = '" + kodeTextField.getText() + "'";
			ps = con.prepareStatement(sql);
			
			String kode = kodeTextField.getText();
			
			if (getKode(kode)==true) {
				ps.execute();
				JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "BERHASIL", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Tidak dapat menemukan data!", "GAGAL", JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == insert) {
//			+ if validasi gagal
			if(m.validateKode(kodeTextField.getText())==true) {
				if(getKode(kodeTextField.getText())==false) {
					tambah();
				}
				else {
					JOptionPane.showMessageDialog(this, "Duplikat Kode Menu!", "GAGAL", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Input Kode tidak sesuai!", "GAGAL", JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		} else if(e.getSource() == view) {
			tampilkan_data();
		} else if(e.getSource() == update) {
			edit();
		} else if(e.getSource() == delete) {
			hapus();
		} else if(e.getSource() == cancel) {
			kosongin_form();
		} else {
			this.dispose();
			System.exit(0);
		}
		
		kosongin_form();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}