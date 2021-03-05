package mindmap;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import mindmap.InfoNode.Node;

public class MindMap extends JFrame {
	private JFrame frm;
	private JMenuBar Menu_Mindmap;
	private JMenu screenMenu1, screenMenu2, screenMenu3, screenMenu4;
	private JMenuItem newmake_menu, load_menu, save_menu, saveas_menu, close_menu, change_menu, change_color_menu, Help;
	private JSplitPane splitPane1, splitPane2;
	static public JPanel LeftPanel, RightPanel;
	static public CenterPanel CenterPanel;
	private JTextArea ta;
	private JScrollPane sc;
	private JButton btn_L, btn_R, new_make, load, save, save_as, close, apply, change;
	private JLabel lblText, lblX, lblY, lblW, lblH, lblColor;
	static private JLabel ptr = null;
	static private JTextField textField, textField_1, textField_2, textField_3, textField_4, textField_5;
	private InfoNode outer = new InfoNode();
	private InfoNode.Tree root = null;
	private JLabel lblAttributePane, lblMindMap, lblTexteditor;
	JFileChooser chooser = new JFileChooser();
	static int label_x[];
	static int label_y[];
	int tmp = 0;
	int first = 0; // CenterPanel�� ��ó�� �ҷ��ö� ���� �Ⱥ��̰� �Ϸ�
	static int num;
	private static Point startpos;
	private static int startx, starty;
	public static JLabel node_paint[][] = new JLabel[100][100];
	public static int print_index1;
	public static int print_index2;
	static boolean isDragged; // ���콺 �巡�� üũ
	static int offX, offY; // ���콺 ��������ǥ
	public static JLabel open_paint[][] = new JLabel[100][100];
	static int open_num;
	public static int open_index1;
	public static int open_index2;

	static Focus focus = new Focus();
	static Color color;
	static Color color_tmp;

	public MindMap() {
		frm = new JFrame("MindMap");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setSize(1280, 720);
		frm.setVisible(true);
		Create_Menu();
		Create_ToolBar();
		Create_Splitpane();
		TextEditor();
		isDragged = false;

		frm.validate();
	}

	public InfoNode.Tree getRoot() {
		return root;
	}

	public void setRoot(InfoNode.Tree root) {
		this.root = root;
	}

