/*
 * Problem 2 Sell My Pet Food
 */
public class TargetedAd {
  static final int BLANKET = 1;
  static final int JACKET = 2;
  static final int COUPONS = 3;

  public static void main(String[] args)
  {
    //CREATE CSV FILE USING JSOUP (ExtractPage.java)
    ExtractPage amazonCSV = new ExtractPage();
    amazonCSV.prepareCSVfile();


    //Preparing targeted advertisements
    DataCollector data = new DataCollector();
    data.setData("socialMediaPosts.txt", "targetWords.txt");

    System.out.println("Searching for customers...");
    
    //main storage for usernames
    String allUsernames = ""; 

    //specialized storage for usernames
    String allUsernames_blanket = "";
    String allUsernames_jacket = "";
    String allUsernames_coupons = "";


    String dataLine = data.getNextPost(); //pass csv file heading
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
      String targetWordandNum = data.getNextTargetWord();
      String targetWord; //get word only
      int targetNum; //get num only

      //Catch if targetWordandNum = "NONE" or other input
      try {
        targetWord = targetWordandNum.substring(0, targetWordandNum.indexOf(" "));
        targetNum = Integer.valueOf(targetWordandNum.substring(targetWordandNum.length()-1));
      } catch (Exception e) {
        targetWord = "NONE";
        targetNum = 0;
      }

      while (!targetWord.equals("NONE")) {
        if (review.indexOf(targetWord) != -1) {
          //if the target word is included in the review, add name to list
          String product = "";

          //make sure username is not already included in allUsernames
          if (allUsernames.indexOf(username + ",") == -1) {
            allUsernames += username + ", ";

            //prepare lists for specialized advertisements
            if (targetNum == BLANKET) {
              allUsernames_blanket += username + ", ";
              product = "blanket";
            } else if (targetNum == JACKET) {
              allUsernames_jacket += username + ", ";
              product = "jacket";
            } else if (targetNum == COUPONS) {
              allUsernames_coupons += username + ", ";
              product = "coupons";
            }
            
            System.out.println("CUSTOMER FOUND: " + username + " - " + product);
          }
          break;
        }
        targetWordandNum = data.getNextTargetWord();
        try {
          targetWord = targetWordandNum.substring(0, targetWordandNum.indexOf(" "));
          targetNum = Integer.valueOf(targetWordandNum.substring(targetWordandNum.length()-1));
        } catch (Exception e) {
          break; //if there are no more words
        }
      }

      //get next post
      dataLine = data.getNextPost();
    }
    //System.out.println(allUsernames);
    

    //PREPARE ADVERTISEMENT METHODS
    ExtractPage blanketAdvertisement = new ExtractPage("blanketAd.txt");
    String blanketAdString = blanketAdvertisement.getFileContent();
    data.prepareAdvertisement("blanketTargetMarket.txt", allUsernames_blanket, blanketAdString);

    ExtractPage jacketAdvertisement = new ExtractPage("jacketAd.txt");
    String jacketAdString = jacketAdvertisement.getFileContent();
    data.prepareAdvertisement("jacketTargetMarket.txt", allUsernames_jacket, jacketAdString);

    ExtractPage couponsAdvertisement = new ExtractPage("couponsAd.txt");
    String couponsAdString = couponsAdvertisement.getFileContent();
    data.prepareAdvertisement("couponsTargetMarket.txt", allUsernames_coupons, couponsAdString);
  }

}
