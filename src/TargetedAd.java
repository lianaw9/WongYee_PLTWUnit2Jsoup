/*
 * Problem 2 Sell My Pet Food
 */
public class TargetedAd {

  public static void main(String[] args)
  {
    /* your code here */
    //CREATE CSV FILE USING JSOUP (ExtractPage.java)
    ExtractPage amazonCSV = new ExtractPage();
    amazonCSV.prepareCSVfile();
    
    DataCollector data = new DataCollector();
    data.setData("socialMediaPosts.txt", "targetWords.txt");

    System.out.println("Searching for customers...");
    
    String allUsernames = "";
    String dataLine = data.getNextPost(); //csv file heading
    dataLine = data.getNextPost();
    while (!dataLine.equals("NONE")) {
      //System.out.println(dataLine);

      //get review 
      int reviewStart = dataLine.indexOf("\"");
      int reviewEnd = dataLine.indexOf("\"", reviewStart+1);
      String review = dataLine.substring(reviewStart+1, reviewEnd);
      //System.out.println(review);

      //get username
      int usernameEnd = dataLine.indexOf(",");
      String username = dataLine.substring(0, usernameEnd);
      
      
      //check if review includes a target word
      String targetWord = data.getNextTargetWord();
      while (!targetWord.equals("NONE")) {
        if (review.indexOf(targetWord) != -1) {
          //if the target word is included in the review, add name to list
          allUsernames += username + ", ";
          System.out.println("CUSTOMER FOUND: " + username);
          break;
        }
        targetWord = data.getNextTargetWord();
      }

      //get next post
      dataLine = data.getNextPost();
    }
    //System.out.println(allUsernames);
    
    data.prepareAdvertisement("BlanketAd.txt", allUsernames, "So It sounds like you like soft and warm items such as this scarf which you bought........You should buy my epic blanket which will keep you very warm.");
  }

}
