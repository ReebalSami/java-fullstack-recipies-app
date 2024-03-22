import {Recipe} from "../types/Recipe.ts";
import RecipeCard from "../components/RecipeCard/RecipeCard.tsx";

type RecipesPageProps = {
    recipes: Recipe[];
}

export default function RecipesPage(props: Readonly<RecipesPageProps>) {
    return (
        <div className="recipe-list">
            <h2>Recipe List</h2>
            <div className="recipes-container" style={{display: 'flex', flexWrap: 'wrap'}}>
                {props.recipes.map((recipe) => (
                    <RecipeCard key={recipe.id} recipe={recipe}/>
                ))}
            </div>

            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
        </div>
    )
}