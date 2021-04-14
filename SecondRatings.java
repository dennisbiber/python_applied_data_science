import java.util.ArrayList;

public class SecondRatings {
    
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        this("C:\\Users\\14127\\Documents\\ratedmoviesfull.csv", "C:\\Users\\14127\\Documents\\ratings.csv");
    }

    public SecondRatings(String movieFile, String ratingFile) {
        FirstRatings a = new FirstRatings();
        myMovies = a.loadMovies(movieFile);
        myRaters = a.loadRaters(ratingFile);
    }
    
    public int getMovieSize() {
        return myMovies.size();
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }

    private double getAverageByID(String movieID, int minimalRaters) {
        int count = 0;
        double total = 0;
        for (Rater i : myRaters) {
            double rating = i.getRating(movieID);
            if (rating != -1) {
                count++;
                total += rating;
            }
        }
        if (count >= minimalRaters) return total / count;
        return 0.0;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> ratingList = new ArrayList<>();
        for (Movie i : myMovies) {
            double ave = getAverageByID(i.getID(), minimalRaters);
            if (ave > 0)
                ratingList.add(new Rating(i.getID(), ave));//item is string id?
        }
        return ratingList;
    }
    
    public String getTitle(String movieID) {
        for (Movie i : myMovies) {
            if (i.getID().equals(movieID)) {
                return i.getTitle();
            }
        }
        return "The Movie ID was not found!";
    }
    
    public String getID(String title) {
        for (Movie i : myMovies) {
            if (i.getTitle().equals(title)) {
                return i.getID();
           }
        }
        return "NO SUCH TITLE";
    }
    
    public static void main(String[] args) {
        SecondRatings sr = new SecondRatings("C:\\Users\\14127\\Documents\\ratedmoviesfull.csv", "C:\\Users\\14127\\Documents\ratings.csv");
        System.out.println("---------------test-------------");
        System.out.println(sr.getAverageRatings(2));
    }
    
}