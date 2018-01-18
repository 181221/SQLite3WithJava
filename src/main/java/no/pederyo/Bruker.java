package no.pederyo;

public class Bruker {
    private int id;
    private String navn;
    private String passord;
    private String salt;

    public Bruker(){
        this(0,"","","");
    }

    public Bruker(int id, String navn, String passord, String salt) {
        this.id = id;
        this.navn = navn;
        this.passord = passord;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
