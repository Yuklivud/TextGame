package com.servletproject.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class QuestionLoader {
    public List<Question> loadQuestions(){
        List<Question> questionsList;
        try(InputStream inputStream = getClass().getResourceAsStream("/questions.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            questionsList = new Gson().fromJson(inputStreamReader, new TypeToken<List<Question>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return questionsList;
    }
}
