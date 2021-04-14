import java.util.ArrayList;
import java.util.Collections;

public class FourthRatings {

    private double dotProduct(Rater me, Rater r) {
        double dp = 0;
        ArrayList<String> memovieid = me.getItemsRated();
        for (String id : memovieid) {
            if (r.getItemsRated().contains(id)) {
                dp += (me.getRating(id) - 5) * (r.getRating(id) - 5);
            }
        }
        return dp;
    }
 
    private ArrayList<Rating> getSimilarities(String raterId) {
        ArrayList<Rating> simiList = new ArrayList<>();
        ArrayList<Rater> raters = RaterDatabase.getRaters();
        for (Rater r : raters) {
            if (!r.getID().equals(raterId)) {
                double dotProduct = dotProduct(RaterDatabase.getRater(raterId), r);
                if (dotProduct > 0) {
                    simiList.add(new Rating(r.getID(), dotProduct));
                }
            }
        }
        Collections.sort(simiList);
        Collections.reverse(simiList);
        return simiList;
    }

    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> ratingList = new ArrayList<>();
        Filter trueFilter = new TrueFilter();
        for (String j : MovieDatabase.filterBy(trueFilter)) {
            double ave = 0;
            ArrayList<Rating> simiList = getSimilarities(raterID);
            int count = 0;
            double total = 0;
            int simiweighttotal = 0;
            for (int i = 0; i < numSimilarRaters; i++) {
                double rating = RaterDatabase.getRater(simiList.get(i).getItem()).getRating(j);
                if (rating != -1) {
                    count++;
                    total += rating * simiList.get(i).getValue();
                    simiweighttotal += simiList.get(i).getValue();
                }
            }
            if (count >= minimalRaters)
                ave = total / count;

            if (ave > 0)
                ratingList.add(new Rating(j, ave));
        }
        Collections.sort(ratingList);
        Collections.reverse(ratingList);
        
        return ratingList;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimalRaters, Filter f) {
        ArrayList<Rating> ratingList = new ArrayList<>();
        Filter trueFilter = new TrueFilter();
        ArrayList<Rating> simiListTest = getSimilarities(raterID);
        for (String j : MovieDatabase.filterBy(trueFilter)) {
            if (f.satisfies(j)) {
                double ave = 0;
                ArrayList<Rating> simiList = getSimilarities(raterID);
                int count = 0;
                double total = 0;
                double simiweighttotal = 0;
                for (int i = 0; i < numSimilarRaters; i++) {
                    double rating = RaterDatabase.getRater(simiList.get(i).getItem()).getRating(j);
                    if (rating != -1) {
                        count++;
                        total += rating * simiList.get(i).getValue();
                        simiweighttotal += simiList.get(i).getValue();
                        
                        //System.out.println(count + " : " + "id = " + simiList.get(i).getItem() + " rating " + rating + " ave " + total);
                        
                    }
                }
                if (count >= minimalRaters)
                    ave = total / count;
                
                if (ave > 0)
                    ratingList.add(new Rating(j, ave));
            }
        }
        Collections.sort(ratingList);
        Collections.reverse(ratingList);
        
        return ratingList;
    }
    
    public static void main(String[] args) {
        MovieDatabase.initialize("C:\\Users\\14127\\Documents\\ratedmoviesfull.csv");
        RaterDatabase.initialize("C:\\Users\\14127\\Documents\\ratings.csv");
        FourthRatings sr = new FourthRatings();
        System.out.println("---------------test-------------");
        System.out.println(sr.getSimilarRatings("2", 3, 0));
        
    }
}