package controller;

import gui.FormEvent;
import model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ming.li on 19/12/2014.
 */
public class Controller {
    private Database database = new Database();

    public List<Person> getPeople(){
        return database.getPeople();
    }

    public void addPerson(FormEvent e) {
        String name = e.getName();
        String occupation = e.getOccupation();
        int ageCat = e.getAgeCategory();
        String empCat = e.getEmpCat();
        String gender = e.getGender();
        boolean isUS = e.isUsCitizen();
        String taxId = e.getTaxId();

        AgeCategory ageCategory;

        switch (ageCat) {
            case 0:
                ageCategory = AgeCategory.child;
                break;
            case 1:
                ageCategory = AgeCategory.adult;
                break;
            case 2:
                ageCategory = AgeCategory.senior;
                break;
            default:
                ageCategory = AgeCategory.adult;
                break;
        }

        EmploymentCategory employmentCategory;

        switch (empCat) {
            case "employed":
                employmentCategory = EmploymentCategory.Employed;
                break;
            case "self-employed":
                employmentCategory = EmploymentCategory.SelfEmployed;
                break;
            case "unemployed":
                employmentCategory = EmploymentCategory.Unemployed;
                break;
            default:
                employmentCategory = EmploymentCategory.Other;
                System.out.println(empCat);
                break;
        }

        Gender genderCategory = "male".equals(gender) ? Gender.Male : Gender.Female;

        Person person = new Person(name, occupation, ageCategory, employmentCategory, taxId, isUS, genderCategory);

        database.addPerson(person);
    }

    public void saveToFile(File file) throws IOException {
        database.saveToFile(file);
    }

    public void loadFromFIle(File file) throws IOException {
        database.loadFromFIle(file);
    }
}
