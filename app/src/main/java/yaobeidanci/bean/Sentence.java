package yaobeidanci.bean;

public class Sentence {
    public String id;
    public  String context;
    public  String from;
    public int imageId;
    public int fromImageId;

    public Sentence(String context,String from,String id,int imageId,int fromImageId){
        this.context=context;
        this.from=from;
        this.id=id;
        this.imageId=imageId;
        this.fromImageId=fromImageId;
    }

    public String getContext(){
        return context;
    }

    public String getId(){
        return id;
    }

    public int getImageId(){
        return imageId;
    }

    public int getFromImageId() { return fromImageId; }

    public String getFrom() { return from; }
}


