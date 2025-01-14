/*
 * Problem 2 Sell My Pet Food
 */
public class TargetedAd {

  public static void main(String[] args)
  {
    //CREATE CSV FILE USING JSOUP (ExtractPage.java)
    ExtractPage amazonCSV = new ExtractPage();
    amazonCSV.prepareCSVfile();


    //Preparing targeted advertisements
    DataCollector data = new DataCollector();
    String targetProduct = "blanket"; //CHANGE THIS
    data.setData("socialMediaPosts.txt", targetProduct + "TargetWords.txt");

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

          //make sure username is not already included in allUsernames
          if (allUsernames.indexOf(username + ",") == -1) {
            allUsernames += username + ", ";
            System.out.println("CUSTOMER FOUND: " + username);
          }
          break;
        }
        targetWord = data.getNextTargetWord();
      }

      //get next post
      dataLine = data.getNextPost();
    }
    //System.out.println(allUsernames);
    

    //PREPARE ADVERTISEMENT METHOD

    ExtractPage advertisement = new ExtractPage(targetProduct + "Ad.txt");
    String advertisementStr = advertisement.getFileContent();
    data.prepareAdvertisement(targetProduct + "TargetMarket.txt", allUsernames, advertisementStr);
  }

}
