/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


/**
 *
 * @author Nguyen Duc Hai
 */
public class ListMovieSeller implements Comparable<ListMovieSeller>{
    private String ID;
    private String nameMovie;
    private String timeShow;
    private int totalSellTicket;

    public ListMovieSeller(String ID, String nameMovie, String timeShow, int totalSellTicket) {
        this.ID = ID;
        this.nameMovie = nameMovie;
        this.timeShow = timeShow;
        this.totalSellTicket = totalSellTicket;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public String getTimeShow() {
        return timeShow;
    }

    public void setTimeShow(String timeShow) {
        this.timeShow = timeShow;
    }

    public int getTotalSellTicket() {
        return totalSellTicket;
    }

    public void setTotalSellTicket(int totalSellTicket) {
        this.totalSellTicket = totalSellTicket;
    }

    @Override
    public int compareTo(ListMovieSeller compareMovie) {
        int compareTotalTicketSell = ((ListMovieSeller)compareMovie).getTotalSellTicket();
        return compareTotalTicketSell - this.getTotalSellTicket();
    }
    
}
