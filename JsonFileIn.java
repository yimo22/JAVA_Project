package mindmap;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import mindmap.InfoNode.Node;
import mindmap.InfoNode.Tree;

public class JsonFileIn {
	private String pathName;
	private Tree FileInroot;

	public Tree getFileInroot() {
		return FileInroot;
	}

	public void setFileInroot(Tree fileInroot) {
		FileInroot = fileInroot;
	}

	public JsonFileIn(String pathName) {
		if (pathName.contains(".json")) {
			pathName = pathName.replaceAll(".json", "");
			this.pathName = pathName.concat(".json");
		} else {
			this.pathName = pathName.concat(".json");
		}
		// TODO Auto-generated constructor stub

		if (this.pathName != null) {
			JSONParser jsonParser = new JSONParser();

			try {
				Object obj = jsonParser.parse(new FileReader(this.pathName));
				JSONObject jsonObject = (JSONObject) obj;
				JSONArray jslist = (JSONArray) jsonObject.get("Node");
				boolean FLAG = false;
				InfoNode outer = new InfoNode();
				for (int i = 0; i < jslist.size(); i++) {
					jsonObject = (JSONObject) jslist.get(i);
					if (!jsonObject.containsKey(("Parent_node"))) {
						this.FileInroot = outer.new Tree((String) jsonObject.get("data"),
								(int) ((long) jsonObject.get("X")), (int) ((long) jsonObject.get("Y")),
								(int) ((long) jsonObject.get("W")), (int) ((long) jsonObject.get("H")),
								(int) ((long) jsonObject.get("Color")));
						FLAG = true;
						break;
					}
				}
				if (FLAG) {
					Recursive_makeTree(FileInroot.GetRoot(), jslist, jsonObject);
				} else {
					System.out.println("FilePath = " + this.pathName + " 잘못된 접근(There's no root Node to make)");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	void Recursive_makeTree(Node js, JSONArray DATA, JSONObject pre_info) {
		JSONObject temp;
		JSONObject now = pre_info;
		for (int i = 0; i < DATA.size(); i++) {
			temp = (JSONObject) DATA.get(i);
			if (now.containsKey("Child_node")
					&& temp.get("SERIAL_ID").toString().equals(now.get("Child_node").toString())) {
				Node js_tmp;
				js.SetChild(js_tmp = new Node((String) temp.get("data"), (int) ((long) temp.get("X")),
						(int) ((long) temp.get("Y")), (int) ((long) temp.get("W")), (int) ((long) temp.get("H")),
						(int) ((long) temp.get("Color"))));
				js_tmp.setParents(js);
				Recursive_makeTree(js_tmp, DATA, temp);

			}
			if (now.containsKey("Sibling_node")
					&& now.get("Sibling_node").toString().equals(temp.get("SERIAL_ID").toString())) {
				Node js_tmp;
				js.SetSibling(js_tmp = new Node((String) temp.get("data"), (int) ((long) temp.get("X")),
						(int) ((long) temp.get("Y")), (int) ((long) temp.get("W")), (int) ((long) temp.get("H")),
						(int) ((long) temp.get("Color"))));
				js_tmp.setParents(js.getParents());
				Recursive_makeTree(js_tmp, DATA, temp);
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
