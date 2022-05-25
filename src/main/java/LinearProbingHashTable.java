import java.util.*;

public class LinearProbingHashTable<K, V> implements GradableMap<K, V> {
	private int size;
	private double loadfactor;
	private HashTableEntry<K, V>[] array;

	public LinearProbingHashTable(){
		size = 11;
		loadfactor = .75;
	}
	
	public LinearProbingHashTable(int size){
		this.size = size;
		loadfactor = 0.75;
		
	}

	/**
	 * @param size
	 * @param loadFactor
	 */
	public LinearProbingHashTable(int size, double loadFactor){
		this.size = size;
		this.loadfactor = loadFactor;
	
	}

	/**
	 * Clear this hashtable so that it contains no keys.
	 */
	@Override
	public void clear() {
		int i=0;
		while(i > size-1){
			array[i] = null;
			i--;
		}

	}

	/**
	 * @param arg0 key whose presence in this map is to be tested
	 * @return true if and only if the specified object is a key in this hashtable, as determined by the equals method;
	 * 			false otherwise.
	 */
	@Override
	public boolean containsKey(Object arg0) {
		int i = 0;
		if (array[i].getKey().equals(arg0)) return true;
		else{
			// Case if the slot is occupied previously(now is empty or not),
			// traverse the array until we find an open slot
			while(i<size-1){
				if (array[i].getKey().equals(arg0)) return true;
				i++;
			}
		}
		return false;
	}

	/**
	 * @param arg0 value whose presence in this map is to be tested
	 * @return return true if some keys maps to the value argument in this hashtable as determined by the equals method;
	 * 			false otherwise.
	 */
	@Override
	public boolean containsValue(Object arg0) {

		int i = 0;
		while(i < size-1){
			if (array[i].getValue().equals(arg0)) return true;
			i++;
		}
		return false;
	}

	/**
	 * @return a set containing all the HashTableEntry objects from the entries table.
	 */
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> set = new HashSet<>();
		int i = 0;
		while(i < size-1){
			if (array[i] != null){
				set.add(array[i]);
			}
			i++;
		}
		return set;
	}

	/**
	 * @param arg0 the key whose associated value is to be returned
	 * @return 	1) If the key is in the table, return the value associated with that key.
	 * 			2) If the key is not in the table, return null.
	 * 			3) If key is null, throw a NullPointerException immediately.
	 */
	@Override
	public V get(Object arg0) {
		int index = 0;
		if (arg0 == null)  throw new NullPointerException("The key cannot be null");//key is null
		while(index < size-1){
			//find the key in the table, return the value associated with the key
			if (array[index].getKey().equals(arg0)) return array[index].getValue();
			index++;
		}
		return null;//key not found
	}

	/**
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		return array.length == 0;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<>();
		int i = 0;
		while(i < size-1){
			if (array[i] != null){
				set.add(array[i].getKey());
			}
			i++;
		}

		return null;
	}

	@Override
	public V put(K key, V value) {
		HashTableEntry<K,V> entry = new HashTableEntry<>(key,value);
		if(key == null) throw new NullPointerException("The key cannot be null");

		if(!containsKey(key)){
			if(array.length+1 > size*loadfactor) size*=2;//auto increase the size if exceed the max load factor
			if(array[key.hashCode()%size] != null) array[key.hashCode()%size] = entry;
			else {
				int i = 0;
				while (i < size - 1) {
					if (array[i].isAvailable()) {
						array[i] = entry;
					}

					i++;
				}
			}
			return null;
		} else if (containsKey(key)) {
			V temp = get(key);
			if(array[key.hashCode()%size].getKey().equals(key)) array[key.hashCode()%size].setValue(value);
			else{
				int i = 0;
				while(i < size-1){
					if (array[i].getKey().equals(key)) {
						array[i].setValue(value);
					}
					}
					i++;
				}
			return temp;
			}
		return null;

		}


	@Override
	public void putAll(Map<? extends K, ? extends V> otherMap) {
		for (Map.Entry<? extends K, ? extends V> entry : otherMap.entrySet()){
			put(entry.getKey(),entry.getValue());
		}
		
	}

	@Override
	public V remove(Object key) {
		if (key == null) throw new NullPointerException("Key cannot be null");
		if (containsKey(key)) {
			int index = 0;
			if (array[key.hashCode() % size].getKey().equals(key)){
				array[key.hashCode() % size].setAvailable(true);
				index = key.hashCode() % size;
			}

			else {
				int i = 0;
				while (i < size - 1) {
					if (array[i].getKey().equals(key)) index = i;
					i++;
				}
			}
			V temp = array[index].getValue();
			array[index] = new HashTableEntry(null,null);
			return temp;
		}else
			return null;

	}


	@Override
	public int size() {
		return size;
	}

	@Override
	public Collection<V> values() {
		Collection<V> collect = new ArrayList<>();
		for (Map.Entry<K, V> entry: array){
			collect.add(entry.getValue());
		}

		return collect;
	}

	@Override
	public HashTableEntry<K, V>[] getArray() {
		return getArray();

	}

	@Override
	public void setArray(HashTableEntry<K, V>[] array) {
		this.array = array;
		
	}

	@Override
	public void setSize(int size) {
		this.size = size;
		
	}

}
