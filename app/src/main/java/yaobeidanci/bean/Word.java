package yaobeidanci.bean;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private String text;
    private List<Explanation> exp;

    public Word(String text){
        this.text=text;
        exp=new ArrayList<Explanation>();
    }

    public String getText(){
        return text;
    }

    public void addExp(Explanation e){
        exp.add(e);
    }

    public List<Explanation> getExp(){ return exp;}

    public String getcExp(){
        String cexp="";
        for (int i=0;i<exp.size();i++){
            cexp+=exp.get(i).getcExp()+"；";
        }
        return cexp;
    }

}