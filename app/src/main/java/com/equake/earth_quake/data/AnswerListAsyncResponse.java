package com.equake.earth_quake.data;

import com.equake.earth_quake.model.Earthquake;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Earthquake>earthquakes);
}
