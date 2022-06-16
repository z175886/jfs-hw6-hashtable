import java.util.*;

public class LinearProbingHashTable<K, V> implements GradableMap<K, V> {
	private int size;
	private double loadfactor;
	private HashTableEntry<K, V>[] array;
	private int numOfEntry=0;

	public LinearProbingHashTable(){
		size = 11;
		loadfactor = .75;
		this.array= new HashTableEntry[size];
	}
	
	public LinearProbingHashTable(int size){
		this.size = size;
		loadfactor = 0.75;
		this.array= new HashTableEntry[size];
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
	 * Clear this hashtable to be an empty array,set size and number of entry back to 0
	 */
	@Override
	public void clear() {
		array= new HashTableEntry[0];
		size = 0;
		numOfEntry = 0;
		}



	/**
	 * @param arg0 key whose presence in this map is to be tested
	 * @return true if and only if the specified object is a key in this hashtable, as determined by the equals method;
	 * 			false otherwise.
	 */
	@Override
	public boolean containsKey(Object arg0) {
		int targetIndex = arg0.hashCode() % size;
		if (array[targetIndex]!=null && array[targetIndex].getKey().equals(arg0))return true;

		for (int i = 0; i< size ; i++){
			if(array[i]!=null){
				if (array[i].getKey().equals(arg0) && !array[i].isAvailable()) return true;
			}
		}
		return false;
	}

	/**
	 * Check if the hashtable contain this value
	 * //1)check if the entry is null,
	 * //2)check if the entry's value is a match
	 * //3)check if the entry actually exists (case when the entry is marked available when being removed)
	 * @param arg0 value whose presence in this map is to be tested
	 * @return return true if some keys maps to the value argument in this hashtable as determined by the equals method;
	 * 			false otherwise.
	 */
	@Override
	public boolean containsValue(Object arg0) {
		for (int i = 0; i< size ; i++){
			if(array[i]!=null) {
				if (array[i].getValue().equals(arg0) && !array[i].isAvailable()) return true;
			}
		}
		return false;
	}

	/**
	 * @return a set containing all the HashTableEntry objects from the entries table.
	 */
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> set = new HashSet<>();

		for (int i = 0; i< size ; i++){
			if (array[i] != null)
				set.add(array[i]);
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

		if (arg0 == null)  throw new NullPointerException("The key cannot be null");//key is null
		for (int i = 0; i< size ; i++){
			//find the key in the table, return the value associated with the key
			if (array[i] != null && array[i].getKey().equals(arg0)) return array[i].getValue();

		}
		return null;//key not found
	}

	/**
	 * @return true if this hashtable maps no keys to values;
	 * 			false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return array.length == 0;
	}

	/**
	 * @return a set view of the keys contained in this map.
	 */
	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<>();
		for (int i = 0; i< size ; i++){
			if (array[i] != null) set.add(array[i].getKey());

		}
		return set;
	}

