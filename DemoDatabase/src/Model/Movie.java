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
public class Movie {
    private String ID;
    private String nameMovie;
    private String yearOfManufacture;
    private int minYearOfAge;
    private String timeShowStart;
    private String timeShowEnd;
    private String movieDuration;
    private String movieContent;
    private String movieBlockbuster;
    private String movieState;

    public Movie() {
    }

    public Movie(String ID, String nameMovie, String yearOfManufacture, int minYearOfAge, String timeShowStart, String timeShowEnd, String movieDuration, String movieContent, String movieBlockbuster, String movieState) {
        this.ID = ID;
        this.nameMovie = nameMovie;
        this.yearOfManufacture = yearOfManufacture;
        this.minYearOfAge = minYearOfAge;
        this.timeShowStart = timeShowStart;
        this.timeShowEnd = timeShowEnd;
        this.movieDuration = movieDuration;
        this.movieContent = movieContent;
        this.movieBlockbuster = movieBlockbuster;
        this.movieState = movieState;
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

    public String getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(String yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public int getMinYearOfAge() {
        return minYearOfAge;
    }

    public void setMinYearOfAge(int minYearOfAge) {
        this.minYearOfAge = minYearOfAge;
    }

    public String getTimeShowStart() {
        return timeShowStart;
    }

    public void setTimeShowStart(String timeShowStart) {
        this.timeShowStart = timeShowStart;
    }

    public String getTimeShowEnd() {
        return timeShowEnd;
    }

    public void setTimeShowEnd(String timeShowEnd) {
        this.timeShowEnd = timeShowEnd;
    }

    public String getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(String movieDuration) {
        this.movieDuration = movieDuration;
    }

    public String getMovieContent() {
        return movieContent;
    }

    public void setMovieContent(String movieContent) {
        this.movieContent = movieContent;
    }

    public String getMovieBlockbuster() {
        return movieBlockbuster;
    }

    public void setMovieBlockbuster(String movieBlockbuster) {
        this.movieBlockbuster = movieBlockbuster;
    }

    public String getMovieState() {
        return movieState;
    }

    public void setMovieState(String movieState) {
        this.movieState = movieState;
    }

    
    
    
       
}
