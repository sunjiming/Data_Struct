package com.one.three;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * 空参的，双向循环链表
 * 
 * */

public class DoubleLinkList3<Item> implements Iterable<Item>{
	
	private Node<Item> begin;
	private Node<Item> end;
	int n = 0;
	
	// 内部类
	private static class Node<Item>{
		public Item item;
		private Node<Item> prev;
		private Node<Item> next;
	}
	//初始化
	public DoubleLinkList3(){
		begin = new Node<Item>(); 
		end = new Node<Item>(); 
		
		begin.prev = end;
		begin.next = null;
		end.prev =null;
		end.next = begin;
	}
	// 从尾部添加
	public void add(Item item){
		if(begin.next == null){// 说明第一次添加元素
			Node<Item> node =new Node<Item>();
			node.item = item;
			
			begin.next = node;
			node.prev = begin;
			end = node;
			begin.prev = end;
			end.next = begin;
			n++;
		}else{
			Node<Item> node =new Node<Item>();
			node.item = item;
			
			end.next = node;
			node.prev = end;
			end = node;
			begin.prev = end;
			end.next = begin;
			n++;
		}
	}
	// 从尾部删除
	public Item delete(){
		if(n==0){
			System.out.println("链表为空");
			return null;
		}
		Item item = end.item;// 返回删除的对象
		
		Node<Item> node = end.prev;	//作为新的尾巴	
		//旧的尾巴断开
		end.prev = null;
		end.next = null;	
		//新的首尾相连
		node.next = begin;
		begin.prev = node;
		end = node;
		n--;
		return item;
	}
	//判断索引是否合法
	public void valiIndex(int index){
		if(index<=0 || index>n){
			throw new IndexOutOfBoundsException();
		}
	}
	//获取指定位置的节点对象
	public Node<Item> getNode(int index){
		valiIndex(index);
		Node<Item> cur;
		// 从bengin开始，查询次数少
		if(index <= n/2){
			cur = begin.next;
			for(int i = 0 ;i < index-1;i++)
				cur = cur.next;
			return cur;
		}
		// 从end开始，查询次数少
		cur = end;
		for(int i = 0 ;i < n-index;i++){
			cur = cur.prev;
		}
		return cur;
	}
	//获取指定索引对应的item 值
	public Item getItem(int index){
		Node<Item> node = getNode(index);
		return node.item;
	}
	// 判断是否为空
	public boolean isEmpty(){
		return n ==0;
	}
	// 返回列表长度
	public int size(){
		return n;
	}
	// 在指定位置添加Item，不允许在bengin处添加元素。
	public void addIndex(int index,Item item){
		Node<Item> newNode = new Node<Item>();//新节点
		newNode.item = item;
		Node<Item> oldNode = getNode(index);//旧节点
		
		newNode.prev = oldNode.prev;// 新节点，前驱连接
		oldNode.prev.next = newNode;// 旧节点，后继变为新节点
		oldNode.prev = newNode;// 旧节点，前驱为变为新节点
		newNode.next = oldNode;// 新节点，后继变为旧节点
		n++;
	}
	// 删除指定位置的元素
	public Item deleteIndex(int index){
		Node<Item> node = getNode(index);
		node.prev.next = node.next;
		node.next.prev = node.prev;
		
		node.next = null;//断开后继
		node.prev = null;//断开前驱
		
		n--;
		return node.item;
	}
	// 获取第一个节点的Item
	public Item getBegin(){
		return begin.next.item;
	}
	// 获取最后一个节点的Item
	public Item getEnd(){
		return end.item;
	}
	@Override
	public Iterator<Item> iterator() {
		
		return new DoubleLinkListIterator(begin);
	}
	class DoubleLinkListIterator implements Iterator<Item>{
		Node<Item> current;
		int x = n; 
		public DoubleLinkListIterator(Node<Item> current) {
				this.current = current;
		}
		@Override
		public boolean hasNext() {
			return x--!=0;
		}
		@Override
		public Item next() {
			/*if (!hasNext()) 
				throw new NoSuchElementException();*/
			current = current.next;
			Item item = current.item;		
			return item;
		}
	}
}
 