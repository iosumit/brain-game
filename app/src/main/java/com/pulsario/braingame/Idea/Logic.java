package com.pulsario.braingame.Idea;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {
    private int startRange = 1;
    private int endRange = 10;
    private Random rd;
    private int op1;
    private int op2;
    private int oprator;
    private int[] answers;
    private int answerPosition = 1;
    private int answerCount = 4;
    private List<Integer> seed;

    public Logic() {
        rd = new Random();
        answers = new int[answerCount];
        seed = new ArrayList<Integer>();
        seed.add(-2);
        seed.add(-3);
        seed.add(-4);
        seed.add(1);
        seed.add(2);
        seed.add(3);
    }
    /*public Logic(int range) {
        rd = new Random();
        endRange = range;
    }*/
    public void generateNumbers(){
        op2 = rd.nextInt(endRange) + 1;
        oprator = rd.nextInt(4) + 1;

        if (oprator == 4){
            op1 = (rd.nextInt(endRange) + 1) * op2;
        } else {
            op1 = rd.nextInt(endRange) + 1;
        }
    }
    public int getFirstOperand(){
        generateNumbers();
        return op1;
    }
    public int getSecondOperand(){
        return op2;
    }
    public int getOperator(){
        return oprator;
    }

    public int[] getAnswers() {
        List<Integer> tempSeed = seed;
        int x = 0;
        int y = 0;
        for (int i = 0; i < answers.length; i++){
            y = rd.nextInt(tempSeed.size());
            x = tempSeed.get(y);

            switch (oprator){
                case 1: answers[i] = ((op1 + op2) + x); break;
                case 2: answers[i] = ((op1 - op2) + x); break;
                case 3: answers[i] = ((op1 * op2) + x); break;
                case 4: answers[i] = ((op1 / op2) + x); break;
            }
            //tempSeed.remove(y);
        }
        int i = rd.nextInt(answers.length);
        switch (oprator){
            case 1: answers[i] = ((op1 + op2)); break;
            case 2: answers[i] = ((op1 - op2)); break;
            case 3: answers[i] = ((op1 * op2)); break;
            case 4: answers[i] = ((op1 / op2)); break;
        }
        answerPosition = i+1;
        return answers;
    }

    public int getAnswerPosition() {
        return answerPosition;
    }

    public void setEndRange(int endRange) {
        this.endRange = endRange;
    }
}
