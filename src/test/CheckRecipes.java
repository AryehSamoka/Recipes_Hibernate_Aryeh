package test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.jb.entity.Ingredient;
import com.jb.entity.IngredientAmount;
import com.jb.entity.IngredientAmountKey;
import com.jb.entity.Recipe;

public class CheckRecipes {

	public static void main(String[] args) {
			
			Set<Recipe> recipes = goodRecipes();
			System.out.println(recipes);
	}

	public static Set<Recipe> goodRecipes(){
		Set<Recipe> recipes = new HashSet<>();
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Ingredient.class)
				.addAnnotatedClass(Recipe.class)
				.addAnnotatedClass(IngredientAmount.class)
				.addAnnotatedClass(IngredientAmountKey.class)
				.buildSessionFactory();

		try {
			Session session = factory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			
			Query<Ingredient> query = session.createQuery("from Ingredient", Ingredient.class);
			List<Ingredient> ingredients = query.getResultList();
			
			possibleRecipes(recipes, ingredients);
		
			checkedRecipes(recipes, ingredients);
			
			transaction.commit();
		}
		finally {
			factory.close();
		}	
		return recipes;
	}
	
	private static void checkedRecipes(Set<Recipe> recipes, List<Ingredient> ingredients) {
		
		Iterator<Recipe> it = recipes.iterator();
		while (it.hasNext()) {
		    Recipe r = it.next();
		    
		    List<Ingredient> ingredients_r = r.getIngredients();
			for(Ingredient i: ingredients_r) {
				double amount = i.getAmount(r);
				if(!ingredients.contains(i)||i.getAmountHome()<amount) {
					it.remove();
					break;
				}
			}
		}
	}

	private static void possibleRecipes(Set<Recipe> recipes, List<Ingredient> ingredients) {
		for (Ingredient ingredient : ingredients) {
			List<Recipe> iRecipes = ingredient.getRecipes();
			recipes.addAll(iRecipes);
		}
	}
}
