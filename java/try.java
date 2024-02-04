import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DataProcessing {

    public static void main(String[] args) {
        // Step 1: Create DataItem1 Entity, Repository, and Sample Data
        List<DataItem1> dataItem1List = Arrays.asList(
                new DataItem1(new CompositeKey1("Key1", "Key2"), LocalDate.of(2022, 1, 1), "OtherData1"),
                new DataItem1(new CompositeKey1("Key1", "Key2"), LocalDate.of(2022, 1, 1), "OtherData2"),
                new DataItem1(new CompositeKey1("Key3", "Key4"), LocalDate.of(2022, 1, 2), "OtherData3"),
                new DataItem1(new CompositeKey1("Key3", "Key4"), LocalDate.of(2022, 1, 2), "OtherData4"),
                new DataItem1(new CompositeKey1("Key5", "Key6"), LocalDate.of(2022, 1, 3), "OtherData5"),
                new DataItem1(new CompositeKey1("Key5", "Key6"), LocalDate.of(2022, 1, 3), "OtherData6"),
                new DataItem1(new CompositeKey1("Key7", "Key8"), LocalDate.of(2022, 1, 4), "OtherData7")
        );

        // Step 2: Create DataItem2 Entity
        List<DataItem2> dataItem2List = processDataItem1List(dataItem1List);

        // Print Output
        System.out.println("DataItem1 List:");
        dataItem1List.forEach(System.out::println);

        System.out.println("\nDataItem2 List:");
        dataItem2List.forEach(System.out::println);
    }

    private static List<DataItem2> processDataItem1List(List<DataItem1> dataItem1List) {
        // Step 3: Group by Date and Create groupedByDateMap
        Map<String, List<DataItem1>> groupedByDateMap = dataItem1List.stream()
                .collect(Collectors.groupingBy(
                        dataItem1 -> generateRandomString(new Random(), 6) // Generate random string as key
                ));

        // Step 4: Create DataItem2 List from groupedByDateMap
        return groupedByDateMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(dataItem1 -> new DataItem2(
                                new CompositeKey1(dataItem1.getCompositeKey().getData1(), dataItem1.getCompositeKey().getData2()),
                                dataItem1.getDateField(),
                                dataItem1.getSomeOtherData(),
                                entry.getKey() // Random string as some other data
                        ))
                )
                .collect(Collectors.toList());
    }

    // Helper method to generate a random string of given length
    private static String generateRandomString(Random random, int length) {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(random.nextInt(10)); // Append random digit (0-9)
        }
        return randomString.toString();
    }

    // Step 1: Create DataItem1 Entity, Repository, and Sample Data
    @Entity
    @Table(name = "data_item1")
    public static class DataItem1 implements Serializable {

        @EmbeddedId
        private CompositeKey1 compositeKey;

        @Column(name = "date_field")
        private LocalDate dateField;

        @Column(name = "some_other_data")
        private String someOtherData;

        // Constructors, getters, setters...
    }

    @Embeddable
    public static class CompositeKey1 implements Serializable {

        @Column(name = "data1")
        private String data1;

        @Column(name = "data2")
        private String data2;

        // Constructors, getters, setters...
    }

    // Step 2: Create DataItem2 Entity
    @Entity
    @Table(name = "data_item2")
    public static class DataItem2 implements Serializable {

        @EmbeddedId
        private CompositeKey1 compositeKey; // Reusing CompositeKey1 for data1 and data2

        @Column(name = "date_field")
        private LocalDate dateField;

        @Column(name = "some_other_data")
        private String someOtherData;

        // Constructors, getters, setters...
    }
}
