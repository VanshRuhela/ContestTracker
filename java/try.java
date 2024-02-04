import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GroupByRandomNumberExample {
    public static void main(String[] args) {
        // Sample dataItems with different timestamps
        List<DataItem> dataItems = Arrays.asList(
                new DataItem(LocalDate.of(2022, 1, 1), "OtherData1"),
                new DataItem(LocalDate.of(2022, 1, 1), "OtherData2"),
                new DataItem(LocalDate.of(2022, 1, 2), "OtherData3"),
                new DataItem(LocalDate.of(2022, 1, 2), "OtherData4"),
                new DataItem(LocalDate.of(2022, 1, 3), "OtherData5"),
                new DataItem(LocalDate.of(2022, 1, 3), "OtherData6"),
                new DataItem(LocalDate.of(2022, 1, 4), "OtherData7"),
                new DataItem(LocalDate.of(2022, 1, 4), "OtherData8"),
                new DataItem(LocalDate.of(2022, 1, 5), "OtherData9"),
                new DataItem(LocalDate.of(2022, 1, 5), "OtherData10")
        );

        // Group the dataItems by composite key
        Map<String, List<DataItem>> groupedByCompositeKey = dataItems.stream()
                .collect(Collectors.groupingBy(DataItem::getCompositeKey));

        // Assign a random number to each group and create a map
        Random random = new Random();
        Map<Integer, List<DataItem>> groupedByRandomNumber = groupedByCompositeKey.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> random.nextInt(), // Random number as key
                        Map.Entry::getValue            // List of DataItems as value
                ));

        // Create a new list with random ID and composite key
        List<NewItem> newList = new ArrayList<>();
        int idCounter = 1;

        for (Map.Entry<Integer, List<DataItem>> entry : groupedByRandomNumber.entrySet()) {
            int randomNumber = entry.getKey();
            List<DataItem> dataItemList = entry.getValue();

            for (DataItem dataItem : dataItemList) {
                String compositeKey = dataItem.getCompositeKey();
                newList.add(new NewItem(idCounter++, randomNumber, compositeKey));
            }
        }

        // Print the result
        newList.forEach(System.out::println);
    }

    static class NewItem {
        private int id;
        private int randomNumber;
        private String compositeKey;

        public NewItem(int id, int randomNumber, String compositeKey) {
            this.id = id;
            this.randomNumber = randomNumber;
            this.compositeKey = compositeKey;
        }

        @Override
        public String toString() {
            return "NewItem{" +
                    "id=" + id +
                    ", randomNumber=" + randomNumber +
                    ", compositeKey='" + compositeKey + '\'' +
                    '}';
        }
    }
}
