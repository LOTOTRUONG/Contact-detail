package Function;

public class Contact {
    private String Nom;
    private String Prenom;
    private String DOB;
    private String Adresse;
    private String Tel;
    private String Email;

    public Contact(String Nom, String Prenom, String DOB, String Adresse, String Tel, String Email) {
        this.Nom = Nom;
        this.Prenom = Prenom;
        this.DOB = DOB;
        this.Adresse = Adresse;
        this.Tel = Tel;
        this.Email = Email;
    }

    public String getNom() {
        return Nom;
    }
    public void setNom(String Nom) {
        this.Nom = Nom;
    }
    public String getPrenom() {
        return Prenom;
    }
    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }
    public String getDOB() {return DOB;}
    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
    public String getAdresse() {
        return Adresse;
    }
    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }
    public String getTel() {
        return Tel;
    }
    public void setTel(String Tel) {
        this.Tel = Tel;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

}
