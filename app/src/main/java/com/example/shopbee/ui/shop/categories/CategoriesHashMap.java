package com.example.shopbee.ui.shop.categories;

import com.example.shopbee.R;
import com.example.shopbee.ui.shop.adapter.CategoriesAdapter;

import java.nio.channels.ScatteringByteChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CategoriesHashMap {
    private static final CategoriesHashMap instance = new CategoriesHashMap();
    public static CategoriesHashMap getInstance() {
        return instance;
    }

    public Set<String> getClothingCategories() {
        return clothingCategories;
    }

    private Set<String> clothingCategories;
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

        clothingCategories = new HashSet<>();
        clothingCategories.add("1040660");

        clothingCategories.add("1045024");
        clothingCategories.add("2368343011");
        clothingCategories.add("1044456");
        clothingCategories.add("1258603011");
        clothingCategories.add("1048188");
        clothingCategories.add("1048184");
        clothingCategories.add("1045022");
        clothingCategories.add("1048186");
        clothingCategories.add("1258967011");
        clothingCategories.add("3456051");
        clothingCategories.add("1046622");
        clothingCategories.add("9522931011");
        clothingCategories.add("9522930011");
        clothingCategories.add("1044646");
        clothingCategories.add("9522932011");
        clothingCategories.add("1044886");

        clothingCategories.add("11300395011");

        clothingCategories.add("11300396011");
        clothingCategories.add("11300408011");
        clothingCategories.add("11300418011");
        clothingCategories.add("11300423011");
        clothingCategories.add("11300426011");
        clothingCategories.add("11300427011");
        clothingCategories.add("11300431011");
        clothingCategories.add("11300435011");
        clothingCategories.add("11300439011");
        clothingCategories.add("11300440011");
        clothingCategories.add("11300464011");
        clothingCategories.add("11300476011");
        clothingCategories.add("11300536011");
        clothingCategories.add("11300539011");
        clothingCategories.add("11300568011");
        clothingCategories.add("11300578011");

        clothingCategories.add("7581674011");

        clothingCategories.add("9650170011");
        clothingCategories.add("9650277011");
        clothingCategories.add("9650260011");
        clothingCategories.add("9650175011");
        clothingCategories.add("9650178011");
        clothingCategories.add("9650231011");
        clothingCategories.add("9650239011");
        clothingCategories.add("9650235011");
        clothingCategories.add("9650182011");
        clothingCategories.add("9650113011");
        clothingCategories.add("9650265011");
        clothingCategories.add("9650183011");
        clothingCategories.add("9650179011");
        clothingCategories.add("9650141011");
        clothingCategories.add("9650252011");
        clothingCategories.add("9650243011");

        clothingCategories.add("7581675011");

        clothingCategories.add("5674527011");
        clothingCategories.add("1285229011");
        clothingCategories.add("2379250011");
        clothingCategories.add("1285248011");
        clothingCategories.add("1285237011");
        clothingCategories.add("1285239011");
        clothingCategories.add("1285244011");
        clothingCategories.add("1285243011");
        clothingCategories.add("2379251011");
        clothingCategories.add("1285249011");
        clothingCategories.add("1285245011");
        clothingCategories.add("1285230011");
        clothingCategories.add("1285238011");
        clothingCategories.add("2349107011");
        clothingCategories.add("1285246011");
        clothingCategories.add("5674523011");

        clothingCategories.add("7581676011");

        clothingCategories.add("6114042011");
        clothingCategories.add("6114000011");
        clothingCategories.add("6114010011");
        clothingCategories.add("6114015011");
        clothingCategories.add("6114053011");
        clothingCategories.add("6114055011");
        clothingCategories.add("6114064011");
        clothingCategories.add("6114060011");
        clothingCategories.add("6114059011");
        clothingCategories.add("6114018011");
        clothingCategories.add("6114155011");
        clothingCategories.add("6114098011");
        clothingCategories.add("6114109011");
        clothingCategories.add("6114052011");
        clothingCategories.add("6114054011");
        clothingCategories.add("6114072011");
        clothingCategories.add("6114080011");
        clothingCategories.add("6114068011");
        clothingCategories.add("6114076011");
        clothingCategories.add("6114090011");

        clothingCategories.add("7581677011");

        clothingCategories.add("5605285011");
        clothingCategories.add("5605244011");
        clothingCategories.add("5605254011");
        clothingCategories.add("5605259011");
        clothingCategories.add("5605296011");
        clothingCategories.add("5605298011");
        clothingCategories.add("5605307011");
        clothingCategories.add("5605303011");
        clothingCategories.add("5605302011");
        clothingCategories.add("5605262011");
        clothingCategories.add("5605387011");
        clothingCategories.add("5605338011");
        clothingCategories.add("5605344011");
        clothingCategories.add("5605315011");
        clothingCategories.add("5605323011");
        clothingCategories.add("5605311011");
        clothingCategories.add("5605319011");
        clothingCategories.add("5605334011");
        clothingCategories.add("5605400011");
        clothingCategories.add("5605409011");

        clothingCategories.add("7606660011");

        clothingCategories.add("6567206011");
        clothingCategories.add("6575936011");
        clothingCategories.add("2492613011");
        clothingCategories.add("6572910011");

        clothingCategories.add("1040658");
        clothingCategories.add("2476517011");
        clothingCategories.add("1258644011");
        clothingCategories.add("1044442");
        clothingCategories.add("1045830");
        clothingCategories.add("1045564");
        clothingCategories.add("1045558");
        clothingCategories.add("1045560");
        clothingCategories.add("3455821");
        clothingCategories.add("1046670");
        clothingCategories.add("1045684");
        clothingCategories.add("1045706");
//        clothingCategories.add("1045708");
        clothingCategories.add("3455861");
        clothingCategories.add("15697821011");

        clothingCategories.add("11307731011");

        clothingCategories.add("11307732011");
        clothingCategories.add("11307740011");
        clothingCategories.add("11307743011");
        clothingCategories.add("11307748011");
        clothingCategories.add("11307775011");
        clothingCategories.add("11307776011");
        clothingCategories.add("11307779011");
        clothingCategories.add("11307784011");
        clothingCategories.add("11307803011");
        clothingCategories.add("11307808011");
        clothingCategories.add("11307818011");
//        clothingCategories.add("11307831011");
        clothingCategories.add("11307835011");

//        clothingCategories.add("7581681011");

        clothingCategories.add("6002100011");
        clothingCategories.add("6002109011");
        clothingCategories.add("6002131011");
        clothingCategories.add("6002154011");
        clothingCategories.add("6002145011");
        clothingCategories.add("6002146011");
        clothingCategories.add("6002149011");
        clothingCategories.add("6002112011");
        clothingCategories.add("6002191011");
        clothingCategories.add("6002136011");
        clothingCategories.add("6002174011");
        clothingCategories.add("6002181011");
        clothingCategories.add("6002165011");

        clothingCategories.add("7606661011");

        clothingCategories.add("6567198011");
        clothingCategories.add("6575930011");
        clothingCategories.add("2492606011");
        clothingCategories.add("6572900011");

        clothingCategories.add("9564526011");

        clothingCategories.add("9626546011");
        clothingCategories.add("9626511011");
        clothingCategories.add("9626577011");
        clothingCategories.add("9626514011");
        clothingCategories.add("9626541011");
        clothingCategories.add("9626543011");
        clothingCategories.add("9626554011");
        clothingCategories.add("9626491011");
        clothingCategories.add("9626582011");
        clothingCategories.add("9626568011");
        clothingCategories.add("9626587011");
        clothingCategories.add("9626559011");

        clothingCategories.add("1040664");

        clothingCategories.add("3455761");
        clothingCategories.add("1288617011");
        clothingCategories.add("1045470");
        clothingCategories.add("5604818011");
        clothingCategories.add("1045206");
        clothingCategories.add("1045268");
        clothingCategories.add("5468988011");
        clothingCategories.add("2412731011");
        clothingCategories.add("1045272");
        clothingCategories.add("1045264");
        clothingCategories.add("1045266");
        clothingCategories.add("1045468");
        clothingCategories.add("1045424");
        clothingCategories.add("1045426");
        clothingCategories.add("3455741");
        clothingCategories.add("3455751");
        clothingCategories.add("1044490");
        clothingCategories.add("1045428");

        clothingCategories.add("1040666");

        clothingCategories.add("3455641");
        clothingCategories.add("1288606011");
        clothingCategories.add("5604815011");
        clothingCategories.add("1046120");
        clothingCategories.add("1045910");
        clothingCategories.add("3455421");
        clothingCategories.add("1045904");
        clothingCategories.add("1045906");
        clothingCategories.add("3455651");
//        clothingCategories.add("1046030");
        clothingCategories.add("699914011");
        clothingCategories.add("3455451");
        clothingCategories.add("3455631");
        clothingCategories.add("2422483011");
        clothingCategories.add("1046028");

        clothingCategories.add("12035955011");

        clothingCategories.add("9056921011");
        clothingCategories.add("9056985011");
        clothingCategories.add("9057038011");
        clothingCategories.add("9057092011");

        clothingCategories.add("7586174011");

        clothingCategories.add("2491298011");
        clothingCategories.add("1261192011");

        clothingCategories.add("1265851011");

        clothingCategories.add("1268047011");
        clothingCategories.add("1267999011");
        clothingCategories.add("1283420011");
        clothingCategories.add("2492620011");
        clothingCategories.add("1268073011");
        clothingCategories.add("1268067011");

        clothingCategories.add("14194714011");

        clothingCategories.add("14194715011");
        clothingCategories.add("14194720011");
        clothingCategories.add("14194719011");
        clothingCategories.add("14194718011");
        clothingCategories.add("14194717011");
        clothingCategories.add("14194716011");

        clothingCategories.add("14194729011");

        clothingCategories.add("14194730011");
        clothingCategories.add("14194731011");
        clothingCategories.add("14194732011");
        clothingCategories.add("14194733011");
        clothingCategories.add("14194734011");

        clothingCategories.add("14194744011");
        clothingCategories.add("14194752011");
    }

}
