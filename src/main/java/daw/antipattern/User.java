package daw.antipattern;

import java.io.Serializable;

public class User implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 8483694217383301792L;

	private String name;
    
	private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}