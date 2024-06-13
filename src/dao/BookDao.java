package dao;

import core.Database;
import entity.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDao {
    private Connection connection;
    private final CarDao carDao;

    public BookDao(){
        this.connection = Database.getInstance();
        this.carDao = new CarDao();
    }

    public ArrayList<Book> selectByQuery(String query) {
        System.out.println(query);
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                bookList.add(this.match(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bookList;
    }

    public ArrayList<Book> findAll(){
        return this.selectByQuery("SELECT * FROM public.book ORDER BY book_id ASC");
    }

    public boolean save(Book book) {
        String query = "INSERT INTO public.book " +
                "(book_car_id, book_name, book_idno, book_mpno, book_mail, book_start_date, book_fnsh_date, book_prc, book_note, book_case) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, book.getCar_id());
            ps.setString(2, book.getName());
            ps.setString(3, book.getIdno());
            ps.setString(4, book.getMpno());
            ps.setString(5, book.getMail());
            ps.setDate(6, Date.valueOf(book.getStrt_date()));
            ps.setDate(7, Date.valueOf(book.getFnsh_date()));
            ps.setInt(8, book.getPrc());
            ps.setString(9, book.getNote());
            ps.setString(10, book.getbCase());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Book match(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setbCase(rs.getString("book_case"));
        book.setCar_id(rs.getInt("book_car_id"));
        book.setName(rs.getString("book_name"));
        book.setStrt_date(rs.getString()"book_start_date");
    }




}