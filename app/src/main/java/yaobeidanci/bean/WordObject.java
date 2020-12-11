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
}

