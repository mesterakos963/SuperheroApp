package app.superhero.src.interfaces;

import app.superhero.src.dao.SuperheroMasterData;

public interface StarClickCallback {
    void onStarClick(SuperheroMasterData superhero);
}
