package model;

import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ming.li on 19/12/2014.
 */
public class Database {
    private List<Person> people;
    private Connection conn;

    public Database() {
        people = new LinkedList<>();
    }

    public Database(List<Person> people) {
        this.people = people;
    }

    public void connect() throws Exception {
        if (conn != null) return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver not found");
        }
        String url = "jdbc:mysql://localhost:3306/swingtest";
        conn = DriverManager.getConnection(url, "root", "");
        System.out.println("Connected: " + conn);
    }

    public void disConnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.printf("Can't close connection.");
            }
        }
    }

    public void save() throws SQLException {

        String checkSql = "select count(*) as count from people where id=?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);

        String insertSql = "insert into people(id, name,age,employment_status,tax_id,us_citizen,gender,occupation) values(?,?,?,?,?,?,?,?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertSql);

        String updateSql = "update people set name=?,age=?,employment_status=?,tax_id=?,us_citizen=?,gender=?,occupation=? where id=?";
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);


        for (Person person : people) {
            int id = person.getId();
            String name = person.getName();
            String occupation = person.getOccupation();
            AgeCategory age = person.getAgeCategory();
            EmploymentCategory emp = person.getEmploymentCategory();
            String taxId = person.getTaxId();
            boolean usCitizen = person.isUsCitizen();
            Gender gender = person.getGender();

            checkStmt.setInt(1, id);
            ResultSet checkResult = checkStmt.executeQuery();
            checkResult.next();

            int count = checkResult.getInt(1);
            System.out.println("Count for person with ID " + id + " is " + count);


            if (count == 0) {
                System.out.println("Insert person with ID " + id);
                int col = 1;
                insertStmt.setInt(col++, id);
                insertStmt.setString(col++, name);
                insertStmt.setString(col++, age.name());
                insertStmt.setString(col++, emp.name());
                insertStmt.setString(col++, taxId);
                insertStmt.setBoolean(col++, usCitizen);
                insertStmt.setString(col++, gender.name());
                insertStmt.setString(col++, occupation);

                insertStmt.executeUpdate();
            } else {
                System.out.println("Update person with ID " + id);
                int col = 1;
                updateStmt.setString(col++, name);
                updateStmt.setString(col++, age.name());
                updateStmt.setString(col++, emp.name());
                updateStmt.setString(col++, taxId);
                updateStmt.setBoolean(col++, usCitizen);
                updateStmt.setString(col++, gender.name());
                updateStmt.setString(col++, occupation);
                updateStmt.setInt(col++, id);
            }

            updateStmt.close();
            insertStmt.close();
            checkStmt.close();
        }
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public List<Person> getPeople() {
        return Collections.unmodifiableList(people);
    }

    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        Person[] persons = people.toArray(new Person[people.size()]);
        oos.writeObject(persons);

        oos.close();
    }

    public void loadFromFIle(File file) throws IOException {
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

    public void removePerson(int index) {
        people.remove(index);
    }
}