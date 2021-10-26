package app.superhero.src.interfaces;

import app.superhero.src.model.dao.SuperheroMasterData;

public interface StarClickCallback {
    void onStarClick(SuperheroMasterData superhero, int position);
}
