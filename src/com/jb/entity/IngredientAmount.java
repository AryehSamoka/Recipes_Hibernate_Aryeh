package com.jb.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient_amount")
public class IngredientAmount {
    @EmbeddedId
    private IngredientAmountKey id;
 
    @ManyToOne
    @MapsId("recipe_id")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
 
    @ManyToOne
    @MapsId("ingredient_id")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    @Column(name = "amount")
    private double amount;

    public IngredientAmount() {
    	amount = 0;
    }
    
	public IngredientAmount(IngredientAmountKey id, Recipe recipe, Ingredient ingredient, double amount) {
		this.id = id;
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.amount = amount;
	}

	public IngredientAmountKey getId() {
		return id;
	}

	public void setId(IngredientAmountKey id) {
		this.id = id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "IngredientAmount [id=" + id + ", recipe=" + recipe + ", ingredient=" + ingredient + ", amount=" + amount
				+ "]";
	}
}

