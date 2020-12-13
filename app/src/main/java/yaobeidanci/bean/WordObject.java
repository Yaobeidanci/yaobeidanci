package yaobeidanci.bean;

import java.util.List;

/**
 * Word bean
 */
public class WordObject {
    public String word;
    public String phonetic_uk;
    public String phonetic_us;
    public String category;
    public List<SentenceObject> sentences;
    public List<RelateWordObject> relate_words;
    public List<WordExplanationObject> explains;
    public List<PhraseObject> phrases;
    public String remember_method;
    public List<WordExplanationObject> questions;
    public int correct_index;

    public String phrases_label;
    public String relate_words_label;
    public String remember_method_label;
    public String sentences_label;

    public int word_id;

    public String getText(){
        return word;
    }

    public List<WordExplanationObject> getExp(){ return explains;}

    public String getcExp(){
        String cexp="";
        for (int i=0;i<explains.size();i++){
            cexp+=explains.get(i).getExplain_c()+"；";
        }
        return cexp;
    }

}
