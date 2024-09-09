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
        categoriesLink.put("1040660", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvRzz0rHGFZ4qXeVwD9L06KxEjiQxS8W2S0w&s");
        categories.put("679337011", "Shoes");
        categoriesLink.put("679337011", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSP7ogefkuTQo7yBpQtT2eBXnRp5RjrSqLa0A&s");
        categories.put("7192394011", "Jewelry");
        categoriesLink.put("7192394011", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0m2m-NFX2yOnKb0TNTl6NY_R-WMdlxyP6ZA&s");
        categories.put("6358543011", "Watches");
        categoriesLink.put("6358543011", "https://m.media-amazon.com/images/I/71mR1c5l70L._AC_UY350_.jpg");
        categories.put("15743631", "Handbags & Wallets");
        categoriesLink.put("15743631", "https://m.media-amazon.com/images/I/71JaUbOpzzL._AC_UY900_.jpg");
        categories.put("2474936011", "Accessories");
        categoriesLink.put("2474936011", "https://images-eu.ssl-images-amazon.com/images/G/42/SL/June/XCM_Manual_1443273_4798064_440x440_2X.jpg");

        categories.put("1040658", "Clothing");
        categoriesLink.put("1040658", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGnxHQXNHSeKALkxZjzr9HZSlEe0-7Xm11tQ&s");
        categories.put("679255011", "Shoes");
        categoriesLink.put("679255011", "https://m.media-amazon.com/images/I/614tgeynm4L._AC_UY900_.jpg");
        categories.put("3887881", "Jewelry");
        categoriesLink.put("3887881", "https://m.media-amazon.com/images/I/616m-DZkNxL._UF894,1000_QL80_.jpg");
        categories.put("6358539011", "Watches");
        categoriesLink.put("6358539011", "https://m.media-amazon.com/images/I/71JCcF4xHZL._AC_UY1000_.jpg");
        categories.put("2474937011", "Accessories");
        categoriesLink.put("2474937011", "https://m.media-amazon.com/images/I/71iKrPi3DCL._AC_UY1000_.jpg");

        categories.put("1044512", "Clothing");
        categoriesLink.put("1044512", "https://m.media-amazon.com/images/S/mms-media-storage-prod/final/BrandPosts/brandPosts/21e17501-5b42-45ab-aeb7-c598e6df2c90/6ee6d9e9-50a4-4917-a7d1-2166326f3245/media._SL480_.jpeg");
        categories.put("7239798011", "Shoes");
        categoriesLink.put("7239798011", "https://m.media-amazon.com/images/I/616EV9uFprL._AC_UF894,1000_QL80_.jpg");
        categories.put("2478435011", "Accessories");
        categoriesLink.put("2478435011", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/2f63bf24-5429-4b24-92e0-93bd3f97dfde._SL480_.jpg");

        categories.put("1044510","Clothing");
        categoriesLink.put("1044510", "https://m.media-amazon.com/images/I/8146Jt8tJVL._AC_UF894,1000_QL80_.jpg");
        categories.put("7239799011", "Shoes");
        categoriesLink.put("7239799011", "https://m.media-amazon.com/images/I/81e3SnEjmzS._AC_UY900_.jpg");
        categories.put("2478436011", "Accessories");
        categoriesLink.put("2478436011", "https://m.media-amazon.com/images/I/719WAf7CH1L.jpg");

        categories.put("166742011", "Nursery Bedding");
        categoriesLink.put("166742011", "https://m.media-amazon.com/images/I/61WDn5auQPL._AC_UF894,1000_QL80_.jpg");
        categories.put("1063280", "Blankets & Throws");
        categoriesLink.put("1063280", "https://m.media-amazon.com/images/I/8148OcS6eDL._AC_UF894,1000_QL80_.jpg");
        categories.put("1199122", "Bed Pillows & Positioners");
        categoriesLink.put("1199122", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/d4781fad-c4c1-46c2-9f7a-87c6ada35b5a._SL480_.jpg");
        categories.put("1063262", "Decorative Pillows, Inserts & Covers");
        categoriesLink.put("1063262", "https://m.media-amazon.com/images/S/mms-media-storage-prod/final/BrandPosts/brandPosts/b6283012-5ad3-40d0-9ece-fc485091b0e4/34839003-158c-4634-82ff-b72f0a39a21c/media._SL480_.jpeg");

        categories.put("1063244", "Towels");
        categoriesLink.put("1063244", "https://m.media-amazon.com/images/I/81vy2p+HAgL._AC_UF894,1000_QL80_.jpg");
        categories.put("335116011", "Bathroom Shelves");
        categoriesLink.put("335116011", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/21bb290a-90e8-4e91-825c-f914f40453f5._SL480_.jpg");
        categories.put("3731671", "Bath Linen Sets");
        categoriesLink.put("3731671", "https://m.media-amazon.com/images/I/917vvfyNP6L._AC_UF894,1000_QL80_.jpg");
        categories.put("1063242", "Bath Rugs");
        categoriesLink.put("1063242", "https://m.media-amazon.com/images/I/81vSEefvOXL._AC_UF894,1000_QL80_.jpg");

        categories.put("1063308", "Bedroom Furniture");
        categoriesLink.put("1063308", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFTaoZPvwLyKuqY4wpAkNfL1Qt3Ey2jJZAuQ&s");
        categories.put("1063318", "Living Room Furniture");
        categoriesLink.put("1063318", "https://m.media-amazon.com/images/I/81MqH4LTA7L._AC_UF894,1000_QL80_.jpg");
        categories.put("3733781", "Kitchen & Dining Room Furniture");
        categoriesLink.put("3733781", "https://m.media-amazon.com/images/I/81+RnLM+R7L._AC_UF894,1000_QL80_.jpg");
        categories.put("1063312", "Home Office Furniture");
        categoriesLink.put("1063312", "https://m.media-amazon.com/images/I/91yPdiLwpBL._AC_UF894,1000_QL80_.jpg");
        categories.put("5422303011", "Game & Recreation Room Furniture");
        categoriesLink.put("5422303011", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/420b9bbd-222c-48ba-be2b-bc21cd49845b._SL480_.jpeg");

        categories.put("5486428011", "Ceiling Lights");
        categoriesLink.put("5486428011", "https://m.media-amazon.com/images/I/7155NnHr5dL._AC_UF894,1000_QL80_.jpg");
        categories.put("495362", "Ceiling Fans & Accessories");
        categoriesLink.put("495362", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/764595e7-bff0-4484-8a11-0593906a2ce0._SL480_.jpg");
        categories.put("3736561", "Lamps & Shades");
        categoriesLink.put("3736561", "https://m.media-amazon.com/images/I/61Z8U6hia6L._AC_UF894,1000_QL80_.jpg");
        categories.put("5486429011", "Wall Lights");
        categoriesLink.put("5486429011", "https://m.media-amazon.com/images/I/51GqXZsdGOL._AC_UF894,1000_QL80_.jpg");

        categories.put("14554126011", "Air Conditioners");
        categoriesLink.put("14554126011", "https://m.media-amazon.com/images/I/61G1U0BZACL._AC_UF894,1000_QL80_.jpg");
        categories.put("267554011", "Air Purifiers");
        categoriesLink.put("267554011", "https://m.media-amazon.com/images/I/71hP5cdm+8L._AC_UF1000,1000_QL80_.jpg");
        categories.put("3737601", "Household Fans");
        categoriesLink.put("3737601", "https://m.media-amazon.com/images/I/61B6kl5i0vL._AC_UF894,1000_QL80_.jpg");
        categories.put("1063284", "Stoves & Fireplaces");
        categoriesLink.put("1063284", "https://m.media-amazon.com/images/I/616n+Kj0gfL._AC_UF894,1000_QL80_.jpg");

        categories.put("3743521", "Vacuums");
        categoriesLink.put("3743521", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/cce996bc-0ab4-4f5d-a078-b7ba2ddcb579._SL480_.jpg");
        categories.put("11333721011", "Carpet & Upholstery Cleaners & Accessories");
        categoriesLink.put("11333721011", "https://m.media-amazon.com/images/I/719CSrquYYL._AC_UF894,1000_QL80_.jpg");
        categories.put("11333725011", "Steam Cleaners, Steam Mops & Accessories");
        categoriesLink.put("11333725011", "https://m.media-amazon.com/images/I/911zQs+vg-L.jpg");

        categories.put("510246", "Ironing Boards");
        categoriesLink.put("510246", "https://m.media-amazon.com/images/I/71hOIn27WiL._AC_UF894,1000_QL80_.jpg");
        categories.put("510242", "Irons");
        categoriesLink.put("510242", "https://m.media-amazon.com/images/I/61mBBqrgJhL._AC_UF894,1000_QL80_.jpg");
        categories.put("510248", "Garment Steamers");
        categoriesLink.put("510248", "https://m.media-amazon.com/images/I/61dtcYvwMUL._AC_UF894,1000_QL80_.jpg");

        categories.put("2422430011", "Baskets, Bins & Containers");
        categoriesLink.put("2422430011", "https://m.media-amazon.com/images/I/71OexVFpBwL._AC_UF894,1000_QL80_.jpg");
        categories.put("2423187011", "Clothing & Closet Storage");
        categoriesLink.put("2423187011", "https://m.media-amazon.com/images/I/71X756iVmiL.jpg");
        categories.put("2422463011", "Racks, Shelves & Drawers");
        categoriesLink.put("2422463011", "https://m.media-amazon.com/images/I/81vXftpoHhL._AC_UF894,1000_QL80_.jpg");
        categories.put("3744341", "Laundry Storage & Organization");
        categoriesLink.put("3744341", "https://m.media-amazon.com/images/I/81NPf22cJrL.jpg");

        categories.put("172638", "Printer Ink & Toner");
        categoriesLink.put("172638", "https://m.media-amazon.com/images/I/71YNZsK0h0L.jpg");
        categories.put("1069320", "Tape, Adhesives & Fasteners");
        categoriesLink.put("1069320", "https://m.media-amazon.com/images/I/711VSIxOAnL._AC_UF894,1000_QL80_.jpg");
        categories.put("1069784", "Writing & Correction Supplies");
        categoriesLink.put("1069784", "https://m.media-amazon.com/images/I/71V4pYi6gNL.jpg");
        categories.put("1069664", "Paper");
        categoriesLink.put("1069784", "https://m.media-amazon.com/images/I/61Vq5LHY-lL._AC_UF894,1000_QL80_.jpg");

        categories.put("172518", "Calculators");
        categoriesLink.put("172518", "https://m.media-amazon.com/images/I/81gCddxjppL.jpg");
        categories.put("172576", "Copiers");
        categoriesLink.put("172576", "https://m.media-amazon.com/images/I/41v7t+EAAFL._AC_UF1000,1000_QL80_.jpg");
        categories.put("9424016011", "Printers & Accessories");
        categoriesLink.put("9424016011", "https://m.media-amazon.com/images/I/31QqYWnIr7L._SR290,290_.jpg");
        categories.put("14759145011", "Video Projectors & Accessories");
        categoriesLink.put("14759145011", "https://m.media-amazon.com/images/I/71LV06uzumL.jpg");

        categories.put("1068956", "Office Lighting");
        categoriesLink.put("1068956", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/f1ce050f-bac1-4ee2-8f7a-7aa5868171b9._SL480_.jpg");
        categories.put("1069114", "Carts & Stands");
        categoriesLink.put("1069114", "https://m.media-amazon.com/images/I/71ejWdi2rlL._AC_UF894,1000_QL80_.jpg");
        categories.put("1069222", "Tables");
        categoriesLink.put("1069222", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-Lf0vWwgn2290RB-p8lBkQqN1u0INiTrUWA&s");

        categories.put("2975359011", "Food");
        categoriesLink.put("2975359011", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZ2dTifgpVKsX7tcIOHMWa8RdxzjRxnHr42w&s");
        categories.put("2975413011", "Toys");
        categoriesLink.put("2975413011", "Toys");
        categories.put("2975377011", "Health Supplies");
        categoriesLink.put("2975377011", "https://m.media-amazon.com/images/I/61y5u6XY6xL.jpg");
        categories.put("2975326011", "Beds & Furniture");
        categoriesLink.put("2975326011", "https://m.media-amazon.com/images/I/918EVby+hnL._AC_UF1000,1000_QL80_.jpg");

        categories.put("2975243011", "Beds & Furniture");
        categoriesLink.put("2975243011", "https://m.media-amazon.com/images/I/91Ob6wjGg2L.jpg");
        categories.put("2975259011", "Feeding & Watering Supplies");
        categoriesLink.put("2975259011", "https://m.media-amazon.com/images/I/71MTlvMw5VL._AC_UF1000,1000_QL80_.jpg");
        categories.put("2975265011", "Food");
        categoriesLink.put("2975265011", "https://m.media-amazon.com/images/I/712D6MbAnaL._AC_UF1000,1000_QL80_.jpg");
        categories.put("2975303011", "Toys");
        categoriesLink.put("2975303011", "https://m.media-amazon.com/images/I/61NPu0p-wsL._AC_UF1000,1000_QL80_.jpg");

        categories.put("2975464011", "Aquarium Cleaners");
        categoriesLink.put("2975464011", "https://m.media-amazon.com/images/I/719VY6i5BlL.jpg");
        categories.put("2975448011", "Aquarium Decorator");
        categoriesLink.put("2975448011", "https://m.media-amazon.com/images/I/81wTpNbwEML._AC_UF894,1000_QL80_.jpg");
        categories.put("3048857011", "Aquarium Hydrometers");
        categoriesLink.put("3048857011", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/da101607-7454-4f18-b1c7-ef9695d148aa._SL480_.jpg");
        categories.put("7955292011", "Aquariums & Fish Bowls");
        categoriesLink.put("7955292011", "https://m.media-amazon.com/images/I/61K5VpLA1-L._AC_UF1000,1000_QL80_.jpg");

        categories.put("2975238011", "Food");
        categoriesLink.put("2975238011", "https://m.media-amazon.com/images/I/91kFz2HPcNL._AC_UF1000,1000_QL80_.jpg");
        categories.put("2975222011", "Cages & Accessories");
        categoriesLink.put("2975222011", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT132cJ9JZHkKLerDr3IpU4-eNjoUVeSCbqWQ&s");
        categories.put("2975234011", "Feeding & Watering Supplies");
        categoriesLink.put("2975234011", "https://m.media-amazon.com/images/I/71lgfOXeQPL._AC_UF1000,1000_QL80_.jpg");

        categories.put("565098", "Desktops");
        categoriesLink.put("565098", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRMPvjd_sR0P-zbotAwYxtRZTcKTOWTbxFtA&s");
        categories.put("565108", "Laptops");
        categoriesLink.put("565108", "https://m.media-amazon.com/images/I/71aTEZOda0L.jpg");
        categories.put("1232597011", "Tablets");
        categoriesLink.put("565108", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTU2YyTxCEV5m-ea5-9N8SwUXFyFEPLFjDL6Q&s");

        categories.put("11041841", "Chargers & Adapters");
        categoriesLink.put("11041841", "https://m.media-amazon.com/images/I/61elgS6ncgL._AC_UF1000,1000_QL80_.jpg");
        categories.put("2243862011", "Cooling Pads & External Fans");
        categoriesLink.put("2243862011", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0rkAfduzXkSYll4fhjtbvzD9a7lRH-gmHZw&s");
        categories.put("172470", "Bags, Cases & Sleeves");
        categoriesLink.put("172470", "https://m.media-amazon.com/images/I/61Qta0u6sUS._AC_UF894,1000_QL80_.jpg");

        categories.put("3015426011", "Desktop Barebones");
        categoriesLink.put("3015426011", "https://m.media-amazon.com/images/I/81L816JIBSL._AC_UF894,1000_QL80_.jpg");
        categories.put("3012292011", "External Components");
        categoriesLink.put("3012292011", "https://m.media-amazon.com/images/S/al-na-9d5791cf-3faf/db711b42-8ed0-46b6-a7d8-675cafe2ce1c._SL480_.jpg");
        categories.put("17923671011", "Internal Components");
        categoriesLink.put("17923671011", "https://m.media-amazon.com/images/I/41fWoqI9XJL._SR290,290_.jpg");

        categories.put("281052", "Digital Cameras");
        categoriesLink.put("281052", "https://m.media-amazon.com/images/I/71NXowebfKL._AC_UF894,1000_QL80_.jpg");
        categories.put("499248", "Lenses");
        categoriesLink.put("499248", "https://m.media-amazon.com/images/I/71wfYiuyYRL._AC_UF894,1000_QL80_.jpg");
        categories.put("499306", "Tripods & Monopods");
        categoriesLink.put("499306", "https://m.media-amazon.com/images/I/71MPXuJuEjL._AC_UF1000,1000_QL80_.jpg");

        categories.put("2407748011", "Carrier Cell Phones");
        categoriesLink.put("2407748011", "Carrier Cell Phones");
        categories.put("2407749011", "Unlocked Cell Phones");
        categoriesLink.put("2407749011", "https://m.media-amazon.com/images/I/61Nvb4c-i0L._AC_UF894,1000_QL80_.jpg");
        categories.put("2407760011", "Cases, Holsters & Clips");
        categoriesLink.put("2407760011", "https://m.media-amazon.com/images/I/612OrZZihJL._AC_UF894,1000_QL80_.jpg");

        categories.put("12097478011", "Earbud Headphones");
        categoriesLink.put("12097478011", "https://m.media-amazon.com/images/I/51vZrA9+uIL.jpg");
        categories.put("12097480011", "On-Ear Headphones");
        categoriesLink.put("12097478011", "https://m.media-amazon.com/images/I/71aBTe8lZkL._AC_UF894,1000_QL80_.jpg");
        categories.put("12097479011", "Over-Ear Headphones");
        categoriesLink.put("12097479011", "https://m.media-amazon.com/images/I/613axEScHJL._AC_UF894,1000_QL80_.jpg");

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
