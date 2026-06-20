//Covid Thwarter
//This is about thwarting covid and preventing it from spreading. Here, we'll
//look at whether a vaccine mandate is needed, by looking at the rates of weak
//immune systems, how easily covid spreads, how effective the vaccinations are,
//and other things.
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <ctype.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <fcntl.h>
double Run_Simulation_Thing(double a, double b, double c, double d, double f, double g, double h, double i, double j, double k, double l, double m, double n, double o);
double Power_Thing(double f) {
        double s = f;
        if (s == 0.0) { return 0.0; }
        if (s < 0.0) { s = -1.0 * s; }
        int i = 0;
        if (s < 1.0) {
                while (s < 1.0) {
                        i--;
                        s = 2.0 * s;
                }
        } else {
                while (s >= 1.0) {
                        s = s / 2.0;
                        i++;
                }
                s = s * 2.0;
                i--;
        }
        int b = 0;
        if (i >= 0) {
                b = i % 200;
        } else if ((-1 * i) % 200 != 0) {
                b = 200 - ((-1 * i) % 200);
        }
        i = i - b;
        while (b > 0) {
                b--;
                s = s * 2.0;
        }
        double z0 = 1.0;
        double z1 = 2.0;
        b = 52;
        while (b > 0) {
                b--;
                double z2 = (z0 + z1) / 2.0;
                double z3 = z2 * z2;
                z3 = z3 * z2;
                z3 = z3 * z3;
                z3 = z3 * z3;
                z3 = z3 * z3;
                z3 = z3 * z2;
                z3 = z3 * z3;
                z3 = z3 * z3;
                z3 = z3 * z3;
                if (s >= z3) {
                        z0 = z2;
                } else {
                        z1 = z2;
                }
        }
        while (i > 0) {
                i = i - 200;
                z0 = z0 * 2.0;
        }
        while (i < 0) {
                i = i + 200;
                z0 = z0 / 2.0;
        }
        return z0;
}
int main(int argc, char* argv[]) {
        double Initial_Fraction_With_Covid = 0.01;
        double Initial_Fraction_Antivax = 0.05;
        double Antivax_Spread_Rate_If_Vaccinated = 0.03;
        double Antivax_Spread_Rate_If_Unvaccinated = 0.01;
        double Happiness_From_Thwarting_Covid = 100.0;
        double Weak_Immune_System_Rate = 0.05;
        double Risk_Of_Not_Finding_Vaccination_Booth_If_Wanting_Vaccination = 0.1;
        double Risk_Of_Avoiding_Mandate_If_Antivax = 0.2;
        double Covid_Spread_Rate_Vaccinated = 0.04;
        double Covid_Spread_Rate_Unvaccinated = 0.7;
        double Happiness_Of_Being_Vaccinated_When_We_Want_This = 30.0;
        double Sadness_Of_Not_Being_Vaccinated_When_We_Want_This = 15.0;
        double Sadness_Of_Being_Vaccinated_When_We_Dont_Want_This = 13.0;
        double Happiness_Of_Not_Being_Vaccinated_When_We_Dont_Want_This = 4.0;
        double x = Run_Simulation_Thing(Initial_Fraction_With_Covid, Initial_Fraction_Antivax, Antivax_Spread_Rate_If_Vaccinated, Antivax_Spread_Rate_If_Unvaccinated, Happiness_From_Thwarting_Covid, Weak_Immune_System_Rate, 1.0 - Power_Thing(Risk_Of_Not_Finding_Vaccination_Booth_If_Wanting_Vaccination), 1.0 - Power_Thing(Risk_Of_Avoiding_Mandate_If_Antivax), Covid_Spread_Rate_Vaccinated, Covid_Spread_Rate_Unvaccinated, Happiness_Of_Being_Vaccinated_When_We_Want_This, Sadness_Of_Not_Being_Vaccinated_When_We_Want_This, Sadness_Of_Being_Vaccinated_When_We_Dont_Want_This, Happiness_Of_Not_Being_Vaccinated_When_We_Dont_Want_This); //with mandate
        double y = Run_Simulation_Thing(Initial_Fraction_With_Covid, Initial_Fraction_Antivax, Antivax_Spread_Rate_If_Vaccinated, Antivax_Spread_Rate_If_Unvaccinated, Happiness_From_Thwarting_Covid, Weak_Immune_System_Rate, 1.0 - Power_Thing(Risk_Of_Not_Finding_Vaccination_Booth_If_Wanting_Vaccination), 0.0, Covid_Spread_Rate_Vaccinated, Covid_Spread_Rate_Unvaccinated, Happiness_Of_Being_Vaccinated_When_We_Want_This, Sadness_Of_Not_Being_Vaccinated_When_We_Want_This, Sadness_Of_Being_Vaccinated_When_We_Dont_Want_This, Happiness_Of_Not_Being_Vaccinated_When_We_Dont_Want_This); //without mandate
        if (x < y) {
                printf("We will not have a vaccination mandate\n");
        } else {
                printf("Vaccinations will be mandated");
        }
        return 0;
}
double Run_Simulation_Thing(double a, double b, double c, double d, double f, double g, double h, double i, double j, double k, double l, double m, double n, double o) {
        double c1 = 0.0;                                //Weak immune system, vaccinated, has covid, antivax
        double c2 = 0.0;                                //Weak immune system, vaccinated, has covid, pro vaccine
        double c3 = 0.0;                                //Weak immune system, vaccinated, doesn't have covid, antivax
        double c4 = 0.0;                                //Weak immune system, vaccinated, doesn't have covid, pro vaccine
        double c5 = g * a * b;                          //Weak immune system, unvaccinated, has covid, antivax
        double c6 = g * a * (1.0 - b);                  //Weak immune system, unvaccinated, has covid, pro vaccine
        double c7 = g * (1.0 - a) * b;                  //Weak immune system, unvaccinated, doesn't have covid, antivax
        double c8 = g * (1.0 - a) * (1.0 - b);          //Weak immune system, unvaccinated, doesn't have covid, pro vaccine
        double c9 = 0.0;                                //Strong immune system, vaccinated, has covid, antivax
        double c10 = 0.0;                               //Strong immune system, vaccinated, has covid, pro vaccine
        double c11 = 0.0;                               //Strong immune system, vaccinated, doesn't have covid, antivax
        double c12 = 0.0;                               //Strong immune system, vaccinated, doesn't have covid, pro vaccine
        double c13 = (1.0 - g) * a * b;                 //Strong immune system, unvaccinated, has covid, antivax
        double c14 = (1.0 - g) * a * (1.0 - b);         //Strong immune system, unvaccinated, has covid, pro vaccine
        double c15 = (1.0 - g) * (1.0 - a) * b;         //Strong immune system, unvaccinated, doesn't have covid, antivax
        double c16 = (1.0 - g) * (1.0 - a) * (1.0 - b); //Strong immune system, unvaccinated, doesn't have covid, pro vaccine
        int p = 200;
        double happiness = 0.0;
        while (p > 0) {
                //antivax ideas spread
                p--;
                double g0 = (c1 + c3 + c9 + c11) * c + (c2 + c4 + c10 + c12) * d;
                c1 = c1 + (c2 * g0);
                c2 = c2 * (1.0 - g0);
                c3 = c3 + (c4 * g0);
                c4 = c4 * (1.0 - g0);
                c5 = c5 + (c6 * g0);
                c6 = c6 * (1.0 - g0);
                c7 = c7 + (c8 * g0);
                c8 = c8 * (1.0 - g0);
                c9 = c9 + (c10 * g0);
                c10 = c10 * (1.0 - g0);
                c11 = c11 + (c12 * g0);
                c12 = c12 * (1.0 - g0);
                c13 = c13 + (c14 * g0);
                c14 = c14 * (1.0 - g0);
                c15 = c15 + (c16 * g0);
                c16 = c16 * (1.0 - g0);
                //covid spreads
                g0 = c1 + c2 + c5 + c6 + c9 + c10 + c13 + c14;
                double g1 = g0 * k;
                g0 = g0 * j;
                c1 = c1 + c3 * g1;
                c2 = c2 + c4 * g1;
                c3 = c3 * (1.0 - g1);
                c4 = c4 * (1.0 - g1);
                c5 = c5 + c7 * g1;
                c6 = c6 + c8 * g1;
                c7 = c7 * (1.0 - g1);
                c8 = c8 * (1.0 - g1);
                c9 = c9 + c11 * g0;
                c10 = c10 + c12 * g0;
                c11 = c11 * (1.0 - g0);
                c12 = c12 * (1.0 - g0);
                c13 = c13 + c15 * g1;
                c14 = c14 + c16 * g1;
                c15 = c15 * (1.0 - g1);
                c16 = c16 * (1.0 - g1);
                //getting vaccinated
                c1 = c1 + c5 * i;
                c2 = c2 + c6 * h;
                c3 = c3 + c7 * i;
                c4 = c4 + c8 * h;
                c5 = c5 * (1.0 - i);
                c6 = c6 * (1.0 - h);
                c7 = c7 * (1.0 - i);
                c8 = c8 * (1.0 - h);
                c9 = c9 + c13 * i * (1.0 - j);
                c10 = c10 + c14 * h * (1.0 - j);
                c11 = c11 + c15 * i + c13 * i * j;
                c12 = c12 + c16 * h + c14 * h * j;
                c13 = c13 * (1.0 - i);
                c14 = c14 * (1.0 - h);
                c15 = c15 * (1.0 - i);
                c16 = c16 * (1.0 - h);
                //calculating happiness from thwarting covid in people with weak immune systems each day
                happiness = happiness + f * (c1 + c2 + c5 + c6);
        }
        //add happiness at end
        happiness = happiness + f * (c1 + c2 + c5 + c6 + c9 + c10 + c13 + c14);
        happiness = happiness + l * (c2 + c4 + c10 + c12) + o * (c5 + c7 + c13 + c15) - m * (c6 + c8 + c14 + c16) - n * (c1 + c3 + c9 + c11);
        return happiness;
}