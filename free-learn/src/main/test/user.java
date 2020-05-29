
/**
 * @author lpx .
 * @create 2020-03-18-10:39 .
 * @description .
 */


public class user {

    private int id;

    private Double lo;

    private Double la;

    private String loa;

    public user(int id, Double lo, Double la) {
        this.id = id;
        this.lo = lo;
        this.la = la;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLo() {
        return lo;
    }

    public void setLo(Double lo) {
        this.lo = lo;
    }

    public Double getLa() {
        return la;
    }

    public void setLa(Double la) {
        this.la = la;
    }

    public String getLoa() {
        return loa;
    }

    public void setLoa(String loa) {
        this.loa = loa;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", lo=" + lo +
                ", la=" + la +
                ", loa='" + loa + '\'' +
                '}';
    }
}
