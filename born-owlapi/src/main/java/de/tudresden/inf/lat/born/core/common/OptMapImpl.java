package de.tudresden.inf.lat.born.core.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * This is the default implementation of {@link OptMap}. This implementation
 * does not copy the map passed as an argument to create the instance, and it
 * uses the map itself instead. This means that the internal representation can
 * be modified externally, or it can be retrieved by using {@link #asMap()}.
 * 
 * @author Julian Mendez
 * 
 * @param <K>
 *            type of keys in this map
 * @param <V>
 *            type of mapped values
 */
public class OptMapImpl<K, V> implements OptMap<K, V> {

	private final Map<K, V> map;

	/**
	 * Constructs a new map. The default implementation structure is a
	 * {@link HashMap}.
	 */
	public OptMapImpl() {
		this.map = new HashMap<K, V>();
	}

	/**
	 * Constructs a new map using a specified {@link Map}.
	 * 
	 * @param map
	 *            map
	 */
	public OptMapImpl(Map<K, V> map) {
		Objects.requireNonNull(map);
		this.map = map;
	}

	/**
	 * Constructs a new map using another specified {@link OptMap}.
	 * 
	 * @param map
	 *            map
	 */
	public OptMapImpl(OptMap<K, V> map) {
		Objects.requireNonNull(map);
		this.map = map.asMap();
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean containsKey(K key) {
		Objects.requireNonNull(key);
		return this.map.containsKey(key);
	}

	@Override
	public boolean containsValue(V value) {
		Objects.requireNonNull(value);
		return this.map.containsValue(value);
	}

	@Override
	public Optional<V> get(K key) {
		Objects.requireNonNull(key);
		return Optional.ofNullable(this.map.get(key));
	}

	@Override
	public Optional<V> put(K key, V value) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(value);
		return Optional.ofNullable(this.map.put(key, value));
	}

	@Override
	public Optional<V> remove(K key) {
		Objects.requireNonNull(key);
		return Optional.ofNullable(this.map.remove(key));
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Objects.requireNonNull(m);
		this.map.putAll(m);
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}

	@Override
	public Collection<V> values() {
		return this.map.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return this.map.entrySet();
	}

	@Override
	public int hashCode() {
		return this.map.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.map.equals(obj);
	}

	@Override
	public String toString() {
		return this.map.toString();
	}

	@Override
	public Map<K, V> asMap() {
		return this.map;
	}

}
