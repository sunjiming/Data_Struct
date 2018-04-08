package Other;

import java.util.LinkedList;
import java.util.List;

import com.one.three.Queue;

/**
 * 
 * 二叉查找树(BST)
 * 
 * 泛型：Key是实现了Comparable 的 对象。
 * 
 */
public class Simple_Tree<Key extends Comparable<Key>,Value> {
	private Node root; // 根节点

	// 节点
	private class Node {
		private Node lift;// 左子树
		private Node right;// 右子树
		private Key key;
		private Value value;
		private int n;// 指定节点的节点数目

		public Node(Key key, Value value, int n) {
			this.key = key;
			this.value = value;
			this.n = n;
		}
	}
	/**
	 * 添加节点我写了两种，非递归的那种没有实现记录指定节点的子节点数目。
	 * 				递归之所以能实现记录指定节点的节点数目的功能，是因为递归的结尾是一个弹栈的过程，
	 * 				因此可以从叶子节点向上依次更新n值。
	 * add():非递归添加节点
	 * add2():递归添加节点
	 * */
	// add  非递归
	public void add(Key key,Value value) {
		if(root == null){//add first node
			root= new Node(key,value,1);
			return ;
		}
		Node node = new Node(key,value,1);
		Node current = root;
		while(true){
			int cmp = key.compareTo(current.key);
			
			if(cmp<0){//左子树
				if(current.lift!=null){
					current = current.lift;
				}else{
					current.lift = node;
					break ;
				}
			}else if(cmp>0){//右子树
				if(current.right!=null){
					current = current.right;
				}else{
					current.right = node;
					break ;
				}
			}else{// key相等，Value覆盖
				current.value = value;
			}
		}
		return ;
	}
	// add2 递归添加
	public void add2(Key key,Value value){
		root = put(root,key,value);
	}
	private Node put(Node node,Key key,Value value){
		if(node==null){//递归终止条件
			return new Node(key,value,1);
		}
		int cmp = key.compareTo(node.key);
		if(cmp<0){//左子树
			node.lift = put(node.lift, key, value);
		}else if(cmp>0){//右子树
			node.right = put(node.right, key, value);
		}else{
			node.value = value;
		}
		node.n = size(node.lift)+size(node.right)+1;
		return node;
	}
	/**
	 * 根据key值查找节点：这个其实和添加节点的方式一样，只不过添加节点的时候如果是null就添加节点，
	 * 				  而查找到null，就说明该key不存在二叉树里面。
	 * 				 
	 * */ 
	public Object get(Key key){
		if(root==null){
			System.out.println("tree 里面没有节点");
			return null;
		}
		Node current = root;
		while(true){
			int cmp = key.compareTo(current.key);
			if(cmp<0){//左子树
				if(current.lift!=null){
					current = current.lift;
				}else{
					System.out.println("当前tree 没有这个key");
					break ;
				}
			}else if(cmp>0){//右子树
				if(current.right!=null){
					current = current.right;
				}else{
					System.out.println("当前tree 没有这个key");
					break;
				}
			}else{//相等，说明找到该节点
				break;
			}
		}
		return current;
	}
	
	/**
	 * 删除节点：这个是最麻烦的，因为少了一个节点，因此需要稍微的重组一下二叉树结构。
	 * 
	 * */ 
	public void delete(Key key) {
		if(key ==null){
			System.out.println("tree 为null");
			return ;	
		}
		root = delete(root,key);
	}
	public Node delete(Node x, Key key) {
		// 递归结束条件
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if(cmp<0)
			x.lift = delete(x.lift,key);
		else if(cmp>0)
			x.right = delete(x.right,key);
			//key命中
		else{
			/**
			 * 删除节点是叶子节点，或者是只有一个子节点的节点
			 * */ 
			if(x.right==null)
				return x.lift;
			if(x.lift==null)
				return x.right;
			/**
			 * 要删除的节点有两个儿子节点
			 * */
			Node t = x;
			x = min(t.right);
			// 删除命中节点的右子树的最小值节点，并且建立新的链接
			x.right = deleteMin(t.right);
			// 建立新的链接。
			x.lift = t.lift;
		}
		x.n = size(x.lift) + size(x.right) + 1;
		return x;
	}
	private Node deleteMin(Node x) {
		/**
		 * 如何删除的节点？
		 * 用 null覆盖要删除的节点。两种情况：
		 * 		第一种：左侧节点为null，右侧没有节点。null覆盖节点。
		 * 		第二种：左侧节点为null，右侧有节点。右侧节点 覆盖 该节点。
		 * 找到最小值， 如果命中节点的右子树为null,将null赋值给命中节点 如果命中节点的右子树不为null,将右子树替换为命中节点位置
		 */
		if(x.lift==null)
			return x.right;
		
		x.lift = deleteMin(x.lift);
		
		x.n = size(x.lift) + size(x.right) + 1;
		return x;
	}
	
