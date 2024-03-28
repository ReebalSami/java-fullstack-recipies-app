import {Recipe, RecipeCategory, RecipeType} from "../types/Recipe.ts";
import {ChangeEvent, useState} from "react";
import RecipeCard from "../components/RecipeCard/RecipeCard.tsx";
import {Autocomplete, TextField} from "@mui/material";

type SearchAndFilterPageProps = {
    recipes: Recipe[];
    fetchRecipes: () => void;
}
export default function SearchAndFilterPage(props: Readonly<SearchAndFilterPageProps>) {
    const [searchName, setSearchName] = useState<string>("");
    const [searchCategory, setSearchCategory] = useState<string>("");
    const [searchType, setSearchType] = useState<string>("");

    const optionalCategories = Object.values(RecipeCategory)
    const optionalTypes = Object.values(RecipeType)

    function handleSearchName(e: ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        setSearchName(value);
    }

    const sortedRecipes = [...props.recipes].sort(function (a, b) {
        const nameA = a.name.toLowerCase();
        const nameB = b.name.toLowerCase();

        if (nameA < nameB) {
            return -1;
        }
        if (nameA > nameB) {
            return 1;
        }

        if (a.name < b.name) {
            return -1;
        }
        if (a.name > b.name) {
            return 1;
        }

        return 0;
    });

    const filteredRecipes = sortedRecipes.filter(
        (recipe) =>
            recipe.name.toLowerCase().includes(searchName.toLowerCase()) &&
            recipe.category.toString().includes(searchCategory) &&
            recipe.type.toString().includes(searchType)
    );

    return (
        <>
        <div className={"searchForm"}>
            <TextField className="searchTextField" label="Search for recipe" type={"search"} value={searchName} onChange={handleSearchName}/>
            <Autocomplete
                disablePortal
                options={optionalCategories}
                onInputChange={(_e, value) => setSearchCategory(value)}
                sx={{m: 1, width: 300}}
                renderInput={(params) => <TextField {...params} label="Choose Category" />}/>
            <Autocomplete
                disablePortal
                options={optionalTypes}
                onInputChange={(_e, value) => setSearchType(value)}
                sx={{m: 1, width: 300}}
                renderInput={(params) => <TextField {...params} label="Choose Type" />}/>
        </div>
        <div className="recipe-list">
            <h2>Recipe List</h2>
            <div className="recipes-container" style={{display: 'flex', flexWrap: 'wrap'}}>
                {filteredRecipes.map((recipe) => (
                    <RecipeCard key={recipe.id} recipe={recipe}/>
                ))}
            </div>
        </div>
        </>
    )
}