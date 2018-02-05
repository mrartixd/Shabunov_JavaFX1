package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Book {

    private final SimpleStringProperty bookid;
    private final SimpleStringProperty author;
    private final SimpleStringProperty title;
    private final SimpleStringProperty genre;
    private final SimpleStringProperty price;
    private final SimpleStringProperty pubdate;
    private final SimpleStringProperty descripe;
    private final SimpleIntegerProperty count;


    Book(int count, String idb, String authorb, String titleb, String genreb, String priceb, String date, String desc){
        this.count = new SimpleIntegerProperty(count);
        this.bookid = new SimpleStringProperty(idb);
        this.author = new SimpleStringProperty(authorb);
        this.title = new SimpleStringProperty(titleb);
        this.genre = new SimpleStringProperty(genreb);
        this.price = new SimpleStringProperty(priceb);
        this.pubdate = new SimpleStringProperty(date);
        this.descripe = new SimpleStringProperty(desc);
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public String getBookId() {
        return bookid.get();
    }

    public String getAuthor(){
        return author.get();
    }

    public String getTitle(){
        return title.get();
    }

    public String getGenre(){
        return genre.get();
    }

    public String getPrice(){
        return price.get();
    }

    public String getDate(){
        return pubdate.get();
    }

    public String getDesc(){
        return descripe.get();
    }

    public void setBookId(String ids){
        bookid.set(ids);
    }

    public void setAuthor(String authors){
        author.set(authors);
    }

    public void setTitle(String titles){
        title.set(titles);
    }

    public void setGenre(String genres){
        genre.set(genres);
    }

    public void setPrice(String prices){
        price.set(prices);
    }

    public void setDate(String dates){
        pubdate.set(dates);
    }

    public void setDescr(String descrs){
        descripe.set(descrs);
    }


}
