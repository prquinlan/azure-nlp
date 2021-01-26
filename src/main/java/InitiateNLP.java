import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.CategorizedEntity;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.ai.textanalytics.models.SentenceSentiment;
import com.azure.core.credential.AzureKeyCredential;

public class InitiateNLP
{
    public static void main(String [] args)
    {

        //You will create these methods later in the quickstart.
        TextAnalyticsClient client = authenticateClient("b95e184bd952443bb592910c38771a18", "https://ccnlptest.cognitiveservices.azure.com/");

        recognizeEntitiesExample(client);
        System.out.println("***");
        extractKeyPhrasesExample(client);
        System.out.println("***");
        sentimentAnalysisExample(client);
    }
    static TextAnalyticsClient authenticateClient(String key, String endpoint) {
        return new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();
    }

    static void recognizeEntitiesExample(TextAnalyticsClient client)
    {
        // The text that need be analyzed.
        String text = "Migraine \\ On average, how often do you have headaches? (select one). Never";

        for (CategorizedEntity entity : client.recognizeEntities(text)) {
            System.out.printf(
                    "Recognized entity: %s, entity category: %s, entity sub-category: %s, score: %s, offset: %s, .%n",
                    entity.getText(),
                    entity.getCategory(),
                    entity.getSubcategory(),
                    entity.getConfidenceScore(),
                    entity.getOffset());
        }
    }

    static void sentimentAnalysisExample(TextAnalyticsClient client)
    {
        // The text that need be analyzed.
        String text = "Have you been diagnosed as having Osteoporosis \\ What treatment did\\do you have? \\ Do you or does anyone in your family have Osteoporosis (brittle bone disease) \\ Yourself \\ If yes, what treatment was used?";

        DocumentSentiment documentSentiment = client.analyzeSentiment(text);
        System.out.printf(
                "Recognized document sentiment: %s, positive score: %s, neutral score: %s, negative score: %s.%n",
                documentSentiment.getSentiment(),
                documentSentiment.getConfidenceScores().getPositive(),
                documentSentiment.getConfidenceScores().getNeutral(),
                documentSentiment.getConfidenceScores().getNegative());

        for (SentenceSentiment sentenceSentiment : documentSentiment.getSentences()) {
            System.out.printf(
                    "Recognized sentence sentiment: %s, positive score: %s, neutral score: %s, negative score: %s.%n",
                    sentenceSentiment.getSentiment(),
                    sentenceSentiment.getConfidenceScores().getPositive(),
                    sentenceSentiment.getConfidenceScores().getNeutral(),
                    sentenceSentiment.getConfidenceScores().getNegative());
        }
    }

    static void extractKeyPhrasesExample(TextAnalyticsClient client)
    {
        // The text that need be analyzed.
        String text = "Have you been diagnosed as having Osteoporosis \\ What treatment did\\do you have? \\ Do you or does anyone in your family have Osteoporosis (brittle bone disease) \\ Yourself \\ If yes, what treatment was used?";

        System.out.printf("Recognized phrases: %n");
        for (String keyPhrase : client.extractKeyPhrases(text)) {
            System.out.printf("%s%n", keyPhrase);
        }
    }
}
