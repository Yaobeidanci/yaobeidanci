package yaobeidanci.view.book;

public class BookItemEntity {
    private int BookId;//书的id
    private String BookName;//书名
    private int BookWordCount;//单词数量
    private int BookImg;//图片
    private String BookIntr;//简介
    public BookItemEntity(int bookId, String bookName, int bookWordCount, int bookImg, String intr) {
        this.BookId=bookId;
        this.BookName=bookName;
        this.BookWordCount=bookWordCount;
        this.BookImg=bookImg;
        this.BookIntr=intr;
    }
    public int getBookId() {
        return BookId;
    }
    public void setBookId(int bookId) {
        this.BookId = bookId;
    }

    public String getBookName() {
        return BookName;
    }
    public void setBookName(String bookName) {
        this.BookName = bookName;
    }

    public int getBookWordNum() {
        return BookWordCount;
    }
    public void setBookWordNum(int bookWordNum) {
        this.BookWordCount = bookWordNum;
    }

    public int getBookImg() { return BookImg; }
    public void setBookImg(int bookImg) { this.BookImg = bookImg; }

    public String getBookIntr() {
        return BookIntr;
    }
    public void setBookIntr(String bookIntr) {
        this.BookIntr = bookIntr;
    }
}
