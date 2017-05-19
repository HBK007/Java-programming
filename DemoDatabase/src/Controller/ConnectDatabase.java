/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ListMovieSeller;
import Model.Movie;
import Model.Showtimes;
import Model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nguyen Duc Hai
 */
public class ConnectDatabase {

    public static Connection mConnectDatabase;

    public static Connection ConnectDb(String user, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: " + e);
            System.exit(0);
        }
        try {
            mConnectDatabase = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", user, password);
            return mConnectDatabase;
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        return null;
    }

    public static ArrayList<Showtimes> getShowTime(String nameMovie, String nameThreate) {
        ArrayList<Showtimes> arrShowTime = new ArrayList<>();
        if (mConnectDatabase != null) {
            Statement st;
            ResultSet rs;
            String strIDThreate = "";
            String strIDMovie = "";
            String strSqlIDThreate = "SELECT MACN FROM CHINHANH WHERE TENGOITAT" + " = '" + nameThreate + "'";
            try {
                st = mConnectDatabase.createStatement();
                rs = st.executeQuery(strSqlIDThreate);
                while (rs.next()) {
                    strIDThreate = rs.getString("MACN");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            String strSqlIDMovie = "SELECT MAPHIM FROM PHIM WHERE TENPHIM" + " = '" + nameMovie + "'";
            try {
                st = mConnectDatabase.createStatement();
                rs = st.executeQuery(strSqlIDMovie);
                while (rs.next()) {
                    strIDMovie = rs.getString("MAPHIM");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!strIDMovie.equals("") && !strIDThreate.equals("")) {
                String strSqlShowTime = "SELECT NGAYGIOCHIEU, LOAISUATCHIEU FROM SUATCHIEU "
                        + "WHERE MAPHIM" + " = '" + strIDMovie + "'" + " AND MACN" + " = '" + strIDThreate + "'";

                try {
                    st = mConnectDatabase.createStatement();
                    rs = st.executeQuery(strSqlShowTime);
                    Showtimes showtime;
                    while (rs.next()) {
                        showtime = new Showtimes(rs.getString("NGAYGIOCHIEU"), rs.getString("LOAISUATCHIEU"));
                        arrShowTime.add(showtime);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa đăng nhập");
        }
        return arrShowTime;
    }

    public static ArrayList<Movie> getListMovie() {
        ArrayList<Movie> arrListMovie = new ArrayList<>();
        if (mConnectDatabase != null) {
            Statement st;
            ResultSet rs;
            String strSqlListMovie = "SELECT * FROM PHIM";
            try {
                st = mConnectDatabase.createStatement();
                rs = st.executeQuery(strSqlListMovie);
                Movie movie;
                while (rs.next()) {
                    movie = new Movie(rs.getString("MAPHIM"), rs.getString("TENPHIM"), rs.getString("NAMSX"), rs.getInt("TUOITOITHIEU"),
                            rs.getString("TIMEBEGIN"), rs.getString("TIMEFINISH"), rs.getString("THOILUONG"), rs.getString("NOIDUNG"),
                            rs.getString("BOMTAN"), rs.getString("TINHTRANG"));
                    arrListMovie.add(movie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa đăng nhập.");
        }
        return arrListMovie;
    }

    public static ArrayList<User> getListUser(String nameCustomer) {
        ArrayList<User> arrListUser = new ArrayList<>();
        if (mConnectDatabase != null) {
            ResultSet rs;
            Statement st;
            String strIDCustomer = "";
            float fTotalPoint = 0.0f;
            // get ID from name customer
            String strSqlIDCustomer = "SELECT ID FROM NGUOI WHERE TEN" + " = '" + nameCustomer + "'";
            try {
                st = mConnectDatabase.createStatement();
                rs = st.executeQuery(strSqlIDCustomer);
                while (rs.next()) {
                    strIDCustomer = rs.getString("ID");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            // get infor of customer

            String strSqlListCustomer = "SELECT CMND, DIACHI, DIENTHOAI, EMAIL, NGAYSINH FROM NGUOI WHERE TEN" + " = '" + nameCustomer + "'";
            try {
                st = mConnectDatabase.createStatement();
                rs = st.executeQuery(strSqlListCustomer);
                User user;
                while (rs.next()) {
                    user = new User(nameCustomer, rs.getString("CMND"), rs.getString("DIACHI"), rs.getString("DIENTHOAI"), rs.getString("EMAIL"),
                            rs.getString("NGAYSINH"), 0.0f);
                    arrListUser.add(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // get total point of customer
            String strSqlTotalPoint = "SELECT TONGDIEM FROM KHACHHANG WHERE ID" + " = '" + strIDCustomer + "'";
            try {
                st = mConnectDatabase.createStatement();
                rs = st.executeQuery(strSqlTotalPoint);
                while (rs.next()) {
                    fTotalPoint = Float.valueOf(rs.getString("TONGDIEM"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            // add total point for user
            for (User user : arrListUser) {
                user.setTotalPoint(fTotalPoint);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Bạn chưa đăng nhập.");
        }
        return arrListUser;
    }

    public static ArrayList<ListMovieSeller> getListMovieSeller(String startTime, String endTime) {
        ArrayList<ListMovieSeller> arrListMovieSellers = new ArrayList<>();
        if (mConnectDatabase != null) {
            Statement st;
            ResultSet rs;
            String strSqlListMovie = "SELECT MAPHIM, NGAYGIOCHIEU, SOVEBANDUOC FROM SUATCHIEU";
            try {
                st = mConnectDatabase.createStatement();
                rs = st.executeQuery(strSqlListMovie);
                ListMovieSeller movieSeller;
                while (rs.next()) {
                    movieSeller = new ListMovieSeller(rs.getString("MAPHIM"), "PHIM CHIEU RAP", rs.getString("NGAYGIOCHIEU"), Integer.valueOf(rs.getString("SOVEBANDUOC")));
                    arrListMovieSellers.add(movieSeller);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            // process time of movie
            String strShowTime = "";
            String strShowDate = "";
            for (ListMovieSeller movieSeller : arrListMovieSellers) {
                strShowTime = movieSeller.getTimeShow();
                String[] parts = strShowTime.split("\\s+");
                strShowDate = parts[0];
                movieSeller.setTimeShow(strShowDate);
            }
            // compare time show
            DateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate, endDate, showDate;
            try {
                startDate = outFormat.parse(startTime);
                endDate = outFormat.parse(endTime);
               
                for (int i = 0; i < arrListMovieSellers.size(); i++) {
                    Date tempDate = inFormat.parse(arrListMovieSellers.get(i).getTimeShow());
                     String strTempDate  = outFormat.format(tempDate);
                     showDate = outFormat.parse(strTempDate);
                    if (showDate.before(startDate) || showDate.after(endDate)) {
                        arrListMovieSellers.remove(i);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // process same ID movie
            int size = arrListMovieSellers.size();
            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size - 1; j++) {
                    if (arrListMovieSellers.get(i).getID().equals(arrListMovieSellers.get(j).getID())) {
                        int iTicket = arrListMovieSellers.get(i).getTotalSellTicket();
                        int jTicket = arrListMovieSellers.get(j).getTotalSellTicket();
                        arrListMovieSellers.get(i).setTotalSellTicket(iTicket + jTicket);
                        arrListMovieSellers.remove(j);
                    }
                }
            }
            // find name movie
            String strIDMovie = "";
            String strNameMovie = "";
            String strSqlNameMovie = "";
            for (ListMovieSeller movieSeller : arrListMovieSellers) {
                strIDMovie = movieSeller.getID();
                strSqlNameMovie = "SELECT TENPHIM FROM PHIM WHERE MAPHIM" + " = '" + strIDMovie + "'";
                try {
                    st = mConnectDatabase.createStatement();
                    rs = st.executeQuery(strSqlNameMovie);
                    while (rs.next()) {
                          strNameMovie = rs.getString("TENPHIM");
                    }
                    movieSeller.setNameMovie(strNameMovie);
                } catch (SQLException ex) {
                    Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // sort movie with sell ticket
            Collections.sort(arrListMovieSellers);
        }else{
            JOptionPane.showMessageDialog(null, "Bạn chưa Đăng nhập.");
        }
        return arrListMovieSellers;
    }
}
