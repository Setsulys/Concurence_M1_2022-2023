package exam;

import java.util.ArrayList;

import exam.PokeAPI.Pokeball;
import exam.PokeAPI.Pokemon;

public class PokemonFactory {

	private ArrayList<Pokeball> listPokeball = new ArrayList<>();
	private ArrayList<Pokemon> listPokemon = new ArrayList<>();

	public PokemonFactory() {

	}

	public static void main(String[] args) {
		var poke = new PokemonFactory();
		for (var i = 0; i < 5; i++) {
			Thread.ofPlatform().start(() -> {
				try {
					for (;;) {
						var x = PokeAPI.capture();
						poke.listPokemon.add(x);
						System.out.println(Thread.currentThread().getName() + "a capture le pokemon " + x);
						for (var elem : poke.listPokemon) {
							if (elem.rarity() < 5) {
								Thread.ofPlatform().start(() -> {
									try {
										for (;;) {
											var pokeball = PokeAPI.trap(elem);
											poke.listPokeball.add(pokeball);
											System.out.println(Thread.currentThread().getName()
													+ "a mis le pokemon  d'une rarete  < 5 " + pokeball);
										}
									} catch (InterruptedException e) {
										throw new RuntimeException(e);
									}
								});
							} else {
								Thread.ofPlatform().start(() -> {
									try {
										for (;;) {
											var pokeball2 = PokeAPI.trap(elem);
											poke.listPokeball.add(pokeball2);
											System.out.println(Thread.currentThread().getName()
													+ "a mis le pokemon d'une rarete 5 " + pokeball2);
										}
									} catch (InterruptedException e) {
										throw new RuntimeException(e);
									}
								});
							}
						}

						for (var pokeball : poke.listPokeball) {
							for (var j = 0; j < 2; j++) {

							}
						}
					}
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});
		}

	}
}