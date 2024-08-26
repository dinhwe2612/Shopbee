package com.example.shopbee.ui.shop.categories;

import com.example.shopbee.R;
import com.example.shopbee.ui.shop.adapter.CategoriesAdapter;

import java.nio.channels.ScatteringByteChannel;
import java.util.HashMap;

public class CategoriesHashMap {
    private static final CategoriesHashMap instance = new CategoriesHashMap();
    public static CategoriesHashMap getInstance() {
        return instance;
    }
    private HashMap<String, String> categories;
    private HashMap<String, Integer> categoriesImage;
    private HashMap<String, String> categoriesLink;
    public HashMap<String, String> getCategories() {
        return categories;
    }
    public HashMap<String, Integer> getCategoriesImage() {
        return categoriesImage;
    }
    public String getCategoriesLink(String key) {
        return categoriesLink.get(key);
    }
    private CategoriesHashMap() {
        categories = new HashMap<>();
        categoriesImage = new HashMap<>();
        categoriesLink = new HashMap<>();
        categories.put("1000", "Books");
        categoriesImage.put("1000", R.style.Book);
        categories.put("7141124011", "Clothing, Shoes & Jewelry");
        categoriesImage.put("7141124011", R.style.Fashion);
        categories.put("1063498", "Home & Kitchen");
        categoriesImage.put("1063498", R.style.Kitchen);
        categories.put("1084128", "Office Products");
        categoriesImage.put("1084128", R.style.Stationery);
        categories.put("2619534011", "Pet Supplies");
        categoriesImage.put("2619534011", R.style.Pet);
        categories.put("541966", "Computers");
        categoriesImage.put("541966", R.style.Computer);
        categories.put("493964", "Electronics");
        categoriesImage.put("493964", R.style.Electronics);

        categories.put("4", "Children's Books");
        categories.put("5", "Computers & Technology");
        categories.put("8975347011", "Education & Teaching");
        categories.put("10", "Health, Fitness & Dieting");
        categories.put("173514", "Medical Books");
        categories.put("4736", "Self-Help");

        categories.put("7147440011", "Women");
        categories.put("7147441011", "Men");
        categories.put("7628012011", "Baby Girls");
        categories.put("7628013011", "Baby Boys");

        categories.put("1063252", "Bedding");
        categories.put("1063236", "Bath");
        categories.put("1063306", "Furniture");
        categories.put("16510975011", "Lighting & Ceiling Fans");
        categories.put("3206324011", "Heating, Cooling & Air Quality");
        categories.put("510106", "Vacuums & Floor Care");
        categories.put("510240", "Irons & Steamers");
        categories.put("3610841", "Storage & Organization");

        categories.put("1069242", "Office & School Supplies");
        categories.put("172574", "Office Electronics");
        categories.put("1069102", "Office Furniture & Lighting");

        categories.put("2975312011", "Dogs");
        categories.put("2975241011", "Cats");
        categories.put("2975446011", "Fish & Aquatic Pets");
        categories.put("2975221011", "Birds");

        categories.put("13896617011", "Computers & Tablets");
        categories.put("3011391011", "Laptop Accessories");
        categories.put("193870011", "Computer Components");

        categories.put("502394", "Camera & Photo");
        categories.put("2811119011", "Cell Phones & Accessories");
        categories.put("172541", "Headphones");

        categories.put("2787", "Animals");
        categoriesLink.put("2787", "https://m.media-amazon.com/images/I/71WUlaKVG-L._AC_UF1000,1000_QL80_.jpg");
//        categoriesImage.put("2787", R.drawable.animal_children_book);
        categories.put("3207", "Science, Nature & How It Works");
        categoriesLink.put("3207", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5X3ngu4e4xfOHSdLCVF7LjsnrH3mOjpjaFA&s");
//        categoriesImage.put("3207", R.drawable.science_children_book);
        categories.put("3344091011", "Geography & Cultures");
        categoriesLink.put("3344091011", "https://m.media-amazon.com/images/I/61HcngqelBL._AC_UF894,1000_QL80_.jpg");
//        categoriesImage.put("3344091011", R.drawable.geography_children_book);
        categories.put("2917", "History");
        categoriesLink.put("2917", "https://m.media-amazon.com/images/I/51NAVbHmeSL._AC_UF1000,1000_QL80_.jpg");
//        categoriesImage.put("2917", R.drawable.history_children_book);

        categories.put("549726", "Business Technology");
        categoriesLink.put("549726", "https://m.media-amazon.com/images/I/618v+5-EzdL._AC_UF1000,1000_QL80_.jpg");
        categories.put("3508", "Computer Science");
        categoriesLink.put("3508", "https://m.media-amazon.com/images/I/71mGHEwMN-L._AC_UF1000,1000_QL80_.jpg");
        categories.put("549646", "Databases & Big Data");
        categoriesLink.put("549646", "https://m.media-amazon.com/images/I/613auODCgnL._AC_UF894,1000_QL80_.jpg");
        categories.put("3652", "Networking & Cloud Computing");
        categoriesLink.put("3652", "https://m.media-amazon.com/images/I/41dPKcYbQyL._AC_UF1000,1000_QL80_.jpg");
        categories.put("3839", "Programming");
        categoriesLink.put("3839", "https://m.media-amazon.com/images/I/41mkuSVEvkL.jpg");
        categories.put("3510", "Web Development & Design");
        categoriesLink.put("3510", "https://m.media-amazon.com/images/I/41pH7mPYBTL.jpg");

        categories.put("69820", "Higher & Continuing Education");
        categoriesLink.put("69820", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1qDNR-8Ti9eLy5ekyPrf6-sYrGLbzwpghhg&s");
        categories.put("5267708011", "Schools & Teaching");
        categoriesLink.put("5267708011", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRB5HwHwOkEFIP0yum2IWxO_Domn62e7RBtgA&s");
        categories.put("5267723011", "Studying & Workbooks");
        categoriesLink.put("5267723011", "https://m.media-amazon.com/images/I/51ODXv0sbGL._AC_SR300,300.jpg");
        categories.put("5267710011", "Test Preparation");
        categoriesLink.put("5267710011", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5o93Zpe_HoCnjb_p6Z_EVh7jhhEZeYowdnQ&s");

        categories.put("4716", "Addiction & Recovery");
        categoriesLink.put("4716", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEjdYDo9FPSMerdtSrURaYScPTl9i3EoXmqw&s");
        categories.put("282840", "Aging");
        categoriesLink.put("282840", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqmRUXukNGAwaLSblN4zOwkOC_TOfkPjo6EQ&s");
        categories.put("4645", "Exercise & Fitness");
        categoriesLink.put("4645", "https://m.media-amazon.com/images/I/712GjqUYJUL._AC_UF1000,1000_QL80_.jpg");
        categories.put("4682", "Mental Health");
        categoriesLink.put("4682", "https://m.media-amazon.com/images/I/718A6RecZKL._AC_UF1000,1000_QL80_.jpg");
        categories.put("6514340011", "Sexual Health");
        categoriesLink.put("6514340011", "https://m.media-amazon.com/images/I/518iWRkFBLL._AC_UF1000,1000_QL80_.jpg");

        categories.put("266181", "Medical Informatics");
        categoriesLink.put("266181", "https://m.media-amazon.com/images/I/71O6yQRfsUL._AC_UF1000,1000_QL80_.jpg");
        categories.put("265542", "Medicine");
        categoriesLink.put("265542", "https://m.media-amazon.com/images/I/61wrToRwudL._AC_UF894,1000_QL80_.jpg");
        categories.put("227640", "Nursing");
        categoriesLink.put("227640", "https://m.media-amazon.com/images/I/61imDp+lvcL._AC_UF1000,1000_QL80_.jpg");
        categories.put("6511980011", "Psychology");
        categoriesLink.put("6511980011", "https://m.media-amazon.com/images/I/81LDP+GDKVL._AC_UF1000,1000_QL80_.jpg");

        categories.put("3568218011", "Anger Management");
        categoriesLink.put("3568218011", "https://m.media-amazon.com/images/I/41HOF7nqyRL._AC_UF1000,1000_QL80_.jpg");
        categories.put("10166949011", "Communication & Social Skills");
        categoriesLink.put("10166949011", "https://m.media-amazon.com/images/I/71+n-OaH1RL._AC_UF1000,1000_QL80_.jpg");
        categories.put("4743", "Memory Improvement");
        categoriesLink.put("4743", "https://m.media-amazon.com/images/I/61c3g0pRDgL._AC_UF894,1000_QL80_.jpg");
        categories.put("4747", "Stress Management");
        categoriesLink.put("4747", "https://m.media-amazon.com/images/I/718-7EH+yKL._AC_UF1000,1000_QL80_.jpg");
        categories.put("9473326011", "Time Management");
        categoriesLink.put("9473326011", "https://m.media-amazon.com/images/I/61rd1Rh3UiL._AC_UF1000,1000_QL80_.jpg");

        categories.put("1040660", "Clothing");
        categories.put("679337011", "Shoes");
        categories.put("7192394011", "Jewelry");
        categories.put("6358543011", "Watches");
        categories.put("15743631", "Handbags & Wallets");
        categories.put("2474936011", "Accessories");

        categories.put("1040658", "Clothing");
        categories.put("679255011", "Shoes");
        categories.put("3887881", "Jewelry");
        categories.put("6358539011", "Watches");
        categories.put("2474937011", "Accessories");

        categories.put("1044512", "Clothing");
        categories.put("7239798011", "Shoes");
        categories.put("2478435011", "Accessories");

        categories.put("1044510","Clothing");
        categories.put("7239799011", "Shoes");
        categories.put("2478436011", "Accessories");

        categories.put("166742011", "Nursery Bedding");
        categories.put("1063280", "Blankets & Throws");
        categories.put("1199122", "Bed Pillows & Positioners");
        categories.put("1063262", "Decorative Pillows, Inserts & Covers");

        categories.put("1063244", "Towels");
        categories.put("335116011", "Bathroom Shelves");
        categories.put("3731671", "Bath Linen Sets");
        categories.put("1063242", "Bath Rugs");

        categories.put("1063308", "Bedroom Furniture");
        categories.put("1063318", "Living Room Furniture");
        categories.put("3733781", "Kitchen & Dining Room Furniture");
        categories.put("1063312", "Home Office Furniture");
        categories.put("5422303011", "Game & Recreation Room Furniture");

        categories.put("5486428011", "Ceiling Lights");
        categories.put("495362", "Ceiling Fans & Accessories");
        categories.put("3736561", "Lamps & Shades");
        categories.put("5486429011", "Wall Lights");

        categories.put("14554126011", "Air Conditioners");
        categories.put("267554011", "Air Purifiers");
        categories.put("3737601", "Household Fans");
        categories.put("1063284", "Stoves & Fireplaces");

        categories.put("3743521", "Vacuums");
        categories.put("11333721011", "Carpet & Upholstery Cleaners & Accessories");
        categories.put("11333725011", "Steam Cleaners, Steam Mops & Accessories");

        categories.put("510246", "Ironing Boards");
        categories.put("510242", "Irons");
        categories.put("510248", "Garment Steamers");

        categories.put("2422430011", "Baskets, Bins & Containers");
        categories.put("2423187011", "Clothing & Closet Storage");
        categories.put("2422463011", "Racks, Shelves & Drawers");
        categories.put("3744341", "Laundry Storage & Organization");

        categories.put("172638", "Printer Ink & Toner");
        categories.put("1069320", "Tape, Adhesives & Fasteners");
        categories.put("1069784", "Writing & Correction Supplies");
        categories.put("1069664", "Paper");

        categories.put("172518", "Calculators");
        categories.put("172576", "Copiers");
        categories.put("9424016011", "Printers & Accessories");
        categories.put("14759145011", "Video Projectors & Accessories");

        categories.put("1068956", "Office Lighting");
        categories.put("1069114", "Carts & Stands");
        categories.put("1069222", "Tables");

        categories.put("2975359011", "Food");
        categories.put("2975413011", "Toys");
        categories.put("2975377011", "Health Supplies");
        categories.put("2975326011", "Beds & Furniture");

        categories.put("2975243011", "Beds & Furniture");
        categories.put("2975259011", "Feeding & Watering Supplies");
        categories.put("2975265011", "Food");
        categories.put("2975303011", "Toys");

        categories.put("2975464011", "Aquarium Cleaners");
        categories.put("2975448011", "Aquarium Decorator");
        categories.put("3048857011", "Aquarium Hydrometers");
        categories.put("7955292011", "Aquariums & Fish Bowls");

        categories.put("2975238011", "Food");
        categories.put("2975222011", "Cages & Accessories");
        categories.put("2975234011", "Feeding & Watering Supplies");

        categories.put("565098", "Desktops");
        categories.put("565108", "Laptops");
        categories.put("1232597011", "Tablets");

        categories.put("11041841", "Chargers & Adapters");
        categories.put("2243862011", "Cooling Pads & External Fans");
        categories.put("172470", "Bags, Cases & Sleeves");

        categories.put("3015426011", "Desktop Barebones");
        categories.put("3012292011", "External Components");
        categories.put("17923671011", "Internal Components");

        categories.put("281052", "Digital Cameras");
        categories.put("499248", "Lenses");
        categories.put("499306", "Tripods & Monopods");

        categories.put("2407748011", "Carrier Cell Phones");
        categories.put("2407749011", "Unlocked Cell Phones");
        categories.put("2407760011", "Cases, Holsters & Clips");

        categories.put("12097478011", "Earbud Headphones");
        categories.put("12097480011", "On-Ear Headphones");
        categories.put("12097479011", "Over-Ear Headphones");
    }
}
