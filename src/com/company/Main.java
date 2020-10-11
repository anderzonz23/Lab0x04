package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Main {
    public static long[] fibTable;
    public static void main(String[] args) {
        long before,after,total,totalTotal,nothing,nothingTotal = 0;
        totalTotal=0;

        for(int i=1; i<=10000; i++) {
            before = getCpuTime();
            after = getCpuTime();
            nothingTotal += (after - before);
        }
        nothing = nothingTotal/10000;

        long y,x = 0;
        for(x=1; x<=100; x++){
            for(int i=1; i<=100; i++) {
                before = getCpuTime();
                //y = fibRecur(x);
                //y = fibCache(x);
                //y = fibLoop(x);
                y = fibMatrix(x);
                after = getCpuTime();
                totalTotal += (after - before);
            }
            total = totalTotal/10 - nothing;
            //System.out.println(x + " Fib Number is: " + y + " time was " + total);
            System.out.println(total);
        }

    }

    public static long fibRecur(long x){
        if(x==0 || x==1)
            return x;
        else
            return (fibRecur(x-1) + fibRecur(x-2));
    }

    public static long fibCache(long x){
        fibTable = new long[(int)x+1];


        return fibCacheHelper(x);
    }

    public static long fibCacheHelper(long x){
        if(x==0 || x==1)
            return x;
        else if(fibTable[(int)x] != 0)
            return fibTable[(int)x];
        else {
            fibTable[(int)x] = fibCacheHelper(x - 1) + fibCacheHelper(x - 2);
            return fibTable[(int)x];
        }

    }

    public static long fibLoop(long x){
        long A = 0;
        long B = 1;
        long next = 0;
        if(x<2)
            return x;

        for(int i=2; i<=x; i++){
            next = A+B;
            A = B;
            B = next;
        }

        return B;
    }

    public static long fibMatrix(long x){
        long M[][] = new long[][]{{1,1},{1,0}};
        if(x == 0)
            return x;
        powerMatrix(M,x-1);

        return M[0][0];
    }

    public static void powerMatrix(long N[][], long x){
        long P[][] = new long[][]{{1,1},{1,0}};

        for(int i=2; i<=x; i++){
            multiplyMatrix(N,P);
        }
    }

    public static void multiplyMatrix(long N[][], long P[][]){
        long A = N[0][0] * P[0][0] + N[0][1] * P[1][0];     //calculate top left matrix index
        long B = N[0][0] * P[0][1] + N[0][1] * P[1][1];     //calculate top right matrix index
        long C = N[1][0] * P[0][0] + N[1][1] * P[1][0];     //calculate bottom left matrix index
        long D = N[1][0] * P[0][1] + N[1][1] * P[1][1];     //calculate bottom right matrix index

        N[0][0] = A;
        N[0][1] = B;
        N[1][0] = C;
        N[1][1] = D;
    }

    // Get CPU time in nanoseconds since the program(thread) started.
    /** from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime **/
    public static long getCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;
    }
}
