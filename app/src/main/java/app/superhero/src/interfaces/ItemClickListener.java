package app.superhero.src.interfaces;

import app.superhero.src.dto.SuperheroDto;

public interface ItemClickListener {
    void onItemClick(SuperheroDto superheroDto) throws Exception;
}
