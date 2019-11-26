package com.jb.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipe")
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "name",unique = true, length = 32, nullable = false)
	private String name;
	
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(
			name = "ingredient_amount",
					joinColumns = @JoinColumn(name = "recipe_id"),
					inverseJoinColumns = @JoinColumn(name = "ingredient_id" ))
	private List<Ingredient> ingredients;
	@OneToMany(cascade = CascadeType.ALL ,mappedBy = "recipe")
    private Set<IngredientAmount> amounts;
	
	public Recipe() {
		ingredients = new ArrayList<>();
		amounts = new HashSet<>();
	}

	public Recipe(String name) {
		this();
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void addIngredient(Ingredient ingredient, double amount) {
		IngredientAmountKey ingredientAmountKey = new IngredientAmountKey(ingredient.getId(), this.id);
		IngredientAmount ingredientAmount = new IngredientAmount(ingredientAmountKey, this, ingredient, amount);
		
		amounts.add(ingredientAmount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + "]";
	}
}
