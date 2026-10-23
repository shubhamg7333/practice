class Solution {
    public boolean checkIfPangram(String sentence) {
     String str = "abcdefghijklmnopqrstuvwxyz";
     for(int i = 0; i<=25;i++){
        boolean isfound = false;
        for(int j = 0; j<sentence.length();j++){
         if(str.charAt(i)==sentence.charAt(j) ){
         isfound = true;
         break;
         }
        }
         if(  isfound == false)
    return false;
     }
   return true;


    }
}