import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GroupByRandomNumberExample {
    public static void main(String[] args) {
        // Sample dataItems with different timestamps
        List<DataItem> dataItems = Arrays.asList(
                new DataItem(LocalDate.of(2022-01-01), "OtherData1"),
                new DataItem(LocalDate.of(2022-01-01), "OtherData2"),
                new DataItem(LocalDate.of(2022-01-02), "OtherData3"),
                new DataItem(LocalDate.of(2022-01-02), "OtherData4"),
                new DataItem(LocalDate.of(2022-01-03), "OtherData5"),
                new DataItem(LocalDate.of(2022-01-03), "OtherData6"),
                new DataItem(LocalDate.of(2022-01-04), "OtherData7"),
                new DataItem(LocalDate.of(2022-01-04), "OtherData8"),
                new DataItem(LocalDate.of(2022-01-05), "OtherData9"),
                new DataItem(LocalDate.of(2022-01-05), "OtherData10")
        );

        // Group the dataItems by composite key
        Map<String, List<DataItem>> groupedByCompositeKey = dataItems.stream()
                .collect(Collectors.groupingBy(DataItem::getCompositeKey));

        // Generate a random string of 6 digits
        Random random = new Random();
        Map<String, List<DataItem>> groupedByRandomString = groupedByCompositeKey.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> generateRandomString(random, 6), // Random string as key
                        Map.Entry::getValue            // List of DataItems as value
                ));

        // Print dataItems
        System.out.println("Original DataItems:");
        dataItems.forEach(System.out::println);
        System.out.println("---------------------");

        // Print groupedByRandomString
        System.out.println("GroupedByRandomString:");
        groupedByRandomString.forEach((randomString, items) -> {
            System.out.println("Random String: " + randomString);
            items.forEach(item -> System.out.println("  " + item));
        });
        System.out.println("---------------------");

        // Create a new list with composite key, other data, and random string
        List<NewDataItem> newDataItems = groupedByRandomString.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(dataItem -> new NewDataItem(entry.getKey(), dataItem.getCompositeKey(), dataItem.getOtherData())))
                .collect(Collectors.toList());

        // Print newDataItems
        System.out.println("NewDataItems:");
        newDataItems.forEach(System.out::println);
    }

    // Helper method to generate a random string of given length
    private static String generateRandomString(Random random, int length) {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(random.nextInt(10)); // Append random digit (0-9)
        }
        return randomString.toString();
    }
}

class NewDataItem {
    private final String randomString;
    private final String compositeKey;
    private final String otherData;

    public NewDataItem(String randomString, String compositeKey, String otherData) {
        this.randomString = randomString;
        this.compositeKey = compositeKey;
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "NewDataItem{" +
                "randomString='" + randomString + '\'' +
                ", compositeKey='" + compositeKey + '\'' +
                ", otherData='" + otherData + '\'' +
                '}';
    }
}
