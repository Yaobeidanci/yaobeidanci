package yaobeidanci.bean;

/**
 * Sentence bean
 */
public class SentenceObject{
    public String sentence;
    public String translation;
    public String origin_title;
    public int backgroundId;
    public int fromImageId;
    public String sentence_id;


    public String getSentence(){
        return sentence;
    }

    public String getSentence_id(){
        return sentence_id;
    }

    public String getOrigin_title(){
        return origin_title;
    }

    public int getBackgroundId(){
        return backgroundId;
    }

    public int getFromImageId(){
        return fromImageId;
    }

    public void init() {
        sentence = "";
        translation = "";
        origin_title = "";
        sentence_id = "";
    }
}

