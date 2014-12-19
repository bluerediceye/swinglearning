import java.util.EventObject;

/**
 * Created by ming.li on 18/12/2014.
 */
public class FormEvent extends EventObject {
    private String name;
    private String occupation;
    private int ageCategory;
    private String empCat;

    public FormEvent(Object source) {
        super(source);
    }

    public FormEvent(Object source, String name, String occupation, int ageCategory, String empCat) {
        super(source);
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.empCat = empCat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public String getEmpCat() {
        return empCat;
    }

    public void setEmpCat(String empCat) {
        this.empCat = empCat;
    }

    public void setAgeCategory(int ageCategory) {
        this.ageCategory = ageCategory;
    }
}
