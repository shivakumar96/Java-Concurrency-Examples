package org.example.problems;

/**
 * Singleton implementation;
 */
public class Problem3 {
    private static volatile Problem3 problem3;
    int i;
    public Problem3(){
        i =2;
    }

    /**
     * Efficient way to implement
     * @return Singleton Object
     */
    public static Problem3 getInstance(){

        /*
        Uses double check idiom
         */
        if(problem3 == null){
           synchronized (Problem3.class){
               if(problem3 == null)
                   problem3 = new Problem3();
           }
        }
        return problem3;
    }
}
