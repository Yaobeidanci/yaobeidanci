package yaobeidanci.bean;

public class Explanation {
    private String cExp;
    private String eExp;

    public Explanation(String cExp, String eExp){
        this.cExp=cExp;
        this.eExp=eExp;
    }

    public String getcExp() {
        return cExp;
    }

    public String geteExp() {
        return eExp;
    }
}
