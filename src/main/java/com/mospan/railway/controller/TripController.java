package com.mospan.railway.controller;

import com.mospan.railway.model.Entity;

import javax.servlet.annotation.WebServlet;

@WebServlet (value = "/trips/*")
public class TripController extends ResourceController {

    @Override
    Entity findModel(String id) {
        return null;
    }
}
