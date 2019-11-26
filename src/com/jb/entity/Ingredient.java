package com.jb.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "ingredient")
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "name",unique = true, length = 32, nullable = false)
	private String name;
	@Column(name = "price")
	private double price;
	@Column(name = "amount_home")
	private double amountHome;
	@ManyToMany(mappedBy = "ingredients", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<Recipe> recipes;
	@OneToMany(cascade = CascadeType.ALL ,mappedBy = "ingredient")
	private Set<IngredientAmount> amounts;
	
	public Ingredient() {
		recipes = new ArrayList<>();
		amounts = new HashSet<>();
	}
	
	public Ingredient(String name, double price, double amountHome) {
		this();
		this.name = name;
		this.price = price;
		this.amountHome = amountHome;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmountHome() {
		return amountHome;
	}
	
	public void setAmountHome(double amountHome) {
		this.amountHome = amountHome;
	}
	
	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	public Set<IngredientAmount> getAmounts() {
		return amounts;
	}
	
	public double getAmount(Recipe recipe) {
		for(IngredientAmount ia:getAmounts()) {
			if(ia.getId().getRecipeId() == recipe.getId()) {
				return ia.getAmount();
			}
		}
		
		return 0;
	}

	public void setAmounts(Set<IngredientAmount> amounts) {
		this.amounts = amounts;
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
		Ingredient other = (Ingredient) obj;
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
		return "Ingredient [id=" + id + ", name=" + name + ", price=" + price + ", amountHome=" + amountHome + "]";
	}
}	