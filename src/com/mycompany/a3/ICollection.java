package com.mycompany.a3;

public interface ICollection {
	public void add(GameObject n);
	public void remove(GameObject x);
	public IIterator getIterator();
	
}
