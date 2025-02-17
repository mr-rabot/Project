package main.popups;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.border.DropShadowBorder;

import com.formdev.flatlaf.ui.FlatScrollPaneUI;

import designs.CLabel;
import designs.MyScrollBarUI;

public class VisitT extends JFrame {
	private JButton btnremove;
	String cd = System.getProperty("user.dir");
	private JPanel contentPane;
	Toolkit t = Toolkit.getDefaultToolkit();
	Dimension dim = new Dimension(t.getScreenSize());
	int height = (int) dim.getHeight();
	private final CLabel lblNewLabel_1;

	private ResultSet rs;

	private Statement st;
	private JTable table;
	int width = (int) dim.getWidth();

	@SuppressWarnings("serial")
	public VisitT() {
		setTitle("Visits");
//    setAlwaysOnTop(true);
		setDefaultCloseOperation(2);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VisitT.class.getResource("/Images/ic1.png")));
		setBounds(width - 1240, 320, 619, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel_1 = new CLabel();
		lblNewLabel_1.setLineColor(new Color(255, 0, 0));
		lblNewLabel_1.setForeground(new Color(0, 128, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", 1, 15));
		lblNewLabel_1.setBounds(40, 10, 400, 26);
		lblNewLabel_1.setHorizontalAlignment(0);
		DropShadowBorder shadowborder = new DropShadowBorder();
		shadowborder.setShowLeftShadow(true);
		shadowborder.setShowRightShadow(true);
		shadowborder.setShowTopShadow(true);
		shadowborder.setShowBottomShadow(true);
		shadowborder.setShadowColor(Color.black);
		shadowborder.setShadowSize(10);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(shadowborder);
		scrollPane.setBounds(5, 40, 456, 218);
		DefaultTableModel tableModel = new DefaultTableModel(new Object[0][], new String[] { "ID", "Date", "Visit" }) {
			final boolean[] columnEditables = { true, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table = new JTable(tableModel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(22);
		table.getTableHeader().setBackground(Color.ORANGE);
		table.setSelectionBackground(new Color(83, 250, 71));
		table.getTableHeader().setFont(new Font("Tahoma", 1, 12));
		table.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
		table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 30));
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);

		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		scrollPane.setUI(new FlatScrollPaneUI());
		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());

		btnremove = new JButton("Remove");
		btnremove.setOpaque(false);
		btnremove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con1 = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
					con1.setAutoCommit(false);
					PreparedStatement ps = con1.prepareStatement("Select * from admin");
					ResultSet rs = ps.executeQuery();
					String pass = null;
					JPanel panel = new JPanel();

					JLabel label = new JLabel("  Enter Passcode");
					label.setHorizontalTextPosition(SwingConstants.CENTER);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setFont(new Font("Tahoma", 1, 14));
					label.setForeground(new Color(255, 102, 0));
					JPasswordField passf = new JPasswordField(10);
					passf.setEchoChar('*');
					passf.setFont(new Font("Tahoma", 1, 11));
					JToggleButton tb = new JToggleButton("show");
					ItemListener il = new ItemListener() {

						@Override
						public void itemStateChanged(ItemEvent e) {
							int state = e.getStateChange();
							if (state == ItemEvent.SELECTED) {
								passf.setEchoChar((char) 0);
								tb.setText("hide");
							} else {
								passf.setEchoChar('*');
								tb.setText("show");
							}
							// TODO Auto-generated method stub

						}
					};
					tb.addItemListener(il);
					panel.add(label);
					panel.add(passf);
					panel.add(tb);
					panel.setAlignmentX(Component.CENTER_ALIGNMENT);
					String[] options = new String[] { "Ok", "Cancel" };
					int option = JOptionPane.showInternalOptionDialog(null, panel, "Update Heading",
							JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					String password = null;
					if (option == 0) {
						password = new String(passf.getPassword());
					}

					if (rs.next()) {
						pass = rs.getString(1);
					}
					con1.commit();
					con1.close();
					TableModel dmd = table.getModel();
					int[] row = table.getSelectedRows();
					if (table.getSelectedRowCount() != 0) {
						try {
							if (!password.equals(null)) {
								if (password.equalsIgnoreCase(pass)) {
									try {

										Class.forName("org.sqlite.JDBC");
										Connection con = DriverManager
												.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");
										con.setAutoCommit(false);
										for (int element : row) {
											int mdr = table.convertRowIndexToModel(element);
											int pname1 = (int) dmd.getValueAt(mdr, 0);
											String qr = "delete from visit where id = '" + pname1 + "'";
											ps = con.prepareStatement(qr);
											ps.executeUpdate();
											DefaultTableModel dm1 = (DefaultTableModel) table.getModel();
											dm1.removeRow(mdr);
											JOptionPane.showMessageDialog(contentPane, "Data Deleted !!! ");
										}
										con.commit();
										con.close();
									} catch (Throwable ex) {
										ex.printStackTrace();
									}
								} else
									JOptionPane.showConfirmDialog(contentPane, "Wrong Passcode ,Try Again ", "Error",
											-1);
							}
						} catch (NullPointerException localNullPointerException) {
						}
					}
				} catch (Throwable a) {
				}
			}
		});
		btnremove.setBackground(new Color(255, 0, 0));
		btnremove.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(36)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE).addGap(37))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(224)
						.addComponent(btnremove, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(275, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(1).addComponent(scrollPane,
						GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(6)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnremove, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(5)));

		contentPane.setLayout(gl_contentPane);
	}

	public void lvdata(String name, String nm) throws Throwable {
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");
		con.setAutoCommit(false);
		String queri = "select id, date, vst from visit  where pid='" + name + "' order by date";
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		lblNewLabel_1.setText(nm);
		st = con.createStatement();
		rs = st.executeQuery(queri);
		dm.setRowCount(0);
		while (rs.next()) {
			int Id = rs.getInt("id");
			String vdate = rs.getString("date");
			String vsts = rs.getString("vst");
			SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat frmtr = new SimpleDateFormat("dd-MMM-yyyy");
			Date sd = fr.parse(vdate);
			String date = frmtr.format(sd);
			Object[] bo = { Id, date, vsts };
			dm.insertRow(0, bo);
		}
		con.commit();
		con.close();
	}
}
