package com.fourhourdesigns.witsrewards;

public class QuestionsLibrary {

    private String quizQuestions[] = {
            "In what year was Wits University established?",
            "Roughly how many students attend Wits?",
            "What building on main campus has the most lecture venues?",
            "Thanks for playing the weekly quiz. Come back next week to earn more points!"
    };

    private String quizOptions[][] = {

            {"1922","1888", "1995","1966"},
            {"21000","89000","38000","49000"},
            {"Central Block","Senate House","FNB Building", "Wits Science Stadium"},
            {"Done","","",""}

    };

    private String correctAnswers[] = {
            "1922", "38000", "Central Block", "Done"
    };


    public String getQuestion(int a){
        String questions = quizQuestions[a];
        return questions;

    }

    public String getChoice1(int a){
        String choice0 = quizOptions[a][0];
        return choice0;
    }

    public String getChoice2(int a){
        String choice1 = quizOptions[a][1];
        return choice1;
    }

    public String getChoice3(int a){
        String choice2 = quizOptions[a][2];
        return choice2;
    }




    public String getCorrectAnswer(int a){
        String answer = correctAnswers[a];
        return answer;
    }
}
