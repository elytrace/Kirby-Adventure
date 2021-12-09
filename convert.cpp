#include <bits/stdc++.h>
using namespace std;

int main() {
    ifstream fin;
    fin.open("Resources/Maps/map5.txt", ios::in);

    int row, col;
    fin >> row >> col;
    int** matrix = new int*[row];
    for(int i = 0; i < row; i++) {
        matrix[i] = new int[col];
        for(int j = 0; j < col; j++) {
            fin >> matrix[i][j];
        }
    }

    fin.close();

    ofstream fout;
    fout.open("Resources/Maps/map1.txt", ios::out);

    for(int i = 1; i < row-1; i++) 
        for(int j = 1; j < col-1; j++) {
            if(matrix[i][j] == 13) {
                if(matrix[i-1][j] == 0) {
                    matrix[i][j] = 14;
                    matrix[i-1][j] = 2;
                }
                if(matrix[i+1][j] == 0)
                    matrix[i+1][j] = 11;
                if(matrix[i][j-1] == 0)
                    matrix[i][j-1] = 8;
                if(matrix[i][j+1] == 0)
                    matrix[i][j+1] = 9;
            }
        }
            
    for(int i = 1; i < row-1; i++) 
        for(int j = 1; j < col-1; j++) {
            if(matrix[i][j] == 14) {
                if(matrix[i+1][j] == 0)
                    matrix[i+1][j] = 11;

                if(matrix[i-1][j] == 0)
                    matrix[i-1][j] = 2;

                if(matrix[i][j-1] == 0)
                    matrix[i][j-1] = 6;

                if(matrix[i][j+1] == 0)
                    matrix[i][j+1] = 7;

                if(matrix[i-1][j-1] == 0 and matrix[i][j-1] != 13)
                    matrix[i-1][j-1] = 1;

                if(matrix[i-1][j+1] == 0 and matrix[i][j+1] != 13)
                    matrix[i-1][j+1] = 3;

                if(matrix[i-1][j-1] == 13)
                    matrix[i-1][j] = 5;
                if(matrix[i-1][j+1] == 13)
                    matrix[i-1][j] = 4;
            }
        }

    fout << row << endl << col << endl;
    for(int i = 0; i < row; i++) {
        for(int j = 0; j < col; j++) {
            fout << matrix[i][j] << " ";
        }
        fout << endl;
    }

    cout << "complete" << endl;
    fout.close();
    return 0;
}