	/**
	 * 	Put entry in to the array
	 * 	resize when the max load factor is exceeded; that is, if the max load factor is .75
	 * 	and the current load factor is also .75, do not resize.
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return a)If key is non-null and was not previously in the table, add the entry
	 * 				and return null.
	 * 			b)If key is non-null and was previously in the table,
	 * 				add the entry and return the old value (the one that was just overwritten).
	 *			c)If key is null, throw a NullPointerException immediately. On
	 * 				average, this should happen in O(1) time.


	 */
	@Override
	public V put(K key, V value) {
		HashTableEntry<K, V> entry = new HashTableEntry<>(key, value);
		//Dynamically expand the array
		if (numOfEntry + 1 > size * loadfactor){
			int newSize = size * 2;
			HashTableEntry<K,V>[] newArray = new HashTableEntry[newSize];
			for(int i = 0 ;i < size ; i++){
				newArray[i] = array[i];
			}
			size = newSize;
			array = newArray;
		};//auto increase the size if exceed the max load factor
		if (key == null) throw new NullPointerException("The key cannot be null");
		int targetIndex = key.hashCode() % size;
		if (containsKey(key)) {
			//no collision in the target position,add entry to the array
			if (array[targetIndex].getKey() == key) {
				V oldValue = array[targetIndex].getValue();
				array[targetIndex] = entry;
				return oldValue;
			}
			//collision in target position
			//search the part of array that is below the targetIndex
			for (int i = targetIndex; i < size; i++) {
				//Check if key is match and the slot is open
				if (array[i].getKey() == key ) {
					//the entry in the slot actually exist(not removed)
					if(!array[i].isAvailable()){
						V oldValue = array[i].getValue();
						array[i] = entry;
						return oldValue;
					//the entry in the slot is marked removed
					}else{
						array[i] = entry;
						return null;
					}

				}

			}
			//search the part of array that is above the targetIndex
			for (int i = 0; i < targetIndex; i++) {
				if (array[i].getKey() == key ) {
					//the entry in the slot actually exist(not removed)
					if(array[i] == null || !array[i].isAvailable()){
						V oldValue = array[i].getValue();
						array[i] = entry;
						return oldValue;
						//the entry in the slot is removed
					}else{
						array[i] = entry;
						return null;
					}
				}
			}
		}
		//The table doesn't contain this key
		else {

			if (array[targetIndex] == null || array[targetIndex].isAvailable()) {
				//add new entry to the table
				array[targetIndex] = entry;
				numOfEntry ++;
				return null;
				//previously had collision, keep search for next available slot
			} else {
				//search the part of array that is below the targetIndex
				for (int i = targetIndex; i < size; i++) {
					if (array[i] == null || array[i].isAvailable()) {
						//add new entry to the table
						array[i] = entry;
						numOfEntry ++;
						return null;
					}
				}
				//search the part of array that is above the targetIndex
				for (int i = 0; i < targetIndex; i++) {
					if (array[i] == null || array[i].isAvailable()) {
						//add new entry to the table
						array[i] = entry;
						numOfEntry ++;
						return null;
					}
				}

			}
		}
		return null;
	}


	/**
	 * @param otherMap entries in this map will be stored in this hashtable
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> otherMap) {
		for (Map.Entry<? extends K, ? extends V> entry : otherMap.entrySet()){
			put(entry.getKey(),entry.getValue());
		}
		
	}

	/**
	 * @param key key whose mapping is to be removed from the map
	 * @return  a) If key is non-null and the entry exists, remove the entry and
	 * 				return the associated value.
	 * 			b)If key is non-null and the entry does not exist, return null.
	 * 			c)If key is null, throw a NullPointerException.
	 */
	@Override
	public V remove(Object key) {
		if (key == null) throw new NullPointerException("Key cannot be null");
		int targetIndex = key.hashCode() % size;
		//This slot is null, meaning no key with this target index has been added to the table before,
		// concluding the key is not in the table.
		if(array[targetIndex] == null ) return null;
		//This slot is not null, the key is match and entry is actually exist.
		if(array[targetIndex].getKey() == key && !array[targetIndex].isAvailable() ){
			array[targetIndex].setAvailable(true);
			numOfEntry --;
			return array[targetIndex].getValue();
		}
		//there was a collision previously, so key is not found in the target slot,iterating over every slot in the array to find
		// the key and check if entry is actually exist.
		else{
			for(int i = targetIndex; i < size; i++ ){
				if(array[i].getKey() == key && !array[i].isAvailable()){
					array[i].setAvailable(true);
					numOfEntry --;
					return array[i].getValue();
				}
			}
			for(int i = 0; i < targetIndex; i++ ){
				if(array[i].getKey() == key && !array[i].isAvailable()){
					array[i].setAvailable(true);
					numOfEntry --;
					return array[i].getValue();
				}
			}
		}
		//key is not null, and key is not being found after iterating over every slot in the array
		return  null;

	}


	/**
	 * @return capacity of the hashtable
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * @return a arraylist of values that contained in this hashtable.
	 */
	@Override
	public Collection<V> values() {
		Collection<V> collect = new ArrayList<>();
		for (Map.Entry<K, V> entry: array){
			if(entry != null)
			collect.add(entry.getValue());
		}
	return collect;

	}

	/**
	 * A setter from gradableMap
	 */
	@Override
	public HashTableEntry<K, V>[] getArray() {
		return array;

	}

	/**
	 * A setter from gradableMap
	 */
	@Override
	public void setArray(HashTableEntry<K, V>[] array) {
		this.array = array;
		
	}


	/**
	 * A setter from gradableMap
	 */
	@Override
	public void setSize(int size) {
		HashTableEntry[]newArray= new HashTableEntry[size];
		for(int i =0 ;i < size; i++){
			newArray[i] = this.array[i];
		}
		this.size = size;
		this.array = newArray;
		
	}


}
