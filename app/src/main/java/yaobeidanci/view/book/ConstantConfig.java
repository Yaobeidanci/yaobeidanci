package yaobeidanci.view.book;

import yaobeidanci.view.R;

public class ConstantConfig {
    // 书默认ID
    public static final String CET4 = "CET4luan_2";
    public static final String CET6 = "CET6_2";
    public static final String ZHUAN4 = "Level4luan_2";
    public static final String KAOYAN = "KaoYan_2";
    public static final String ZHUAN8 = "Level8luan_2";
    // 根据书ID获取该书的单词总量
    public static int wordTotalNumberById(String bookId) {
        int num = 0;
        switch (bookId) {
            case CET4:
                num = 3739;
                break;
            case CET6:
                num = 2078;
                break;
            case ZHUAN4:
                num = 4025;
                break;
            case KAOYAN:
                num = 4533;
                break;
            case ZHUAN8:
                num = 12197;
                break;
        }
        return num;
    }
    // 根据书ID获取该书的书名
    public static String bookNameById(String bookId) {
        String name = "";
        switch (bookId) {
            case CET4:
                name = "四级英语词汇";
                break;
            case CET6:
                name = "六级英语词汇";
                break;
            case ZHUAN4:
                name = "专四核心词汇";
                break;
            case KAOYAN:
                name = "考研英语词汇";
                break;
            case ZHUAN8:
                name = "专八核心词汇";
                break;
        }
        return name;
    }
    // 根据书ID获取该书的图片
    public static int bookPicById(String bookId) {
        int picAddress = 0;
        switch (bookId) {
            case CET4:
                picAddress = R.drawable.img_cet4luan;
                break;
            case CET6:
                picAddress = R.drawable.img_cet6;
                break;
            case ZHUAN4:
                picAddress = R.drawable.img_level4luan;
                break;
            case KAOYAN:
                picAddress = R.drawable.img_kaoyan;
                break;
            case ZHUAN8:
                picAddress = R.drawable.img_level8luan;
                break;
        }
        return picAddress;
    }
    // 根据书ID获取该书的简介
    public static String bookIntrById(String bookId) {
        String bookIntr = "";
        switch (bookId) {
            case CET4:
                bookIntr = "精选四级真题词汇";
                break;
            case CET6:
                bookIntr = "精选六级真题词汇";
                break;
            case ZHUAN4:
                bookIntr = "最新专四考纲词汇";
                break;
            case KAOYAN:
                bookIntr = "考研英语核心词汇";
                break;
            case ZHUAN8:
                bookIntr = "精选专八核心词汇";
                break;
        }
        return bookIntr;
    }

}
