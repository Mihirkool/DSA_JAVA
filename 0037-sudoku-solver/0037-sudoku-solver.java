// class Solution {
//     private char[][] board;

//     public void solveSudoku(char[][] board) {
//         this.board = board;
//         helper(0, 0);
//     }

//     private boolean helper(int row, int col) {
//         if (col == 9) {
//             row++;
//             col = 0;
//         }
//         if (row == 9) {
//             return true;
//         }

//         // skip already filled cells
//         if (board[row][col] != '.') {
//             return helper(row, col + 1);
//         }

//         // try numbers 1 to 9
//         for (char i = '1'; i <= '9'; i++) {
//             if (isValid(row, col, i)) {
//                 board[row][col] = i;
//                 if (helper(row, col + 1)) {
//                     return true;
//                 }
//                 board[row][col] = '.';
//             }
//         }
//         return false;
//     }

//     private boolean isValid(int row, int col, char cur) {
//         for (int i = 0; i < 9; i++) {
//             if (board[row][i] == cur) return false;
//             if (board[i][col] == cur) return false;
//         }

//         int[] rowBorder = findSE(row);
//         int[] colBorder = findSE(col);

//         for (int i = rowBorder[0]; i <= rowBorder[1]; i++) {
//             for (int j = colBorder[0]; j <= colBorder[1]; j++) {
//                 if (board[i][j] == cur) return false;
//             }
//         }
//         return true;
//     }

//     private int[] findSE(int coor) {
//         int start = (coor / 3) * 3;
//         return new int[]{start, start + 2};
//     }
// }
class Solution {

    private char[][] board;

    // bitmasks: bit i means digit (i+1) is already used
    private int[] rowMask = new int[9];
    private int[] colMask = new int[9];
    private int[] boxMask = new int[9];

    public void solveSudoku(char[][] board) {
        this.board = board;

        // initialize masks
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') {
                    int d = board[r][c] - '1';
                    setBit(r, c, d);
                }
            }
        }

        backtrack();
    }

    private boolean backtrack() {
        int minCount = 10;
        int targetRow = -1, targetCol = -1;
        int candidateMask = 0;

        // MRV: find cell with minimum possibilities
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    int used = rowMask[r] | colMask[c] | boxMask[boxIndex(r, c)];
                    int possible = (~used) & 0x1FF; // 9 bits
                    int count = Integer.bitCount(possible);

                    if (count == 0) return false;
                    if (count < minCount) {
                        minCount = count;
                        targetRow = r;
                        targetCol = c;
                        candidateMask = possible;
                        if (count == 1) break;
                    }
                }
            }
        }

        // solved
        if (targetRow == -1) return true;

        // try candidates
        while (candidateMask != 0) {
            int bit = candidateMask & -candidateMask;
            int digit = Integer.numberOfTrailingZeros(bit);

            place(targetRow, targetCol, digit);
            if (backtrack()) return true;
            remove(targetRow, targetCol, digit);

            candidateMask ^= bit;
        }
        return false;
    }

    private void place(int r, int c, int d) {
        board[r][c] = (char) ('1' + d);
        setBit(r, c, d);
    }

    private void remove(int r, int c, int d) {
        board[r][c] = '.';
        clearBit(r, c, d);
    }

    private void setBit(int r, int c, int d) {
        rowMask[r] |= (1 << d);
        colMask[c] |= (1 << d);
        boxMask[boxIndex(r, c)] |= (1 << d);
    }

    private void clearBit(int r, int c, int d) {
        rowMask[r] ^= (1 << d);
        colMask[c] ^= (1 << d);
        boxMask[boxIndex(r, c)] ^= (1 << d);
    }

    private int boxIndex(int r, int c) {
        return (r / 3) * 3 + (c / 3);
    }
}

