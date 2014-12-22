import model.*;

import java.sql.SQLException;

/**
 * Created by ming.li on 22/12/2014.
 */
public class TestDatabase {
    public static void main(String[] args) {
        System.out.println("Running db");

        Database db = new Database();

        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.addPerson(new Person("Ming", "Developer", AgeCategory.adult, EmploymentCategory.Employed, "???", false, Gender.Male));

        try {
            db.save();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.disConnect();
    }
}
