class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int ht = triangle.size();
        int[][] dp = new int[ht + 1][ht + 1];

        for (int lev = ht - 1; lev >= 0; lev--) {
            for (int i = 0; i <= lev; i++) {
                dp[lev][i] = triangle.get(lev).get(i) +
                             Math.min(dp[lev + 1][i], dp[lev + 1][i + 1]);
            }
        }

        return dp[0][0];
    }
}