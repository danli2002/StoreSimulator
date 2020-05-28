/* 
    Handles real life events, like disasters...
    Soon to be implemented.

    by Daniel Li
    5/27/20
*/

public class EventHandler{
    public EventHandler(){
    }

    public String indShock(StoreItem_DL item){
        double coinflip = Math.random();
        if (coinflip > 0.5){
            double change = Math.random() + 0.5;
            String[] posHeadlines = {
                    String.format("A new study shows that %s is very beneficial to health",item.getName()),
                    String.format("TikTok stars are promoting %s like crazy!",item.getName()),
                    String.format("No one knows why, but all of the sudden, owning %s is the wave!",item.getName()),
                    String.format("Reports show that prices of %s are going to skyrocket in the future!",item.getName())
                    };

            String[] negHeadlines = {
                    String.format("A new study shows that %s is very harmful to health",item.getName()),
                    String.format("TikTok star denounces %s",item.getName()),
                    String.format("Who even likes %s anymore? Was it just a fad?",item.getName()),
                    String.format("Reports show that prices of %s are going to plummet in the future!",item.getName())
                    };
                
            if(change > 1){
                if(item.getPIndex() * change > 1){
                    item.setPIndex(1);
                }
                else{
                    item.incrementPIndex(change);
                }
                int seed = (int)(Math.random() * (posHeadlines.length - 1));
                return posHeadlines[seed];
            }
            else{
                item.incrementPIndex(change);
                int seed = (int)(Math.random() * (negHeadlines.length - 1));
                return negHeadlines[seed];
            }
        }
        else{return "";}

    }

    public String marketShock(StoreItem_DL[] items){
        double change = Math.random() + 0.5;
        String[] negHeadlines = {
                "A pandemic has caused the government to discourage shopping",
                "A financial recession has hit and is underway",
                "A new non-materialistic religion has surfaced and is gaining traction!",
                "Federal income tax has been raised, spending power down.",
                "A decline in population of the local town means there will be less shoppers demanding products"};

        String[] posHeadlines = {
                "Government imposes shopping rebates",
                "Economic growth is underway!",
                "A new cult has arisen that praises the acquisition of material goods",
                "Federal income tax has been lowered, Americans spending like crazy!",
                "A boost in population of the local town means there will be more shoppers demanding products"};
            
        if(change > 0){
            for(int i =0; i < items.length; i++){
                StoreItem_DL item = items[i];
                if(change > 0){
                    if(item.getPIndex() * change > 1){
                        item.setPIndex(1);
                    }
                    else{
                        item.incrementPIndex(change);
                    }   
                }
            }
            int seed = (int)(Math.random() * (posHeadlines.length - 1));
            return posHeadlines[seed];   
        }
        else{
            for(int i =0; i < items.length; i++){
                StoreItem_DL item = items[i];
                item.incrementPIndex(change);
            }
            int seed = (int)(Math.random() * (negHeadlines.length - 1));
            return negHeadlines[seed];
        }
    }
}
