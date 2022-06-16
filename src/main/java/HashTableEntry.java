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

		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}
	
	@Override
	public int hashCode(){
		return (this.getKey()==null   ? 0 : this.getKey().hashCode()) ^
				(this.getValue()==null ? 0 : this.getValue().hashCode());

	}
	
	@Override
	public boolean equals(Object o){
		try{
			HashTableEntry e2 =  (HashTableEntry) o;
			return (this.getKey()==null ? e2.getKey()==null : this.getKey().equals(e2.getKey()))  &&
					(this.getValue()==null ? e2.getValue()==null : this.getValue().equals(e2.getValue()));

		}catch (Exception e){
			System.out.println("Object doesn't in the same object type");
			return false;
		}

	}
}
