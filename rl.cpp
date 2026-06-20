#include <iostream>

using namespace std;

double Q[4][2] = {{0.0,0.0},{0.0,0.0},{0.0,0.0},{0.0,0.0}};
int counts[4] = {0,0,0,0};

double maxQ(int sprime) {
    return max(Q[sprime][0], Q[sprime][1]);
}

int selectMyAction(int s) {
    if (s == -1) {   // first turn, pick randomly
        cout << "picking a random action" << endl;
        return rand() % 2;
    }

    // pick randomly with probability .1
    double explorationRate = 100.0 / (counts[s] + 1);
    if (counts[s] > 9)
        explorationRate = 10;
    cout << "explorationRate: " << explorationRate << endl;
    if ((rand() % 100) < explorationRate) {
        cout << "exploring" << endl;
        return rand() % 2;
    }

     cout << "picking the action greedily" << endl;
    // pick the action greedily based on current Q values
    if (Q[s][0] > Q[s][1])
        return 0;
    else if (Q[s][0] < Q[s][1])
        return 1;
    else
        return rand() % 2;
}

// tit-for-tat
// int selectTheirAction(int s) {
//     if (s < 2)
//         return 0; // reciprocate cooperation

//     // otherwise, reciprocate defection

//     return 1;
// }

// random
int selectTheirAction(int s) {
    return rand() % 2;
}

string stateStr(int s) {
    switch (s) {
        case 0: return "00";
        case 1: return "01";
        case 2: return "10";
        case 3: return "11";
    }

    return "xx";
}

void printQValues(int s) {
    cout << "Qvalues: " << endl;
    for (int i = 0; i < 4; i++) {
        if (s == i)
            cout << "  *";
        else
            cout << "   ";
        cout << "State " << stateStr(i) << ": " << Q[i][0] << ", " << Q[i][1] << "  (" << counts[i] << ")" << endl;
    }
    cout << endl;
}

int main() {
    srand(time(NULL));

    double Payoffs[2][2] = {{3, 0},{5,1}};

    int s = -1, sprime;
    double reward;
    int round = 0;

    double alpha = 0.8;
    double gamma = 0.95;
    bool printQs = true;

    while (round < 100000) {

        cout << "\n\n\nStarting round " << round << " from state " << stateStr(s) << endl << endl;;

        printQValues(s);

        int myAction = selectMyAction(s);
        int theirAction = selectTheirAction(s);


        sprime = myAction * 2 + theirAction;
        reward = Payoffs[myAction][theirAction];

        cout << "   picked " << myAction << endl;
        cout << "Other player picked " << theirAction << endl;
        cout << "Received a reward of: " << reward << endl;
        cout << "The new state is " << sprime << endl;

        if (s >= 0) {
            alpha = 10.0 / (counts[s] + 10.0);
            if (alpha < 0.01)
                alpha = 0.01;
            Q[s][myAction] = (1.0 - alpha) * Q[s][myAction] + alpha * (reward + gamma * maxQ(sprime));
            counts[s] ++;
        }

        cout << "The updated Q-values are:" << endl;
        printQValues(s);

        round ++;
        s = sprime;

        if (printQs) {
            string nothing;
            cout << "\nEnter string to continue ... ";
            cin >> nothing;
            if (nothing == "stop")
                printQs = false;
        }
    }


}