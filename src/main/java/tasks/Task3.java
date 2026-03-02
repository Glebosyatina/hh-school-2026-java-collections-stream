package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    //sorted применяем к стриму задавая порядок сортировки по полям
    //думаю проблема может быть в компараторе при обработке null значений, наверное
    //для этого нужен свой null компаратор
    Comparator<? super String> nullComparator = (a, b) -> {
      if (a == null && b == null){
        return 0;
      }
      if (a == null) {
        return -1;
      }
      if (b == null){
        return 1;
      }
      return a.compareTo(b);
    };

    return persons.stream()
        .sorted(Comparator.comparing(Person::secondName, nullComparator)
            .thenComparing(Person::firstName, nullComparator)
            .thenComparing(Person::createdAt))
        .collect(Collectors.toList());
  }
}
