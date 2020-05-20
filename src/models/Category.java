package models;

public enum Category {
    BREAKFAST,
    SOUP,
    MEAT,
    VEGETARIAN,
    VEGAN,
    BEVERAGE,
    ALCOHOL,
    DESSERT;

    public static Category getCategory(String name) {
        switch(name.toLowerCase()) {
            case "breakfast":
                return BREAKFAST;
            case "soup":
                return SOUP;
            case "meat":
                return MEAT;
            case "vegetarian":
                return VEGETARIAN;
            case "vegan":
                return VEGAN;
            case "beverage":
                return BEVERAGE;
            case "alcohol":
                return ALCOHOL;
            case "dessert":
                return DESSERT;
            default:
                throw new IllegalArgumentException("There is not such category: " + name);
        }
    }
}
