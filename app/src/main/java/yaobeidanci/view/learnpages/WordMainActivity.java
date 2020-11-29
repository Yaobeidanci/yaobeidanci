package yaobeidanci.view.learnpages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import yaobeidanci.bean.WordBean;
import yaobeidanci.view.R;

public class WordMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* ********************* *
         * Test data
         * ********************* */
        WordBean wordBean = new WordBean();
        wordBean.word = "recognize";
        wordBean.pronunciation = "[ˈrekəɡnaɪz]";
        wordBean.explains = new String[]{"vt.认出；承认；（正式）认可；公认", "n.录音带，录影带",
                "vt.使和谐一致；使和好；妥协", "vt. vi.意识到"};
        WordBean.ExampleSentences sentences = new WordBean.ExampleSentences();
        sentences.word = "recognize";
        sentences.pronunciation = "[ˈrekəɡnaɪz]";
        sentences.explain = "vt.认出；识别\nto know or remember sb/sth because you have seen, heard or experienced them before";
        sentences.list.add(new WordBean.ExampleItem("科学美国人", "We recognize our friends' face",
                "我们能认出朋友的样子","0"));
        sentences.list.add(new WordBean.ExampleItem("少儿动漫万用对白", "Why wouldn't I recognize my own parents",
                "我怎么会不认识自己的父母？","0"));
        wordBean.rightList.add(sentences);
        WordBean.ExampleSentences sentences2 = new WordBean.ExampleSentences();
        sentences2.word = "recognize";
        sentences2.pronunciation = "[ˈrekəɡnaɪz]";
        sentences2.explain = "vt.认出\nto know or remember sb/sth because you have seen";
        sentences2.list.add(new WordBean.ExampleItem("科学美国", "We recognize our",
                "我们能认出朋友的","0"));
        sentences2.list.add(new WordBean.ExampleItem("少儿动漫", "Why wouldn't I ",
                "我怎么会不认识？","0"));
        wordBean.rightList.add(sentences2);

        WordBean.RootDetail rootDetail = new WordBean.RootDetail();
        rootDetail.word = "recognize";
        rootDetail.pronunciation = "[ˈrekəɡnaɪz]";
        rootDetail.exampleEnglish = "I recognized her by her red hair";
        rootDetail.exampleChinese = "我从她的红头发认出了他";

        rootDetail.memoryMethod = "以前就知道，遇到后再知道";
        rootDetail.root = "gn= to know知道";
        rootDetail.postfixes ="-ize=构成动词";
        rootDetail.prefixes = new String[]{"re=again,back","co-com-=together"};

        wordBean.leftList.add(rootDetail);

        WordBean.ExtendDetail extendDetail = new WordBean.ExtendDetail();
        extendDetail.word = "recognize";
        extendDetail.pronunciation = "[ˈrekəɡnaɪz]";
        extendDetail.wordLists = new String[]{"recognize","recognition", "recognized", "unrecognized"};

        wordBean.leftList.add(extendDetail);

        /* ********************* *
         * Main
         * ********************* */
        setContentView(R.layout.layout_main_learn_page);

        OuterViewPager outerViewPager = findViewById(R.id.outerPageView);
        VerticalButtonViewPager verticalButtonViewPager = findViewById(R.id.verticalPageView);
        outerViewPager.connected = verticalButtonViewPager;
        verticalButtonViewPager.connected = outerViewPager;
        outerViewPager.setWordBean(wordBean);
        // By default, there are only 2 view remaining in the viewpager, one is show and the other is off screen
        // So set the limit to 2, to make all the 3 page remain
        verticalButtonViewPager.setOffscreenPageLimit(2);
        // Set the initial page to the page in middle
        outerViewPager.setCurrentItem(1);
        verticalButtonViewPager.setCurrentItem(1);

    }


}