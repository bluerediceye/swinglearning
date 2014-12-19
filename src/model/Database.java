package model;

import java.util.List;

/**
 * Created by ming.li on 19/12/2014.
 */
public class Database {
    private List<Person> people;

    public Database() {
    }

    public Database(List<Person> people) {
        this.people = people;
    }

    public void addPerson(Person person){
        people.add(person);
    }

    public List<Person> getPeople() {
        return people;
    }
}