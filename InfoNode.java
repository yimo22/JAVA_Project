package mindmap;

import java.util.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class InfoNode {
	public int NumofChild = 0;
	public static int NumofNode;
	public static int NumofParent;

	static class Node {
		private String data;
		private Node child;
		private Node sibling;
		private Node parents;
		private int X, Y, Height, Width;
		private Color color;
		private JLabel lbl;

		Node() {
			this.data = null;
			this.child = null;
			this.sibling = null;
			this.lbl = null;
			this.X = 0;
			this.Y = 0;
			this.Height = 50;
			this.Width = 50;
			this.color = Color.BLACK;
		}

		public Node(String data, Node child, Node sibling, Node parents, int x, int y, int w, int h, int color) {
			this.data = data;
			this.child = child;
			this.sibling = sibling;
			this.parents = parents;
			this.X = x;
			this.Y = y;
			this.Height = h;
			this.Width = w;
			this.color = new Color(color);
			this.lbl = null;

		}

		public Node(String data, int x, int y, int w, int h, int color) {
			this.data = data;
			this.child = null;
			this.sibling = null;
			this.parents = null;
			this.X = x;
			this.Y = y;
			this.Height = h;
			this.Width = w;
			this.color = new Color(color);
			this.lbl = new JLabel(data);
			this.lbl.setHorizontalAlignment(JLabel.CENTER);
			this.lbl.setBorder(new LineBorder(Color.black, 3));
			this.lbl.setBackground(this.color);
			this.lbl.setOpaque(true);
			this.lbl.addMouseListener(new MindMap.MyMouseListener());
			this.lbl.addMouseMotionListener(new MindMap.MyMouseListener());
		}

		public Node getParents() {
			return parents;
		}

		public void setParents(Node parents) {
			this.parents = parents;
		}

		public int getX() {
			return X;
		}

		public void setX(int x) {
			X = x;
		}

		public int getY() {
			return Y;
		}

		public void setY(int y) {
			Y = y;
		}

		public int getHeight() {
			return Height;
		}

		public void setHeight(int height) {
			Height = height;
		}

		public int getWidth() {
			return Width;
		}

		public void setWidth(int width) {
			Width = width;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		Node(String data) {
			this.data = data;
			this.X = 0;
			this.Y = 0;
			this.Width = 50;
			this.Height = 50;
			this.child = null;
			this.sibling = null;
			this.parents = null;
			this.color = color.WHITE;
			this.Width = 50;
			this.Height = 50;
			this.lbl = new JLabel(data);
			this.lbl.setHorizontalAlignment(JLabel.CENTER);
			this.lbl.setBorder(new LineBorder(Color.black, 3));
			this.lbl.setBackground(this.color);
			this.lbl.setOpaque(true);
			this.lbl.addMouseListener(new MindMap.MyMouseListener());
			this.lbl.addMouseMotionListener(new MindMap.MyMouseListener());
			NumofNode++;
		}

		JLabel GetJlabel() {
			return lbl;
		}

		void Show_Info() {
			System.out.println("Data : " + data);
			System.out.println("X : " + X);
			System.out.println("Y : " + Y);
			System.out.println("Height : " + Height);
			System.out.println("Width : " + Width);
			System.out.println("Parent node : " + this.parents);
			System.out.println("Sibling node : " + this.sibling);
			System.out.println("Child node : " + this.child);
		}

		void SetPosition(int x, int y) {
			this.X = x;
			this.Y = y;
		}

		void SetData(String data) {
			this.data = data;
			this.lbl = new JLabel(data);
			lbl.addMouseListener(new MindMap.MyMouseListener());
			lbl.addMouseMotionListener(new MindMap.MyMouseListener());
		}

		void SetChild(Node child) {
			this.child = child;
		}

		void SetSibling(Node sibling) {
			this.sibling = sibling;
		}

		String GetData() {
			return data;
		}
		
		Node GetChild() {
			return child;
		}

		Node GetSibling() {
			return sibling;
		}
	};

	class Tree {
		private Node root;
		private Node pn; // present node
		private LinkedList<Node> queue = new LinkedList<Node>();

		Tree() {
			root = null;
			pn = null;
		}

		Tree(String data, int x, int y, int w, int h, int color) {
			root = new Node(data, x, y, w, h, color);
			pn = root;
		}

		Tree(String data) {
			root = new Node(data);
			pn = root;
		}
		void AllNodeSave(Node js) {
			JLabel tmp = js.GetJlabel();
			js.SetPosition(tmp.getX() , tmp.getY());
			js.setColor(tmp.getBackground());
			js.setWidth(tmp.getWidth());
			js.setHeight(tmp.getHeight());
			
			if(js.GetChild() != null)
				AllNodeSave(js.GetChild());
			if(js.GetSibling()!=null)
				AllNodeSave(js.GetSibling());
		}
		void SetPosAndadd(Node js, int x, int y, int w, int h) {
			js.SetPosition(x, y);
			js.setWidth(w);
			js.setHeight(h);
			js.GetJlabel().setBounds(js.getX(), js.getY(), js.getWidth(), js.getHeight());
			MindMap.CenterPanel.add(js.GetJlabel());
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

		void LevelTourNode(Node js_present) {
			queue.add(js_present.GetChild());
			Node pre = js_present;
			SetPosAndadd(js_present, 430, 40, 50, 50);

			MindMap.CenterPanel.add(pre.GetJlabel());

			Node tmp;
			while (!queue.isEmpty()) {
				tmp = queue.poll();
				if (tmp != null) {
					if (Depth(js_present, pre) == Depth(js_present, tmp)) { // Depth가 같은경우
						try {
							SetPosAndadd(tmp, pre.getX() + 70, pre.getY(), 50, 50);
							if(tmp.getParents().GetChild()== tmp) {
                   MindMap.print_index1++;
                   MindMap.print_index2=0;
                   MindMap.node_paint[MindMap.print_index1][MindMap.print_index2] = tmp.getParents().GetJlabel();
                   MindMap.print_index2++;
                   MindMap.node_paint[MindMap.print_index1][MindMap.print_index2] = tmp.GetJlabel();
               }
               else {
               MindMap.print_index2++;
               MindMap.node_paint[MindMap.print_index1][MindMap.print_index2] = tmp.GetJlabel();
               }
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (Depth(js_present, pre) + 1 == Depth(js_present, tmp)) {// Depth 가 달라지는경우
						try {
							SetPosAndadd(tmp, 30, pre.getY() + 100, 50, 50);
							               MindMap.print_index1++;
               MindMap.print_index2=0;
               MindMap.node_paint[MindMap.print_index1][MindMap.print_index2] = tmp.getParents().GetJlabel();
               MindMap.print_index2++;
               MindMap.node_paint[MindMap.print_index1][MindMap.print_index2] = tmp.GetJlabel();

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
		void SetRoot(String data) {
			root = new Node(data);
			pn = root;
		}

		void SetPN(Node n) {
			pn = n;
		}

		Node GetRoot() {
			return root;
		}

		Node GetPN() {
			return pn;
		}

		void NodeChangeData(String data) {
			pn.SetData(data);
		}

		void ParentInsertChild(String data, int pre_tab, int Current_tab) {
			Integer myInteger = null;
			Node tmp;
			if (pn == null) {
				SetRoot(data);
			} else {
				if (pre_tab == Current_tab) {
					pn.SetSibling(new Node(data));
					pn.GetSibling().setParents(pn.getParents());
					myInteger = new Integer(pn.getY());
					pn.GetSibling().setY(myInteger);
					SetPN(pn.GetSibling());
				} else if (pre_tab + 1 == Current_tab) {
					pn.SetChild(new Node(data));
					pn.GetChild().setParents(pn);
					myInteger = new Integer(pn.getY());
					SetPN(pn.GetChild());
					pn.setY(myInteger.intValue() + 100);
					NumofParent++;
				} else if (pre_tab - 1 == Current_tab) {
					tmp = pn.getParents();
					tmp.SetSibling(new Node(data));
					SetPN(tmp.GetSibling());
					pn.setParents(tmp.getParents());
					myInteger = new Integer(tmp.getY());
					pn.setY(myInteger.intValue());

				} else {
					for (int i = pre_tab - Current_tab; i > 0; i--)
						SetPN(pn.getParents());
					pn.SetSibling(tmp = new Node(data));
					tmp.setParents(pn.getParents());
					SetPN(pn.GetSibling());
				}

			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}