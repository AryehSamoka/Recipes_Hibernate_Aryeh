package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jb.entity.Ingredient;
import com.jb.entity.Recipe;
import com.jb.entity.IngredientAmount;
import com.jb.entity.IngredientAmountKey;

public class Main_SaveRecipeAndIngredients {

	public static void main(String[] args) {
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

			Recipe recipe = new Recipe("Pizza");
			Recipe recipe1 = new Recipe("Omelet");
			
			session.persist(recipe);
			session.persist(recipe1);
			
			Ingredient ingredient1 = new Ingredient("Sugar", 1, 100);
			Ingredient ingredient2 = new Ingredient("Egg", 2, 3);
			Ingredient ingredient3 = new Ingredient("Tomato", 1, 6);
			Ingredient ingredient4 = new Ingredient("Onion", 3, 7);
			
			session.persist(ingredient1);
			session.persist(ingredient2);
			session.persist(ingredient3);
			session.persist(ingredient4);
			
			recipe.addIngredient(ingredient2,1);
			recipe.addIngredient(ingredient3,4);
			recipe.addIngredient(ingredient4,2);
			
			recipe1.addIngredient(ingredient1,1);
			recipe1.addIngredient(ingredient2,4);
			recipe1.addIngredient(ingredient4,3);
			
			transaction.commit();
		}
		finally {
			factory.close();
		}
	}
}
