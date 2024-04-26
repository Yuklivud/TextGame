package com.servletproject.utils;

import java.util.List;
                                                              
public class Question {                                       
    private int id;                                           
    private String questText;                                 
    private List<String> answers;                             
    private String correctAnswer;
    private String audioPath;
    public Question(int id, String question, List<String> answers, String correctAnswer, String audioPath) {
        this.id = id;
        this.questText = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.audioPath = audioPath;
    }

    public int getId() {
        return id;
    }

    public String getQuestText() {
        return questText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getAudioPath() {
        return audioPath;
    }
}
