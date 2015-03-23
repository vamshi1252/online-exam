package com.online.exam.online_exam;


//Tree to DLL
//Inorder to post order

class Node {
	int data;
	public Node lchild;
	public Node rchild;

	Node(int data) {
		this.data = data;
		lchild = rchild = null;
	}
}

class MaxDepth {
	public Node node;
	public int height;

	MaxDepth(Node node, int height) {
		this.node = node;
		this.height = height;
	}
}

class Pointer {
	public Node head;
	public Node tail;
	
	Pointer(Node head, Node tail){
		this.head = head;
		this.tail = tail;
	}
}

public class Tree {

	public static Node constructNode() {
		Node root = new Node(30);
		root.lchild = new Node(20);
		root.rchild = new Node(40);
		root.lchild.lchild = new Node(10);
		root.lchild.rchild = new Node(25);
		root.rchild.lchild = new Node(35);
		root.rchild.rchild = new Node(50);
		root.lchild.lchild.lchild = new Node(5);
		root.rchild.rchild.rchild = new Node(60);
		root.rchild.rchild.lchild = new Node(54);
		root.rchild.rchild.lchild.rchild = new Node(56);
		return root;
	}

	public static void display(Node root) {
		if (root == null)
			return;
		display(root.lchild);
		System.out.println(root.data);
		display(root.rchild);
	}

	public static int getLast(Node root) {
		if (root == null)
			return 0;
		return 1 + Math.max(getLast(root.lchild), getLast(root.rchild));
	}

	public static MaxDepth getLastNode(Node root) {
		if (root == null)
			return null;
		if (root.lchild == null && root.rchild == null)
			return new MaxDepth(root, 1);

		MaxDepth maxDepth1 = getLastNode(root.lchild);
		MaxDepth maxDepth2 = getLastNode(root.rchild);
		if(maxDepth1 == null){
			maxDepth2.height++;
			return maxDepth2;
		}
		if(maxDepth2 == null){
			 maxDepth1.height++;
			 return maxDepth1;
		}
		
		
		if (maxDepth1.height >= maxDepth2.height) {
			maxDepth1.height++;
			return maxDepth1;
		}
		else {
			maxDepth2.height++;
			return maxDepth2;
		}
	}
	
	public static  Pointer convertToDLL(Node root) {
		if(root == null) return null;
		if(root.lchild == null && root.rchild == null)
			return new Pointer(root,root);
		
		if(root.lchild == null){
			Pointer p2 = convertToDLL(root.rchild);
			return new Pointer(p2.head, p2.tail);
		}
		
		if(root.rchild == null){
			Pointer p1 = convertToDLL(root.lchild);
			return new Pointer(p1.head, p1.tail);
		}
			
		Pointer p1 = convertToDLL(root.lchild);
		Pointer p2 = convertToDLL(root.rchild);
	    
		p1.tail.rchild = root;
		root.lchild = p1.tail;
		root.rchild = p2.head;
		p2.head.lchild = root;
		
		return new Pointer(p1.head, p2.tail);
	}
	
	public static void displayList(Pointer p) {
		Node head = p.head;
		while(head != null) {
			System.out.print(head.data + "-> ");
			head = head.rchild;
		}
		System.out.println("-----");
		
		Node tail = p.tail;
		while(tail != null) {
			System.out.print(tail.data + "-> ");
			tail = tail.lchild;
		}
	}
	
	

	public static void main(String args[]) {
		Node root = constructNode();
		//display(root);
		//MaxDepth temp = getLastNode(root);
	//	System.out.println(temp.node.data);
		
		Pointer p3 = convertToDLL(root);
		displayList(p3);
		
		// System.out.println(getLast(root));
	}
}