	class CenterPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.RED);
			if (first != 0 && InfoNode.NumofNode != 0 && tmp == 0 && InfoNode.NumofNode == 1) { // �ʱ�ȭ
				tmp++;
			}
			if (first == 0) { // ���ø��������� ��츦 �����ؾ��ؼ� �ؾ��Ѵ�
				g.drawLine(0, 0, 0, 0);
				first++;
			} else if (InfoNode.NumofNode != 0 || open_num != 0) // ��尡 �ƿ� ���� ��� �������� �ͼ��Ƕߴ� �� ����
			{
				for (int j = 0; j < 100; j++) {
					for (int i = 0; i < 100; i++) {
						if (MindMap.node_paint[j][i] != null && open_num == 0) {
							int p_x[] = new int[4];
							int p_y[] = new int[4];
							int c_x[] = new int[4];
							int c_y[] = new int[4];
							double tmp_value = 10000;
							int tmp_p_x = 0, tmp_p_y = 0, tmp_c_x = 0, tmp_c_y = 0;
							p_x[0] = (MindMap.node_paint[j][0].getX() + (MindMap.node_paint[j][0].getWidth() / 2));
							p_y[0] = MindMap.node_paint[j][0].getY();
							p_x[1] = (MindMap.node_paint[j][0].getX() + (MindMap.node_paint[j][0].getWidth()));
							p_y[1] = MindMap.node_paint[j][0].getY() + MindMap.node_paint[j][0].getHeight() / 2;
							p_x[2] = (MindMap.node_paint[j][0].getX() + (MindMap.node_paint[j][0].getWidth() / 2));
							p_y[2] = MindMap.node_paint[j][0].getY() + (MindMap.node_paint[j][0].getHeight());
							p_x[3] = MindMap.node_paint[j][0].getX();
							p_y[3] = MindMap.node_paint[j][0].getY() + MindMap.node_paint[j][0].getHeight() / 2;
							c_x[0] = (MindMap.node_paint[j][i].getX() + (MindMap.node_paint[j][i].getWidth() / 2));
							c_y[0] = MindMap.node_paint[j][i].getY();
							c_x[1] = (MindMap.node_paint[j][i].getX() + (MindMap.node_paint[j][i].getWidth()));
							c_y[1] = MindMap.node_paint[j][i].getY() + MindMap.node_paint[j][i].getHeight() / 2;
							c_x[2] = (MindMap.node_paint[j][i].getX() + (MindMap.node_paint[j][i].getWidth() / 2));
							c_y[2] = MindMap.node_paint[j][i].getY() + (MindMap.node_paint[j][i].getHeight());
							c_x[3] = MindMap.node_paint[j][i].getX();
							c_y[3] = MindMap.node_paint[j][i].getY() + MindMap.node_paint[j][i].getHeight() / 2;

							for (int a = 0; a < 4; a++) {
								for (int b = 0; b < 4; b++) {
									if (Math.sqrt(
											Math.pow(p_x[a] - c_x[b], 2) + Math.pow(p_y[a] - c_y[b], 2)) < tmp_value) {
										tmp_value = Math
												.sqrt(Math.pow(p_x[a] - c_x[b], 2) + Math.pow(p_y[a] - c_y[b], 2));
										tmp_p_x = p_x[a];
										tmp_p_y = p_y[a];
										tmp_c_x = c_x[b];
										tmp_c_y = c_y[b];
									}
								}
							}
							g.drawLine(tmp_p_x, tmp_p_y, tmp_c_x, tmp_c_y);
						} else if (open_paint[j][i] != null && open_num != 0) {
							int p_x[] = new int[4];
							int p_y[] = new int[4];
							int c_x[] = new int[4];
							int c_y[] = new int[4];
							double tmp_value = 10000;
							int tmp_p_x = 0, tmp_p_y = 0, tmp_c_x = 0, tmp_c_y = 0;
							p_x[0] = (open_paint[j][0].getX() + (open_paint[j][0].getWidth() / 2));
							p_y[0] = open_paint[j][0].getY();
							p_x[1] = (open_paint[j][0].getX() + (open_paint[j][0].getWidth()));
							p_y[1] = open_paint[j][0].getY() + open_paint[j][0].getHeight() / 2;
							p_x[2] = (open_paint[j][0].getX() + (open_paint[j][0].getWidth() / 2));
							p_y[2] = open_paint[j][0].getY() + (open_paint[j][0].getHeight());
							p_x[3] = open_paint[j][0].getX();
							p_y[3] = open_paint[j][0].getY() + open_paint[j][0].getHeight() / 2;
							c_x[0] = (open_paint[j][i].getX() + (open_paint[j][i].getWidth() / 2));
							c_y[0] = open_paint[j][i].getY();
							c_x[1] = (open_paint[j][i].getX() + (open_paint[j][i].getWidth()));
							c_y[1] = open_paint[j][i].getY() + open_paint[j][i].getHeight() / 2;
							c_x[2] = (open_paint[j][i].getX() + (open_paint[j][i].getWidth() / 2));
							c_y[2] = open_paint[j][i].getY() + (open_paint[j][i].getHeight());
							c_x[3] = open_paint[j][i].getX();
							c_y[3] = open_paint[j][i].getY() + open_paint[j][i].getHeight() / 2;
							for (int a = 0; a < 4; a++) {
								for (int b = 0; b < 4; b++) {
									if (Math.sqrt(
											Math.pow(p_x[a] - c_x[b], 2) + Math.pow(p_y[a] - c_y[b], 2)) < tmp_value) {
										tmp_value = Math
												.sqrt(Math.pow(p_x[a] - c_x[b], 2) + Math.pow(p_y[a] - c_y[b], 2));
										tmp_p_x = p_x[a];
										tmp_p_y = p_y[a];
										tmp_c_x = c_x[b];
										tmp_c_y = c_y[b];
									}
								}
							}
							g.drawLine(tmp_p_x, tmp_p_y, tmp_c_x, tmp_c_y);

						}
					}
				}

			}
		}
	}

	void Create_Menu() {
		Menu_Mindmap = new JMenuBar();
		screenMenu1 = new JMenu("File");
		screenMenu1.add(newmake_menu = new JMenuItem("���� �����"));
		newmake_menu.addActionListener(new Listener());
		screenMenu1.add(load_menu = new JMenuItem("����"));
		load_menu.addActionListener(new Listener());
		screenMenu1.add(save_menu = new JMenuItem("����"));
		save_menu.addActionListener(new Listener());
		screenMenu1.add(saveas_menu = new JMenuItem("�ٸ� �̸����� ����"));
		saveas_menu.addActionListener(new Listener());
		screenMenu1.add(close_menu = new JMenuItem("�ݱ�"));
		close_menu.addActionListener(new Listener());
		Menu_Mindmap.add(screenMenu1);
		screenMenu2 = new JMenu("Apply");
		Menu_Mindmap.add(screenMenu2);
		screenMenu3 = new JMenu("Change");
		screenMenu3.add(change_menu = new JMenuItem("����"));
		change_menu.addActionListener(new Listener());
		screenMenu3.add(change_color_menu = new JMenuItem("������(�ȷ�Ʈ)"));
		change_color_menu.addActionListener(new Listener());
		Menu_Mindmap.add(screenMenu3);
		screenMenu4 = new JMenu("Help");
		screenMenu4.add(Help = new JMenuItem("����"));
		Help.addActionListener(new Listener());
		Menu_Mindmap.add(screenMenu4);
		frm.setJMenuBar(Menu_Mindmap);
	}

	void Create_Splitpane() {
		LeftPanel = new JPanel();
		LeftPanel.setBackground(Color.RED);
		CenterPanel = new CenterPanel();
		CenterPanel.setBackground(Color.WHITE);
		RightPanel = new JPanel();
		RightPanel.setBackground(Color.WHITE);
		splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, LeftPanel, CenterPanel);
		CenterPanel.setLayout(null);

		lblMindMap = new JLabel("< Mind Map >");
		lblMindMap.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		lblMindMap.setHorizontalAlignment(SwingConstants.CENTER);
		lblMindMap.setBounds(321, 0, 226, 35);
		CenterPanel.add(lblMindMap);
		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, RightPanel);
		RightPanel.setLayout(null);

		lblAttributePane = new JLabel("Attribute Pane");
		lblAttributePane.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttributePane.setForeground(Color.DARK_GRAY);
		lblAttributePane.setBounds(35, 22, 154, 50);
		lblAttributePane.setBackground(Color.BLUE);
		RightPanel.add(lblAttributePane);

		lblText = new JLabel("TEXT : ");
		lblText.setBounds(51, 115, 49, 18);
		RightPanel.add(lblText);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setEditable(false);
		textField.setBounds(124, 112, 65, 24);
		RightPanel.add(textField);
		textField.setColumns(10);

		lblX = new JLabel("X : ");
		lblX.setBounds(63, 195, 24, 18);
		RightPanel.add(lblX);

		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setBounds(124, 192, 65, 24);
		RightPanel.add(textField_1);
		textField_1.setColumns(10);

		lblY = new JLabel("Y : ");
		lblY.setBounds(63, 278, 23, 18);
		RightPanel.add(lblY);

		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setBounds(124, 275, 65, 24);
		RightPanel.add(textField_2);
		textField_2.setColumns(10);

		lblW = new JLabel("W : ");
		lblW.setBounds(63, 350, 29, 18);
		RightPanel.add(lblW);

		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setBounds(124, 347, 65, 24);
		RightPanel.add(textField_3);
		textField_3.setColumns(10);

		lblH = new JLabel("H : ");
		lblH.setBounds(63, 429, 25, 18);
		RightPanel.add(lblH);

		textField_4 = new JTextField();
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setBounds(124, 426, 65, 24);
		RightPanel.add(textField_4);
		textField_4.setColumns(10);

		lblColor = new JLabel("Color");
		lblColor.setBounds(51, 496, 35, 18);
		RightPanel.add(lblColor);

		textField_5 = new JTextField();
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setBounds(124, 493, 65, 24);
		RightPanel.add(textField_5);
		textField_5.setColumns(10);

		btn_R = new JButton("����");
		btn_R.addActionListener(new Listener());
		btn_R.setForeground(Color.BLACK);
		btn_R.setBackground(Color.WHITE);
		btn_R.setBounds(63, 579, 105, 27);
		RightPanel.add(btn_R);
		frm.getContentPane().add(splitPane2, BorderLayout.CENTER);
		splitPane1.setDividerLocation((int) frm.getSize().getWidth() / 3 - 200);
		splitPane2.setDividerLocation((int) frm.getSize().getWidth() * 2 / 3 + 200);

	}

	static class Focus extends FocusAdapter {

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			JLabel jlabel = (JLabel) arg0.getSource();
			color = jlabel.getBackground();
			jlabel.setBackground(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
			jlabel.setForeground(color);

		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			JLabel jlabel = (JLabel) arg0.getSource();
			jlabel.setBackground(color);
			jlabel.setForeground(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
		}

	}

	static class MyMouseListener implements MouseListener, MouseMotionListener {
		public void mousePressed(MouseEvent me) {
			isDragged = false;
			JLabel p = (JLabel) me.getSource();
			if (p.contains(new Point(me.getX(), me.getY()))) {
				isDragged = true;
				startpos = me.getPoint();
				startx = me.getX();
				starty = me.getY();
			}
			((JComponent) me.getSource()).requestFocus();
		}

		public void mouseReleased(MouseEvent me) {
			// ���콺 ��ư�� ������Ǹ� �巡�� ��� ����
			ptr = (JLabel) me.getSource();
			textField.setText(ptr.getText());
			textField_1.setText(Integer.toString(ptr.getX())); // X
			textField_2.setText(Integer.toString(ptr.getY())); // Y
			textField_3.setText(Integer.toString(ptr.getWidth())); // W
			textField_4.setText(Integer.toString(ptr.getHeight())); // H
			textField_5.setText(Integer.toHexString(ptr.getBackground().getRGB())); // Color
			isDragged = false;
		}

		public void mouseDragged(MouseEvent me) {
			// �巡�� ����� ��쿡�� �簢�� �̵���Ŵ
			JLabel p = (JLabel) me.getSource();
			int x = p.getX();
			int y = p.getY();
			int w = p.getWidth();
			int h = p.getHeight();
			int dx = me.getX() - startpos.x;
			int dy = me.getY() - startpos.y;
			offX = p.getX() - startx;
			offY = p.getY() - starty;
			if (isDragged) { // ũ�������� ��.
				switch (num) {
				case 1:
					if (!(h - dy < 30)) {
						p.setBounds(x, y + dy, w, h - dy);
						p.revalidate();
					}
					break;
				case 2:
					if (!(h + dy < 30)) {
						p.setBounds(x, y, w, h + dy);
						p.revalidate();
						startpos = me.getPoint();
					}
					break;
				case 3:
					if (!(w - dx < 30)) {
						p.setBounds(x + dx, y, w - dx, h);
						p.revalidate();
					}
					break;
				case 4:
					if (!(w + dx < 30))
						p.setBounds(x, y, w + dx, h);
					p.revalidate();
					startpos = me.getPoint();
					break;
				case 5:
					if (!(h - dy < 30) && !(w - dx < 30)) {
						p.setBounds(x + dx, y + dy, w - dx, h - dy);
						p.revalidate();
					}
					break;
				case 0:
					break;
				}
			}
			if (num == 0)
				p.setLocation(me.getX() + offX, me.getY() + offY);
			CenterPanel.repaint();
		}

		public void mouseMoved(MouseEvent me) {
			JLabel label = (JLabel) me.getSource(); // ���� ��
			if ((me.getY() >= 0) && (me.getY() <= 3) && (me.getX() >= 0) && (me.getX() <= 3)) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
				num = 5;
			} else if ((me.getX() >= 0) && (me.getX() <= 3)
					&& ((me.getY() >= label.getHeight() - 3) && (me.getY() <= label.getHeight()))) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
				num = 6;
			} else if ((me.getX() >= label.getWidth() - 3) && (me.getX() <= label.getWidth()
					&& ((me.getY() >= label.getHeight() - 3) && (me.getY() <= label.getHeight())))) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
				num = 7;
			} else if ((me.getX() >= label.getWidth() - 3) && (me.getX() <= label.getWidth())
					&& ((me.getY() >= 0) && (me.getY() <= 3))) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
				num = 8;
			} else if ((me.getY() >= 0) && (me.getY() <= 5)) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
				num = 1;
			} else if ((me.getY() <= label.getHeight()) && (me.getY() >= label.getHeight() - 5)) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				num = 2;
			} else if ((me.getX() >= 0) && (me.getX() <= 5)) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				num = 3;
			} else if ((me.getX() <= label.getWidth()) && (me.getX() >= label.getWidth() - 5)) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				num = 4;
			} else {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				num = 0;
			}

		}

		public void mouseClicked(MouseEvent me) {
			ptr = (JLabel) me.getSource();
			textField.setText(ptr.getText());
			textField_1.setText(Integer.toString(ptr.getX())); // X
			textField_2.setText(Integer.toString(ptr.getY())); // Y
			textField_3.setText(Integer.toString(ptr.getWidth())); // W
			textField_4.setText(Integer.toString(ptr.getHeight())); // H
			textField_5.setText(Integer.toHexString(color.getRGB())); // Color

		}

		public void mouseEntered(MouseEvent me) {
			JLabel label = (JLabel) me.getSource();
		}

		public void mouseExited(MouseEvent me) {

		}
	}

	class Dialog {
		boolean isOk;

		public Dialog() {
			// TODO Auto-generated constructor stub
			JButton btn1 = new JButton("�����Ͻðڽ��ϱ�?");
			JButton btn2 = new JButton("������");
			JButton btn3 = new JButton("�������� ����");
			JPanel p = new JPanel();
			int result = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "Warning", JOptionPane.OK_CANCEL_OPTION);
			if (result == 0) // OK=0
				this.isOk = true;
			else
				this.isOk = false;
		}

		public boolean isOk() {
			return isOk;
		}
	};

	class MenuActionListener implements ActionListener {
		JColorChooser chooser = new JColorChooser(); // �÷� ���̾�α� ����

		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Color")) { // Color �޴� �������� ���
				Color selectedColor = chooser.showDialog(null, "Color", Color.YELLOW); // �÷� ���̾�α׸� ����ϰ� ����ڰ� ������ ����
																						// �˾ƿ�.
				if (selectedColor != null)
					ptr.setForeground(selectedColor); // ��� ��ư�� �����ų� ���̾�α׸� �׳� �ݴ� ���
			}
		}
	}

	class Listener implements ActionListener {
		// new_make,load,save,save_as,close,apply,change
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == Help) {
				final Frame help = new Frame("����");
				help.setVisible(true);
				help.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						help.setVisible(false);
						help.dispose();
					}
				});
				help.setSize(720, 480);
				JLabel contents = new JLabel(
						"<html>���ε��(mind map)�̶�?<br> ��ġ ������ �׸�����, �ڽ��� ���±��� ����� �����̳�, �ڱ� ���� ���� �� �� �ִ� ����̴�.<br> ���ε���� ���� �����̸� ��ü�� ������ �� ���踦 ǥ���Ѵ�.���ط� ���� ���� ����� ���ε���� �����̶� �� �� �ִ�.<br><br>##���۹�##<br>1. Text Editor Pane���� ���� �߽��� �Ǵ� ������ �Է��Ѵ�.<br>2. EnterŰ�� ���� �� ������ ���õ� �������� �Է��Ѵ�. �̶� ������ �Է��ϱ� ���ؼ��� TabŰ�� ������ �Ѵ�.<br><br>##RGB �÷� ���� ����##<br>red	#FF0000	lime	#00FF00	blue	#0000FF"
								+ "white	#FFFFFF	<br>black	#000000	gray(grey)	#808080"
								+ "teal	#008080	green	#008000	<br>aqua	#00FFFF"
								+ "silver	#C0C0C0	maroon	#800000	purple	#800080"
								+ "<br>olive	#808000	navy	#000080	fuchsia	#FF00FF"
								+ "yellow	#FFFF00				</html>");
				help.add("North", contents);
				ImageIcon ic = new ImageIcon(MindMap.class.getResource("").getPath() + "RGB.png");
				JLabel lbImage1 = new JLabel(ic);
				help.add(lbImage1);
			}
			if (e.getSource() == new_make || e.getSource() == newmake_menu) {
				for (int j = 0; j < 100; j++) {
					for (int i = 0; i < 100; i++) {
						MindMap.node_paint[j][i] = null;
					}
				}
				for (int j = 0; j < 100; j++) {
					for (int i = 0; i < 100; i++) {
						open_paint[j][i] = null;
					}
				}

				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				Remove_CenterPanel();
				ta.setText("");
				setRoot(null);
			}
			if (e.getSource() == change_color_menu) {
				if (ptr == null)
					return;
				JColorChooser chooser = new JColorChooser(); // �÷� ���̾�α� ����
				Color selectedColor = chooser.showDialog(null, "������(�ȷ�Ʈ)", Color.YELLOW); // �÷� ���̾�α׸� ����ϰ� ����ڰ� ������
				if (selectedColor != null) {
					ptr.setOpaque(true);
					ptr.setBackground(selectedColor); // ��� ��ư�� �����ų� ���̾�α׸� �׳� ���� ���� ���
				}
			}
			if (e.getSource() == load || e.getSource() == load_menu) {
				if (chooser.getSelectedFile() == null)
					if (new Dialog().isOk()) { // �����۾��� �������� �ʰ�, ���ο���� ��������� && �����ϰڴٰ� �� ���
						chooser = new JFileChooser();
						FileNameExtensionFilter filter = new FileNameExtensionFilter("json", "json");
						chooser.setDialogTitle("Save as...");
						chooser.setMultiSelectionEnabled(false);
						chooser.setFileFilter(filter);
						String pathname = null;
						int ret = chooser.showSaveDialog(null);
						if (ret == JFileChooser.APPROVE_OPTION) {
							pathname = chooser.getSelectedFile().getPath();
							new JsonFileOut(root, pathname);
						}
					}
				FileNameExtensionFilter filter = new FileNameExtensionFilter("json", "json");
				chooser = new JFileChooser();
				chooser.setDialogTitle("Load");
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileFilter(filter);
				String pathName = null;
				int ret = chooser.showOpenDialog(null);
				if (ret == JFileChooser.APPROVE_OPTION) {
					pathName = chooser.getSelectedFile().getPath();
					JsonFileIn jsonfilein = new JsonFileIn(pathName);
					setRoot(jsonfilein.getFileInroot());
					Setting_txtarea(root.GetRoot(), ta);
					BuildTree(root.GetRoot());
					open_num++;
					Open_Paint(root.GetRoot());
					CenterPanel.repaint();
					CenterPanel.revalidate();
				}

			}
			if (e.getSource() == save || e.getSource() == save_menu) {
				root.AllNodeSave(root.GetRoot());
				if (chooser.getSelectedFile() != null) {
					new JsonFileOut(root, chooser.getSelectedFile().getPath());
				} else {
					FileNameExtensionFilter filter = new FileNameExtensionFilter("json", "json");
					chooser.setDialogTitle("Save");
					chooser.setMultiSelectionEnabled(false);
					chooser.setFileFilter(filter);
					String pathname = null;
					int ret = chooser.showSaveDialog(null);
					if (ret == JFileChooser.APPROVE_OPTION) {
						pathname = chooser.getSelectedFile().getPath();
						new JsonFileOut(root, pathname);
					}
				}
			}
			if (e.getSource() == save_as || e.getSource() == saveas_menu) {
				root.AllNodeSave(root.GetRoot());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("json", "json");
				chooser.setDialogTitle("Save as...");
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileFilter(filter);
				String pathname = null;
				int ret = chooser.showSaveDialog(null);
				if (ret == JFileChooser.APPROVE_OPTION) {
					pathname = chooser.getSelectedFile().getPath();
					new JsonFileOut(root, pathname);
				}
			}
			if (e.getSource() == close) {
				System.exit(0);
			}
			if (e.getSource() == btn_L || e.getSource() == apply) {
				for (int j = 0; j < 100; j++) {
					for (int i = 0; i < 100; i++) {
						MindMap.node_paint[j][i] = null;
					}
				}
				Remove_CenterPanel();
				try {
					StringReader result = new StringReader(ta.getText());
					BufferedReader br = new BufferedReader(result);
					String line;
					while (true) {
						line = br.readLine();
						if (line == null)
							return;
						if (!line.contains("\t")) {
							root = outer.new Tree(line);
							int CurrentTab = 0;
							while ((line = br.readLine()) != null) {
								int NumOfTab = 0;
								String temp = line;
								line = line.trim();
								while (temp.startsWith("\t")) {
									temp = temp.substring(1);
									NumOfTab++;
								}
								root.ParentInsertChild(line, CurrentTab, NumOfTab);
								CurrentTab = NumOfTab;
							}
							root.LevelTourNode(root.GetRoot());
							ShowAllNode(root.GetRoot());
							CenterPanel.revalidate();
							CenterPanel.repaint();

							break;
						}

					} // While�� ����
				} catch (IOException error) {
					error.printStackTrace();
				}
			} // btn_L �� ����
			if (e.getSource() == btn_R || e.getSource() == change || e.getSource() == change_menu) {
				if (ptr == null)
					return;
				CenterPanel.repaint();
				ptr.setBounds(Integer.parseInt(textField_1.getText()), Integer.parseInt(textField_2.getText()),
						Integer.parseInt(textField_3.getText()), Integer.parseInt(textField_4.getText()));
				if (textField_5.getText().length() == 6 && ptr.getX() == Integer.parseInt(textField_1.getText())) {
					ptr.setOpaque(true);
					Color color = new Color(Integer.parseInt(textField_5.getText().replaceFirst("#", ""), 16));
					ptr.setBackground(color);
				}
			}
		}
	}

	void TextEditor() {
		LeftPanel.setLayout(new BorderLayout());
		btn_L = new JButton("\uC801 \uC6A9");
		btn_L.addActionListener(new Listener());
		sc = new JScrollPane();
		sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		LeftPanel.add(sc);

		lblTexteditor = new JLabel("Text Editor Pane");
		lblTexteditor.setBackground(Color.BLACK);
		lblTexteditor.setHorizontalAlignment(SwingConstants.CENTER);
		sc.setColumnHeaderView(lblTexteditor);
		ta = new JTextArea(30, 30);
		sc.setViewportView(ta);
		ta.setBackground(Color.WHITE);
		ta.setLineWrap(true);
		LeftPanel.add(btn_L, BorderLayout.SOUTH);
	}

	void Create_ToolBar() {
		// new_make,load,save,save_as,close,apply,change
		JToolBar toolBar = new JToolBar("Tool Bar");
		toolBar.setBackground(Color.WHITE);
		toolBar.add(new_make = new JButton("���� �����"));
		new_make.addActionListener(new Listener());
		toolBar.addSeparator();
		toolBar.add(load = new JButton("����"));
		load.addActionListener(new Listener());
		toolBar.addSeparator();
		toolBar.add(save = new JButton("����"));
		save.addActionListener(new Listener());
		toolBar.addSeparator();
		toolBar.add(save_as = new JButton("�ٸ��̸����� ����"));
		save_as.addActionListener(new Listener());
		toolBar.addSeparator();
		toolBar.add(close = new JButton("�ݱ�"));
		close.addActionListener(new Listener());
		toolBar.addSeparator();
		toolBar.add(apply = new JButton("����"));
		apply.addActionListener(new Listener());
		toolBar.addSeparator();
		toolBar.add(change = new JButton("����"));
		change.addActionListener(new Listener());
		toolBar.addSeparator();

		frm.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	void Remove_CenterPanel() {
		CenterPanel.removeAll();
		CenterPanel.add(lblMindMap);
		CenterPanel.repaint();
	}

	void Setting_txtarea(Node rootnode, JTextArea txtar) {
		if (rootnode.getParents() != null) {
			Node Checknode = rootnode.getParents();
			while (Checknode != null) {
				txtar.append("\t");
				Checknode = Checknode.getParents();
			}

		}
		txtar.append(rootnode.GetData() + "\n");
		if (rootnode.GetChild() != null)
			Setting_txtarea(rootnode.GetChild(), txtar);
		if (rootnode.GetSibling() != null)
			Setting_txtarea(rootnode.GetSibling(), txtar);

	}

	void BuildTree(Node js) {
		JLabel tmp = js.GetJlabel();
		tmp.setBounds(js.getX(), js.getY(), js.getWidth(), js.getHeight());
		tmp.setBackground(js.getColor());
		tmp.addFocusListener(MindMap.focus);
		tmp.setFocusable(true);
		CenterPanel.add(tmp);
		if (js.GetChild() != null)
			BuildTree(js.GetChild());
		if (js.GetSibling() != null)
			BuildTree(js.GetSibling());
		CenterPanel.repaint();
	}

	int Depth(Node rootnode, Node present) {
		int cnt = 0;
		Node tmp = present;
		while (tmp != rootnode) {
			cnt++;
			if (tmp == null)
				break;
			tmp = tmp.getParents();
		}
		return cnt;
	}

	void Open_Paint(Node js_present) {
		LinkedList<Node> queue = new LinkedList<Node>();

		queue.add(js_present.GetChild());
		Node pre = js_present;

		MindMap.CenterPanel.add(pre.GetJlabel());

		Node tmp;
		while (!queue.isEmpty()) {
			tmp = queue.poll();
			if (tmp != null) {
				if (Depth(js_present, pre) == Depth(js_present, tmp)) { // Depth�� �������
					try {
						if (tmp.getParents().GetChild() == tmp) {
							open_index1++;
							open_index2 = 0;
							open_paint[open_index1][open_index2] = tmp.getParents().GetJlabel();
							open_index2++;
							open_paint[open_index1][open_index2] = tmp.GetJlabel();
						} else {
							open_index2++;
							open_paint[open_index1][open_index2] = tmp.GetJlabel();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (Depth(js_present, pre) + 1 == Depth(js_present, tmp)) {// Depth �� �޶����°��
					try {
						open_index1++;
						open_index2 = 0;
						open_paint[open_index1][open_index2] = tmp.getParents().GetJlabel();
						open_index2++;
						open_paint[open_index1][open_index2] = tmp.GetJlabel();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (tmp.GetChild() != null)
					queue.addLast(tmp.GetChild());
				if (tmp.GetSibling() != null)
					queue.addFirst(tmp.GetSibling());
				pre = tmp;
			}
		}
	}

	void ShowAllNode(Node js) {
		JLabel jlabel = (JLabel) js.GetJlabel();
		jlabel.addFocusListener(MindMap.focus);
		jlabel.setFocusable(true);
		if (js.GetChild() != null) {
			ShowAllNode(js.GetChild());
		}
		if (js.GetSibling() != null) {
			ShowAllNode(js.GetSibling());
		}
	}

	public static void main(String[] args) {

		MindMap ptr = new MindMap();
	}
}