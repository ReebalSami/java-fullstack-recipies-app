package de.neuefische.backend.controller;

import de.neuefische.backend.model.recipe.*;
import de.neuefische.backend.repository.RecipesRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RecipeControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RecipesRepo repo;

    @Test
    void getAllRecipes_returnEmptyList_WhenCalledInitially() throws Exception {
        //GIVEN
        //WHEN & THEN
        mvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andReturn();
    }

    @Test
    void getAllRecipe_returnsRecipe_whenCalledWithRecipeInRepo() throws Exception {
        //GIVEN
        PreparationTime preparationTime = new PreparationTime(0, 30);
        TotalTime totalTime = new TotalTime(1, 15);
        List<RecipeIngredients> recipeIngredients = new ArrayList<>();
        recipeIngredients.add(new RecipeIngredients("name test", "quantity 1"));
        recipeIngredients.add(new RecipeIngredients("name test 2", "quantity 2"));
        Recipe recipe = new Recipe("1",
                "Test Recipe",
                "Test Description",
                "Test Instructions",
                "Test Author",
                "Test Origin",
                List.of(RecipeType.VEGETARIAN, RecipeType.WITH_MEAT),
                preparationTime,
                totalTime,
                List.of(RecipeCategory.DINNER, RecipeCategory.SIDE_DISH),
                RecipeDifficulty.EASY,
                recipeIngredients
        );
        repo.save(recipe);
        //WHEN & THEN
        mvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "id": "1",
                                "name": "Test Recipe",
                                "description": "Test Description",
                                "instructions": "Test Instructions",
                                "author": "Test Author",
                                "origin": "Test Origin",
                                "type": ["Vegetarian", "With Meat"],
                                "preparationTime": {"hours": 0, "minutes": 30},
                                "totalTime": {"hours": 1, "minutes": 15},
                                "category": ["Dinner", "Side Dish"],
                                "difficulty": "Easy",
                                "ingredients": [
                                                    {
                                                        "name": "name test",
                                                        "quantity": "quantity 1"
                                                    },
                                                    {
                                                        "name": "name test 2",
                                                        "quantity": "quantity 2"
                                                    }
                                                ]
                            }
                        ]

                        """))
                .andReturn();
    }

}