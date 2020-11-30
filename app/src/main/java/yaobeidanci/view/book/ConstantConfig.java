package yaobeidanci.view.book;

import yaobeidanci.view.R;

public class ConstantConfig {
    // 书默认ID
    public static final int CET4 = 1;
    public static final int CET6_MOST = 2;
    public static final int CET6_ALL = 3;
    public static final int KAOYAN = 4;
    public static final int GRE = 5;
    // 根据书ID获取该书的单词总量
    public static int wordTotalNumberById(int bookId) {
        int num = 0;
        switch (bookId) {
            case CET4:
                num = 1162;
                break;
            case CET6_MOST:
                num = 1128;
                break;
            case CET6_ALL:
                num = 2416;
                break;
            case KAOYAN:
                num = 3341;
                break;
            case GRE:
                num = 3078;
                break;
        }
        return num;
    }
    // 根据书ID获取该书的书名
    public static String bookNameById(int bookId) {
        String name = "";
        switch (bookId) {
            case CET4:
                name = "英语四级核心词";
                break;
            case CET6_MOST:
                name = "英语六级核心词";
                break;
            case CET6_ALL:
                name = "六级考纲词汇";
                break;
            case KAOYAN:
                name = "考研必背词汇";
                break;
            case GRE:
                name = "GRE高频词汇";
                break;
        }
        return name;
    }
    // 根据书ID获取该书的图片
    public static int bookPicById(int bookId) {
        int picAddress = 0;
        switch (bookId) {
            case CET4:
                picAddress = R.drawable.cet4;
                break;
            case CET6_MOST:
                picAddress = R.drawable.cet6most;
                break;
            case CET6_ALL:
                picAddress = R.drawable.cetsix;
                break;
            case KAOYAN:
                picAddress = R.drawable.kaoyan;
                break;
            case GRE:
                picAddress = R.drawable.gre;
                break;
        }
        return picAddress;
    }
    // 根据书ID获取该书的简介
    public static String bookIntrById(int bookId) {
        String bookIntr = "";
        switch (bookId) {
            case CET4:
                bookIntr = "精选四级真题词汇";
                break;
            case CET6_MOST:
                bookIntr = "精选六级真题词汇";
                break;
            case CET6_ALL:
                bookIntr = "最新六级考纲词汇";
                break;
            case KAOYAN:
                bookIntr = "考研真题核心词汇";
                break;
            case GRE:
                bookIntr = "GRE核心词汇与精析";
                break;
        }
        return bookIntr;
    }

}
