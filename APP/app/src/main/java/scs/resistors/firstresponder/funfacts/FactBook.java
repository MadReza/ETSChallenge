package scs.resistors.firstresponder.funfacts;

import java.util.Random;

public class FactBook {

    private String[] mFacts = {
            /*
                Found them here: http://www.factslides.com/s-Health
                quiet sure it's BS
             */
            "A lack of exercise is now causing as many deaths as smoking across the world, a study suggests.",
            "People who regularly eat dinner or breakfast in restaurants double their risk of becoming obese.",
            "Farting helps reduce high blood pressure and is good for your health.",
            "Laughing 100 times is equivalent to 15 minutes of exercise on a stationary bicycle.",
            "Sitting for more than three hours a day can cut two years off a person's life expectancy.",
            "Over 30% of cancer could be prevented by avoiding tobacco and alcohol, having a healthy diet and physical activity.",
            "Sleeping less than 7 hours each night reduces your life expectancy.",
            "Every cigarette you smoke reduces your expected life span by 11 minutes.",
            "1 Can of Soda a day increases your chances of getting type 2 diabetes by 22%.",
            "There are more skin cancer cases due to indoor tanning than lung cancer cases due to smoking.",
            "On average, it takes 66 days to form a new habit.",
            "McDonalds' Caesar salad is more fattening than their hamburger." };

    public String getFact() {
        return mFacts[new Random().nextInt(mFacts.length)];
    }
}
