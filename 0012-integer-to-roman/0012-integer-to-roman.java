/*
M - 1000
CM - 900
D - 500
CD - 400
XC - 90
L - 50
XL - 40
X - 10
IX - 9
V - 5
IV - 4
I - 1
*/

class Solution {
    public String intToRoman(int num) {
      int[] value = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
      String roman ="";
      String[] romanLetter = {"M","CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV" , "I"};

    for(int i = 0; i<value.length; i++){
        while(num >= value[i]){
            roman = roman + romanLetter[i];
            num = num - value[i];
        }
    }
    return roman;
    }
}
