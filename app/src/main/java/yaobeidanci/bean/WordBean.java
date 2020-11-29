package yaobeidanci.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple word-learning bean
 */
public class WordBean {
    public String word;
    public String pronunciation;
    public String pronunciationPath;
    public String[] explains;
    public String correctExplain;

    public List<WordDetail> leftList;
    public List<WordDetail> rightList;

    {
        leftList = new ArrayList<>();
        rightList = new ArrayList<>();
    }

    public static class WordDetail{
        public String word;
        public String pronunciation;
        public String pronunciationPath;
    }
    public static class RootDetail extends WordDetail{
        public String[] prefixes;
        public String root;
        public String postfixes;
        public String memoryMethod;
        public String exampleEnglish;
        public String exampleChinese;
    }
    public static class ExampleItem{
        public String title;
        public String sentence;
        public String sentenceChinese;
        public String backgroundPath;

        public ExampleItem(String title, String sentence, String sentenceChinese, String backgroundPath) {
            this.title = title;
            this.sentence = sentence;
            this.sentenceChinese = sentenceChinese;
            this.backgroundPath = backgroundPath;
        }
    }
    public static class ExampleSentences extends WordDetail{
        public String explain;
        public List<ExampleItem> list = new ArrayList<>();
    }
    public static class ExtendDetail extends WordDetail{
        public String[] wordLists;
    }
}
