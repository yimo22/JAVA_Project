	package mindmap;
	
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.Stack;
	
	import org.json.simple.JSONArray;
	import org.json.simple.JSONObject;
	
	import mindmap.InfoNode.Node;
	import mindmap.InfoNode.Tree;
	
	public class JsonFileOut {
		Tree ptr = null;
		String address = null;
	
		public JsonFileOut(Tree ptr, String address) {
			// TODO Auto-generated constructor stub
			this.ptr = ptr;
			if (address.contains(".json")) {
				address = address.replaceAll(".json", "");
				this.address = address.concat(".json");
			} else {
				this.address = address.concat(".json");
			}
			JSONObject jsonObject = new JSONObject();
			JSONArray nodearray = new JSONArray();
			JSONObject node;
	
			if (ptr != null) {
				Stack<Node> stack = new Stack<Node>();
				Node tmp_ptr = ptr.GetRoot();
				stack.push(tmp_ptr);
				while (!stack.isEmpty()) {
					node = new JSONObject();
					tmp_ptr = stack.pop();
					node.put("SERIAL_ID", tmp_ptr.hashCode());
					node.put("data", tmp_ptr.GetData());
					node.put("X", tmp_ptr.getX());
					node.put("Y", tmp_ptr.getY());
					node.put("W", tmp_ptr.getWidth());
					node.put("H", tmp_ptr.getHeight());
					node.put("Color", tmp_ptr.getColor().getRGB());
	
					if (tmp_ptr.getParents() != null)
						node.put("Parent_node", tmp_ptr.getParents().hashCode());
					if (tmp_ptr.GetSibling() != null) {
						node.put("Sibling_node", tmp_ptr.GetSibling().hashCode());
						stack.push(tmp_ptr.GetSibling());
					}
					if (tmp_ptr.GetChild() != null) {
						node.put("Child_node", tmp_ptr.GetChild().hashCode());
						stack.push(tmp_ptr.GetChild());
					}
					nodearray.add(node);
	
				}
				jsonObject.put("Node", nodearray);
				try {
					FileWriter file = new FileWriter(new File(this.address));
					file.write(jsonObject.toJSONString());
					file.flush();
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	
		}
	
		public static void main(String[] args) {
		}
	}
