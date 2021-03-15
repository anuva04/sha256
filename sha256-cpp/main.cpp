#include <iostream>
#include "sha256.h"
 
using namespace std;
 
int main(int argc, char *argv[])
{
    cout << "Input string:" << endl;
    string input;
    cin >> input;
    // string input = "malaya";
    string output1 = sha256(input);
 
    cout << "sha256('"<< input << "'):" << output1 << endl;
    return 0;
}