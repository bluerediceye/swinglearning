package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ming.li on 19/12/2014.
 */
public class Database {
    private List<Person> people;

    public Database() {
        people = new ArrayList<>();
    }

    public Database(List<Person> people) {
        this.people = people;
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public List<Person> getPeople() {
        return people;
    }

    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        Person[] persons = people.toArray(new Person[people.size()]);
        oos.writeObject(persons);

        oos.close();
    }

    public void loadFromFIle(File file) throws IOException{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            Person[] persons = (Person[]) ois.readObject();
            people.clear();
            people.addAll(Arrays.asList(persons));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ois.close();
    }
}