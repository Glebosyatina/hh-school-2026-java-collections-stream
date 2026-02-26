package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    //думаю можно без этой проверки обойтись, стрим нормально обрабатывает ситуацию пустого списка
    //if (persons.size() == 0) {
      //return Collections.emptyList();
    //}
    // persons.remove(0);
    //тут можно просто использовать skip для стрима, не удаляя первый элемент, думаю лучше так
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // return getNames(persons).stream().distinct().collect(Collectors.toSet());
    //думаю тут можно обойтись без стримов и просто создать сет на основе листа
    return new HashSet<String>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    //String result = "";
    //проще через метод String.join
    return String.join(" ", person.firstName(), person.middleName(), person.secondName());
    /*
    if (person.secondName() != null) {
      result += person.secondName();
    }

    if (person.firstName() != null) {
      result += " " + person.firstName();
    }

    if (person.secondName() != null) {
      result += " " + person.secondName();
    }
     */
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    /*
    Map<Integer, String> map = new HashMap<>(1);
    for (Person person : persons) {
      if (!map.containsKey(person.id())) {
        map.put(person.id(), convertPersonToString(person));
      }
    }
     */
    //можно сделать через стрим, компактнее, читабельнее, может упасть если в коллекции попадется несколько дубликатов
    //вылетит IllegalStatException, для этого убираем дубликаты через distinct
    return persons.stream()
        .distinct()
        .collect(Collectors.toMap(Person::id, Person::firstName));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    //как вариант вместо двойного цикла, перекинуть в мапу( 1 проход закинули первый лист в мапу,
    // 2ым проходом пробежались по второй коллекции и если встретили хоть один ключ который уже был то true
    //и в итоге сложность будет не O(N^2), O(2N) = O(N)

    Set<Person> set = new HashSet<>(persons1);
    return persons2.stream()
        .anyMatch(set::contains);
    /*
    boolean has = false;
    for (Person person1 : persons1) {
      for (Person person2 : persons2) {
        if (person1.equals(person2)) {
          //has = true;
          //как только встретили хотя бы одно совпадение уже можно возвращать true и не делать лишних итераций
          return true;
        }
      }
    }
    return has;
     */
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    //count = 0;
    //здесь вместо ручного инкремента счетика проще вернуть count из стрима
    return numbers.filter(num -> num % 2 == 0).count();//forEach(num -> count++);
    //return count;
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    //создали уже отсортированную последовательность
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    //тут создали ее копию (разные объекты, разные ссылки -> изменения на старом объекте никак не влияют на новый
    //в snapshot лежит отсортированная последовательность
    List<Integer> snapshot = new ArrayList<>(integers);
    //тут перемешали первую последовательность
    Collections.shuffle(integers);
    // но когда мы на основе перемешанной последовательности создаем HashSet

    //HashSet внутри использует HashMap, и при добавлениии элементов метод hashCode для Integer возвращает само число,
    //поэтому они сохраняются в отсортированном порядке
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
