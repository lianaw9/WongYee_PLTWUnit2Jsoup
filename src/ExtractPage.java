import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//Prepares the socialMediaPosts.txt file using JSoup
public class ExtractPage {
    String filePath; 
    String fileName;

    public ExtractPage() {
        filePath = "src\\amazonPage.txt";
        fileName = "socialMediaPosts.txt";
    }

    public ExtractPage(String fp, String fn) {
        filePath = fp;
        fileName = fn;
    }
    public void prepareCSVfile() {
        try {
            //Prepare socialMediaPosts.txt
            FileManager smp = new FileManager(fileName);
            smp.createFile();
            smp.clearFile();
            smp.write("Reviewer Name, Review, Stars, Date");
            
            // Read the Amazon file content and parse with Jsoup
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            Document doc = Jsoup.parse(fileContent);

            // Extract data from amazon page
            Elements profileNames = doc.select("ul.a-unordered-list.a-nostyle.a-vertical span.a-profile-name");
            Elements starRatings = doc.select("i[data-hook=review-star-rating] span.a-icon-alt");
            Elements review = doc.select("span[data-hook=review-body] span");
            Elements reviewDates = doc.select("span[data-hook=review-date]");
    
            if (profileNames.size() == starRatings.size() && starRatings.size() == review.size() && review.size() == reviewDates.size()) {
                System.out.println("Processing reviews...");

                //iterate through data
                for (int i = 0; i < starRatings.size(); i++) {
                    String profileNameString = profileNames.get(i).text();
                    String starRatingString = starRatings.get(i).text().substring(0, 1);
                    String reviewString = review.get(i).text();

                    //only extract date
                    String dateString = reviewDates.get(i).text();
                    int cut = dateString.indexOf("on");
                    int comma = dateString.indexOf(",");
                    dateString = dateString.substring(cut+3, comma) + dateString.substring(comma+1);
    
                    smp.write(profileNameString + ", \"" + reviewString + "\", " + starRatingString + ", " + dateString); 
                }
            } else {
                System.out.println("An error has occured");
                System.out.println(profileNames.size());
                System.out.println(starRatings.size());
                System.out.println(review.size()); 
                System.out.println(reviewDates.size());
            }
            
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }
}
   

//<i data-hook="review-star-rating" class="a-icon a-icon-star a-star-5 review-rating"><span class="a-icon-alt">5.0 out of 5 stars</span></i>