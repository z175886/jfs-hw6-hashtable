import java.util.Map.Entry;

public class HashTableEntry<K,V> implements Entry<K, V> {

	private K key;
	private V value;
	private boolean isAvailable;

	public HashTableEntry(K key, V value){
		this.key = key;
		this.value = value;
		isAvailable = false;

	}
	
	public boolean isAvailable(){
		return isAvailable;
	}
	
	public void setAvailable(boolean isAvailable){
		this.isAvailable = isAvailable;
	}

	
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		this.value = value;
		return value;
	}
	
	@Override
	public int hashCode(){
		return key.hashCode();

	}
	
	@Override
	public boolean equals(Object o){
		if(hashCode()== o.hashCode()) return  true;
		return false;
	}
}
