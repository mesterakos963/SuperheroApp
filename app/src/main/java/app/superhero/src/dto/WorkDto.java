package app.superhero.src.dto;

import java.io.Serializable;

import app.superhero.src.utils.ZeroWhenNull;

public class WorkDto implements Serializable {
    String response;

    @ZeroWhenNull
    int id;

    String name;
    String occupation;
    String base;
}