	/**
	 * 二叉树的遍历可以分为三类：
	 * （1）前序遍历，中序遍历，后序遍历。
	 * （2）层级遍历。
	 * （3）从小到大输出二叉树（参考算法第四版）
	 * */
	//前序遍历
	public void printTree1(Node node){
		if(node!=null){
			System.out.println(node.key+" ");
			printTree1(node.lift);
			printTree1(node.right);
		}
	}
	//中序遍历
	public void printTree2(Node node){
		if(node!=null){
			printTree1(node.lift);
			System.out.println(node.key+" ");
			printTree1(node.right);
		}
	}	
	//后序遍历
	public void printTree3(Node node){
		if(node!=null){
			printTree1(node.lift);
			printTree1(node.right);
			System.out.println(node.key+" ");
		}	
	}
	// 层级遍历，无换行(需要借助一个队列)
	public void printTree4(){
		if(root==null){
			return ;
		}
		LinkedList<Node> queue = new LinkedList<>();
		queue.offer(root);
		Node current = null;
		while(!queue.isEmpty()){
			current = queue.poll();
			System.out.print(current.key);
			if(current.lift!=null){
				queue.offer(current.lift);
			}
			if(current.right!=null){
				queue.offer(current.right);
			}
		}
	}
	// 层级遍历，有换行(需要借助一个队列)
	public void printTree5(){
		if(root==null){
			return ;
		}
		LinkedList<Node> queue = new LinkedList<>();
		queue.add(root);
		Node nlast = root;//
		Node last = root;//判断是否换行
		Node current;
		while(!queue.isEmpty()){
			current = queue.poll();
			System.out.print("key:"+current.key+"n:"+current.n+"    ");
			if(current.lift!=null){
				queue.add(current.lift);
				nlast = current.lift;
			}
			if(current.right!=null){
				queue.add(current.right);
				nlast = current.right;
			}
			if(last==current){
				System.out.println();
				last = nlast;
			}
		}
	}
	//从小到大遍历
	public List<Key> printTree6(){
		return keys(min(),max());
	}
	public List<Key> keys(Key lo, Key hi) {
		List<Key> queue = new LinkedList<Key>();
		Keys(root, queue, lo, hi);
		return queue;
	}
	/**
	 * （1）找到最小值，进入队列，并且第一个递归会第一次弹栈
	 * （2）然后，最小值的父节点进入队列。
	 * 然后，进入右子树，重复步骤（1） 和 （2）
	 * 什么时候会输出右子树的值呢？右子树都被当成啦最小值的父节点被输出！！！
	 * 
	 * 一句话概括：先找到最小值进入队列，然后父节点进入队列，然后将兄弟节点看成一颗新的树，继续上述递归。
	 * 
	 * */
	private void Keys(Node x,List queue,Key lo,Key hi){
		if(x ==null)//递归终止条件 
			return;
		int cmplo = lo.compareTo(x.key);
		int cmphi = hi.compareTo(x.key);
		/**
		 * 进入左子树
		 * */
		if(cmplo<0)
				Keys(x.lift,queue,lo,hi);
		if (cmplo <= 0 && cmphi >= 0)
			queue.add(x.key);
		/**
		 *进入右子树
		 * */
		if (cmphi > 0)
			Keys(x.right, queue, lo, hi);
	}
	/**
	 * 工具类：
	 *  size():返回指定节点的节点数目
	 *  max():获取最大的node
	 *  min():获取最小的node
	 * */
	private int size(Node node){
		if(node == null){
			return 0;
		}
		return node.n;
	}
	// 获取最大的node
	public Key max(){
		return max(root).key;
	}
	private Node max(Node node){
		if(node.right==null){
			return node;
		}else{
			return max(node.right);
		}
	}
	// 获取最小的node
	public Key min(){
		return min(root).key;
	}
	private Node min(Node node){
		if(node.lift==null){
			return node;
		}else{
			return min(node.lift);
		}
	}
}
