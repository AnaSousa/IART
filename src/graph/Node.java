package graph;

public class Node {

	private static int currentId=0;
	private int id;
	
	// 0 - não tem nada; 1 - contentor; 2 - lixeira; 3 - bomba gasolina
	private int type=0;
	
	public Node() {
		id=currentId;
		currentId++;
	}

}
