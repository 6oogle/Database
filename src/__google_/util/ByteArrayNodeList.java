package __google_.util;

public class ByteArrayNodeList {
	private Node node = new Node(null);

	public boolean add(byte array[]) {
		node.add(array);
		return true;
	}

	public void remove(int index) {
		node.get(index);
	}

	public byte[] get(int index) {
		return node.get(index);
	}

	public int size() {
		return node.size() - 1;
	}

	private class Node{
		private Node next;
		private byte array[];

		private Node(byte array[]){
			this.array = array;
		}

		private byte[] get(int index){
			if(next == null)return null;
			if(index == 0)return next.array;
			else return next.get(index - 1);
		}

		private void add(byte array[]){
			if(next == null) next = new Node(array);
			else next.add(array);
		}

		private void remove(int index){
			if(next == null)return;
			if(index == 0)next = next.next;
			else next.remove(index - 1);
		}

		private int size(){
			return next == null ? 1 : next.size() + 1;
		}
	}
}
